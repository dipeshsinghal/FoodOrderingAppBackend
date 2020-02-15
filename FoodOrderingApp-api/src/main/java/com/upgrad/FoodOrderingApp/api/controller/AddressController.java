package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.common.Utility;
import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class AddressController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;


    @RequestMapping(
            method = RequestMethod.POST,
            path = "/address",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(
            @RequestHeader("authorization") final String authorization,
            @RequestBody final SaveAddressRequest saveAddressRequest)
            throws AuthorizationFailedException, SaveAddressException, AddressNotFoundException {

        CustomerEntity customerEntity = customerService.getCustomer(Utility.getTokenFromAuthorizationField(authorization));

        StateEntity stateEntity = addressService.getStateByUUID(saveAddressRequest.getStateUuid());

        AddressEntity addressEntity = new AddressEntity();

        //create a new random unique uuid and set it to new Address Entity
        addressEntity.setUuid(UUID.randomUUID().toString());

        //Call AddressService to create a new AddressEntity
        AddressEntity createdAddressEntity =  addressService.saveAddress(addressEntity, customerEntity);

        SaveAddressResponse saveAddressResponse = new SaveAddressResponse().id(createdAddressEntity.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");

        return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/address/customer",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getAddressList(
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        CustomerEntity customerEntity = customerService.getCustomer(Utility.getTokenFromAuthorizationField(authorization));

        List<AddressEntity> listAddressEntity = addressService.getAllAddress(customerEntity);

        List<AddressList> listAddressList = null;
        if(listAddressEntity.size() != 0 ) {
            listAddressList = new ArrayList<AddressList>();
            for (AddressEntity addressEntity: listAddressEntity) {
                AddressListState addressListState = new AddressListState()
                        .id(UUID.fromString(addressEntity.getState().getUuid()))
                        .stateName(addressEntity.getState().getStateName());
                listAddressList.add(new AddressList()
                        .id(UUID.fromString(addressEntity.getUuid()))
                        .flatBuildingName(addressEntity.getFlatBuilNo())
                        .locality(addressEntity.getLocality())
                        .city(addressEntity.getCity())
                        .pincode(addressEntity.getPincode())
                        .state(addressListState));
            }
        }

        AddressListResponse addressListResponse = new AddressListResponse().addresses(listAddressList);

        return new ResponseEntity<AddressListResponse>(addressListResponse, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/address/{address_id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeleteAddressResponse> deleteAddress(
            @RequestHeader("authorization") final String authorization,
            @PathVariable("address_id") final String addressId)
            throws AuthorizationFailedException, AddressNotFoundException {

        CustomerEntity customerEntity = customerService.getCustomer(Utility.getTokenFromAuthorizationField(authorization));

        //Call AddressService to create a new AddressEntity
        AddressEntity addressEntity =  addressService.getAddressByUUID(addressId, customerEntity);

        AddressEntity deletedAddressEntity = addressService.deleteAddress(addressEntity);

        DeleteAddressResponse deleteAddressResponse = new DeleteAddressResponse().id(UUID.fromString(deletedAddressEntity.getUuid())).status("ADDRESS DELETED SUCCESSFULLY");

        return  new ResponseEntity<DeleteAddressResponse>(deleteAddressResponse, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/states",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<StatesListResponse> getStatesList() {

        List<StateEntity> listStateEntity = addressService.getAllStates();

        List<StatesList> listStatesList = new ArrayList<StatesList>();
        for (StateEntity stateEntity: listStateEntity) {
            listStatesList.add(new StatesList().id(UUID.fromString(stateEntity.getUuid())).stateName(stateEntity.getStateName()));
        }
        if(listStatesList.size() == 0 ) {
            listStatesList = null;
        }
        StatesListResponse statesListResponse = new StatesListResponse().states(listStatesList);
        return new ResponseEntity<StatesListResponse>(statesListResponse, HttpStatus.OK);
    }
}

