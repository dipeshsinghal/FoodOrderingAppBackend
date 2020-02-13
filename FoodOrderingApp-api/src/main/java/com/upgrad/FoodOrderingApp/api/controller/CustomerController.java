package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.PasswordCryptographyProvider;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/customer/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> customerSignup(
            @RequestBody final SignupCustomerRequest signupCustomerRequest)
            throws SignUpRestrictedException {

        if (signupCustomerRequest.getFirstName() == null ||
                signupCustomerRequest.getFirstName().isEmpty() ||
                signupCustomerRequest.getEmailAddress() == null ||
                signupCustomerRequest.getEmailAddress().isEmpty() ||
                signupCustomerRequest.getContactNumber() == null ||
                signupCustomerRequest.getContactNumber().isEmpty() ||
                signupCustomerRequest.getPassword() == null ||
                signupCustomerRequest.getPassword().isEmpty()) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled.");
        }

        System.out.println(signupCustomerRequest);
        //create a new CustomerEntity Object
        CustomerEntity customerEntity = new CustomerEntity();

        //create a new random unique uuid and set it to new Customer Entity
        customerEntity.setUuid(UUID.randomUUID().toString());

        //Set All the field of new object from the Request
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setPassword(signupCustomerRequest.getPassword());

        //Call CustomerService to create a new customer Entity
        CustomerEntity createdCustmerEntity = customerService.saveCustomer(customerEntity);

        //create response with create customer uuid
        SignupCustomerResponse signupCustomerResponse = new SignupCustomerResponse().id(createdCustmerEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);

    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/customer/login",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(
            @RequestHeader("authorization") final String authorization)
            throws AuthenticationFailedException, AuthorizationFailedException {

        //split and extract authorization base 64 code string from "authorization" field
        String[] base64EncodedString = authorization.split("Basic ");

        //decode base64 string from a "authorization" field
        if(base64EncodedString.length != 2 ) {
            throw new AuthenticationFailedException("ATH-003","Incorrect format of decoded customer name and password");
        }
        byte[] decodedArray = passwordCryptographyProvider.getBase64DecodedStringAsBytes(base64EncodedString[1]);

        String decodedString = new String(decodedArray);

        //decoded string contain username(contact number) and password separated by ":"
        String[] decodedUserNamePassword = decodedString.split(":");

        if ( decodedUserNamePassword.length != 2 ) {
            throw new AuthenticationFailedException("ATH-003","Incorrect format of decoded customer name and password");
        }

        //get CustomerEntity from Auth Token
        CustomerEntity customerEntity = customerService.getCustomer(authorization);

        //send response with customer uuid and access token in HttpHeader
        LoginResponse loginResponse = new LoginResponse().id(customerEntity.getUuid()).message("SIGNED IN SUCCESSFULLY");
        HttpHeaders headers = new HttpHeaders();
        //headers.add("access_token", customerAuthEntity.getAccessToken());

        return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);

    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/customer/logout",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerService.logout(authorization);


        //create response with signed out customer uuid
        LogoutResponse signoutResponse = new LogoutResponse().id(customerAuthEntity.getCustomer().getUuid()).message("SIGNED OUT SUCCESSFULLY");
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<LogoutResponse>(signoutResponse, headers, HttpStatus.OK);

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/customer",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(
            @RequestHeader("authorization") final String authorization,
            @RequestBody final UpdateCustomerRequest updateCustomerRequest)
            throws UpdateCustomerException, AuthorizationFailedException {

        CustomerEntity customerEntity = new CustomerEntity();

        CustomerEntity updateCustomerEntity = customerService.updateCustomer(customerEntity);

        //create response with create customer uuid
        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse().id(updateCustomerEntity.getUuid()).status("Service Not Implemented");

        return new ResponseEntity<UpdateCustomerResponse>(updateCustomerResponse, HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/customer/password",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse> updatePassword(
            @RequestHeader("authorization") final String authorization,
            @RequestBody final UpdatePasswordRequest updatePasswordRequest)
            throws UpdateCustomerException, AuthorizationFailedException {

        // Call authenticationService with access token came in authorization field.
        CustomerEntity customerEntity = customerService.getCustomer(authorization);

        CustomerEntity updateCustomerEntity = customerService.updateCustomerPassword(updatePasswordRequest.getOldPassword(),updatePasswordRequest.getOldPassword(), customerEntity);

        //create response with create customer uuid
        UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse().id(updateCustomerEntity.getUuid()).status("Service Not Implemented");

        return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse, HttpStatus.CREATED);
    }
}
