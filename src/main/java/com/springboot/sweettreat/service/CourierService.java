package com.springboot.sweettreat.service;

import com.springboot.sweettreat.entity.Courier;

import java.util.List;

public interface CourierService {
    List<Courier> listCouriers();

    Courier findCourier(long id) throws ClassNotFoundException;
}
