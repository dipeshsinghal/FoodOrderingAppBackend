package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupBusinessService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity signup(CustomerEntity customerEntity) throws SignUpRestrictedException {

        if( customerEntity.getFirstName() == null ||
                customerEntity.getFirstName().isEmpty() ||
                customerEntity.getEmail() == null ||
                customerEntity.getEmail().isEmpty() ||
                customerEntity.getContactNumber() == null ||
                customerEntity.getContactNumber().isEmpty() ||
                customerEntity.getPassword() == null ||
                customerEntity.getPassword().isEmpty()) {
            throw new SignUpRestrictedException("SGR-005","Except last name all fields should be filled.");
        }

        CustomerEntity existedCustomer = customerDao.getUserByContactNumber(customerEntity.getContactNumber());
        if( existedCustomer != null ) {
            throw new SignUpRestrictedException("SGR-001","This contact number is already registered! Try other contact number.");
        }

        if( !customerEntity.getEmail().matches("[a-zA-Z0-9]{3,}@[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}")) {
            throw new SignUpRestrictedException("SGR-002","Invalid email-id format!");
        }

        if( !customerEntity.getContactNumber().matches("[0-9]{10,}")) {
            throw new SignUpRestrictedException("SGR-003","Invalid contact number!");
        }

        if( !isPasswordStrong(customerEntity.getPassword())) {
            throw new SignUpRestrictedException("SGR-004","Weak password!");
        }

        String[] encryptedText = passwordCryptographyProvider.encrypt(customerEntity.getPassword());
        customerEntity.setSalt(encryptedText[0]);
        customerEntity.setPassword(encryptedText[1]);

        try {
            return customerDao.createCustomer(customerEntity);
        } catch (Exception e) {
            throw new SignUpRestrictedException("SGR-000","Unknown database error while creating customer");
        }

    }

    private boolean isPasswordStrong(String password){
        //min 8 char long
        //1 digit
        //1 one uppercase letter
        //1 - [#@$%&*!^]
        //total score of password
        boolean containdigit = false;
        boolean containUpperCaseChar = false;
        boolean containSpecialChar = false;

        if( password.length() < 8 )
            return false;
        char c;

        for (int i = 0; i < password.length(); i++) {

            c = password.charAt(i);

            if (c >= '0' && c <= '9') {
                containdigit = true;
             } else if (c >= 'A' && c <= 'Z') {
                containUpperCaseChar = true;
            } else if ( c == '#' || c == '@' || c == '$'  || c == '%' || c == '&'  || c == '*'  || c == '!' || c == '^' ) {
                containSpecialChar = true;
            }

            if(containdigit & containUpperCaseChar & containSpecialChar) {
                return true;
            }
        }

        return false;
    }

}
