package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthTokenEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerAuthDao customerAuthDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;


    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthTokenEntity authenticateByUserNamePassword(String userName, String password) throws AuthenticationFailedException {

        if( userName == null ||
            userName.isEmpty() ||
            password == null ||
            password.isEmpty()) {
            throw new AuthenticationFailedException("ATH-003","Incorrect format of decoded customer name and password");
        }

        CustomerEntity customerEntity = customerDao.getUserByContactNumber(userName);
        if( customerEntity == null ) {
            throw new AuthenticationFailedException("ATH-001","This contact number has not been registered!");
        }

        String encryptedPassword = passwordCryptographyProvider.encrypt(password, customerEntity.getSalt());
        if( !encryptedPassword.equals(customerEntity.getPassword())) {
            throw new AuthenticationFailedException("ATH-002","Invalid Credentials");
        }

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
        CustomerAuthTokenEntity customerAuthTokenEntity = new CustomerAuthTokenEntity();
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
}
