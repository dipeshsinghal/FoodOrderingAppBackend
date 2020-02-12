package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.service.businness.SignupBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private SignupBusinessService signupBusinessService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/user/signup",
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
}
