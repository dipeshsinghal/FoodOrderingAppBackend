package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AuthenticationService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthTokenEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class AddressController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/address",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(
            @RequestHeader("authorization") final String authorization,
            final SaveAddressRequest saveAddressRequest)
            throws AuthorizationFailedException, SaveAddressException, AddressNotFoundException {

        // Call authenticationService with access token came in authorization field.
        CustomerAuthTokenEntity customerAuthTokenEntity = authenticationService.authenticateByAccessToken(authorization);

        return null;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/address/customer",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getAddressList(
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        // Call authenticationService with access token came in authorization field.
        CustomerAuthTokenEntity customerAuthTokenEntity = authenticationService.authenticateByAccessToken(authorization);

        return null;
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/address/{address_id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeleteAddressResponse> deleteAddress(
            @RequestHeader("authorization") final String authorization,
            @PathVariable("address_id") final UUID addressId)
            throws AuthorizationFailedException, AddressNotFoundException {

        // Call authenticationService with access token came in authorization field.
        CustomerAuthTokenEntity customerAuthTokenEntity = authenticationService.authenticateByAccessToken(authorization);

        return null;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/states",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StatesListResponse> getStatesList() {
        return null;
    }
}

