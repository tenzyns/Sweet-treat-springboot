package com.springboot.sweettreat.service;

import com.springboot.sweettreat.entity.Courier;
import com.springboot.sweettreat.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    @Override
    public List<Courier> listCouriers() {
        return (List<Courier>) courierRepository.findAll();
    }

    @Override
    public Courier findCourier(long id) throws ClassNotFoundException {
        Optional<Courier> optionalCourier = courierRepository.findById(id);
        if(optionalCourier.isPresent())
        return optionalCourier.get();
        else
            throw new ClassNotFoundException("Courier not found");
    }
}
