package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private StateDao stateDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, CustomerEntity customerEntity) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity deleteAddress(AddressEntity addressEntity) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity getAddressByUUID(String uuid, CustomerEntity customerEntity) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<AddressEntity> getAllAddress(CustomerEntity customerEntity) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<StateEntity> getAllStates() {
        return stateDao.getAllState();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public StateEntity getStateByUUID(String uuid) {
        return stateDao.getStateByUuid(uuid);
    }
}
