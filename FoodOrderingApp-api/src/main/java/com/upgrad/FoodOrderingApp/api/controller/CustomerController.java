package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AuthenticationService;
import com.upgrad.FoodOrderingApp.service.businness.JwtTokenProvider;
import com.upgrad.FoodOrderingApp.service.businness.PasswordCryptographyProvider;
import com.upgrad.FoodOrderingApp.service.businness.SignupBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthTokenEntity;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private SignupBusinessService signupBusinessService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/customer/signup",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> customerSignup(
            final SignupCustomerRequest signupCustomerRequest)
            throws SignUpRestrictedException {


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

        //Call signupBusinessService to create a new customer Entity
        final CustomerEntity createdCustmerEntity = signupBusinessService.signup(customerEntity);

        //create response with create customer uuid
        SignupCustomerResponse userResponse = new SignupCustomerResponse().id(createdCustmerEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SignupCustomerResponse>(userResponse, HttpStatus.CREATED);

    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/customer/login",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(
            @RequestHeader("authorization") final String authorization)
            throws AuthenticationFailedException {

        //split and extract authorization base 64 code string from "authorization" field
        String[] base64EncodedString = authorization.split("Basic ");

        //decode base64 string from a "authorization" field
        if(base64EncodedString.length != 2 ) {
            throw new AuthenticationFailedException("ATH-003","Incorrect format of decoded customer name and password");
        }
        byte[] decodedArray = passwordCryptographyProvider.getBase64DecodedStringAsBytes(base64EncodedString[1]);

        String decodedString = new String(decodedArray);

        //decoded string contain username and password separated by ":"
        String[] decodedUserNamePassword = decodedString.split(":");

        if ( decodedUserNamePassword.length != 2 ) {
            throw new AuthenticationFailedException("ATH-003","Incorrect format of decoded customer name and password");
        }

        //call authenticationService service to generate user Auth Token for any further communication
        CustomerAuthTokenEntity customerAuthTokenEntity = authenticationService.authenticateByUserNamePassword(decodedUserNamePassword[0], decodedUserNamePassword[1]);

        //get CustomerEntity from Auth Token
        CustomerEntity customerEntity = customerAuthTokenEntity.getCustomer();

        //send response with user uuid and access token in HttpHeader
        LoginResponse loginResponse = new LoginResponse().id(customerEntity.getUuid()).message("SIGNED IN SUCCESSFULLY");
        HttpHeaders headers = new HttpHeaders();
        headers.add("access_token", customerAuthTokenEntity.getAccessToken());

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
        return null;
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/customer",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(
            @RequestHeader("authorization") final String authorization,
            final UpdateCustomerRequest updateCustomerRequest)
            throws UpdateCustomerException, AuthorizationFailedException {
        return null;
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/customer/password",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse> updatePassword(
            @RequestHeader("authorization") final String authorization,
            final UpdatePasswordRequest updatePasswordRequest)
            throws UpdateCustomerException, AuthorizationFailedException {
        return null;
    }
}
