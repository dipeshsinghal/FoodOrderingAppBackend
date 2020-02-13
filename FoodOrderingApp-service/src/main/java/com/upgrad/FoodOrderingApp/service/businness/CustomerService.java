package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerAuthDao customerAuthDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;


    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) throws SignUpRestrictedException {

        if (customerEntity.getFirstName() == null ||
                customerEntity.getFirstName().isEmpty() ||
                customerEntity.getEmail() == null ||
                customerEntity.getEmail().isEmpty() ||
                customerEntity.getContactNumber() == null ||
                customerEntity.getContactNumber().isEmpty() ||
                customerEntity.getPassword() == null ||
                customerEntity.getPassword().isEmpty()) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled.");
        }

        CustomerEntity existedCustomer = customerDao.getUserByContactNumber(customerEntity.getContactNumber());
        if (existedCustomer != null) {
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number.");
        }

        if (!customerEntity.getEmail().matches("[a-zA-Z0-9]{3,}@[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}")) {
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        }

        if (!customerEntity.getContactNumber().matches("[0-9]{10,}")) {
            throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
        }

        if (!isPasswordStrong(customerEntity.getPassword())) {
            throw new SignUpRestrictedException("SGR-004", "Weak password!");
        }

        String[] encryptedText = passwordCryptographyProvider.encrypt(customerEntity.getPassword());
        customerEntity.setSalt(encryptedText[0]);
        customerEntity.setPassword(encryptedText[1]);

        try {
            return customerDao.createCustomer(customerEntity);
        } catch (Exception e) {
            throw new SignUpRestrictedException("SGR-000", "Unknown database error while creating customer");
        }

    }

    private boolean isPasswordStrong(String password) {
        //min 8 char long
        //1 digit
        //1 one uppercase letter
        //1 - [#@$%&*!^]
        //total score of password
        boolean containdigit = false;
        boolean containUpperCaseChar = false;
        boolean containSpecialChar = false;

        if (password.length() < 8)
            return false;
        char c;

        for (int i = 0; i < password.length(); i++) {

            c = password.charAt(i);

            if (c >= '0' && c <= '9') {
                containdigit = true;
            } else if (c >= 'A' && c <= 'Z') {
                containUpperCaseChar = true;
            } else if (c == '#' || c == '@' || c == '$' || c == '%' || c == '&' || c == '*' || c == '!' || c == '^') {
                containSpecialChar = true;
            }

            if (containdigit & containUpperCaseChar & containSpecialChar) {
                return true;
            }
        }

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(String contactNumber, String password) throws AuthenticationFailedException {

        if (contactNumber == null ||
                contactNumber.isEmpty() ||
                password == null ||
                password.isEmpty()) {
            throw new AuthenticationFailedException("ATH-003", "Incorrect format of decoded customer name and password");
        }

        CustomerEntity customerEntity = customerDao.getUserByContactNumber(contactNumber);
        if (customerEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        }

        String encryptedPassword = passwordCryptographyProvider.encrypt(password, customerEntity.getSalt());
        if (!encryptedPassword.equals(customerEntity.getPassword())) {
            throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
        }

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
        CustomerAuthEntity customerAuthTokenEntity = new CustomerAuthEntity();
        customerAuthTokenEntity.setCustomer(customerEntity);
        final ZonedDateTime now = ZonedDateTime.now();
        final ZonedDateTime expiresAt = now.plusHours(8);

        customerAuthTokenEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));
        customerAuthTokenEntity.setLoginAt(now);
        customerAuthTokenEntity.setExpiresAt(expiresAt);

        customerAuthDao.createAuthToken(customerAuthTokenEntity);

        customerDao.updateCustomer(customerEntity);
        return customerAuthTokenEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity getCustomer(final String accessToken) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = customerAuthDao.getAuthTokenEntityByAccessToken(accessToken);
        if (customerAuthEntity != null) {
            // Token exist but customer logged out already or token expired
            if (customerAuthEntity.getLogoutAt() != null) {
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            } else if (customerAuthEntity.getExpiresAt().compareTo(ZonedDateTime.now()) <= 0) {
                throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
            }
            return customerAuthEntity.getCustomer();
        } else {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity logout(final String accessToken) throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerAuthDao.getAuthTokenEntityByAccessToken(accessToken);

        customerAuthEntity.setLogoutAt(ZonedDateTime.now());
        return customerAuthDao.updateAuthToken(customerAuthEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomer(CustomerEntity customerEntity) throws AuthorizationFailedException {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomerPassword(String oldPassword, String newPassword, CustomerEntity customerEntity) throws AuthorizationFailedException {
        return null;
    }

}