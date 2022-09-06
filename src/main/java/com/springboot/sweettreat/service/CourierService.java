package com.springboot.sweettreat.service;

import com.springboot.sweettreat.entity.Courier;

import java.math.BigDecimal;
import java.util.List;

public interface CourierService {
// fetching all available couriers in a list in order of increasing price
    List<Courier> listCouriers(String time, double distance, boolean refrigeration);

    Courier cheapestCourier(String time, double distance, boolean refrigeration);

    Courier findCourier(long id);
//    adding a new courier
    Courier addCourier(Courier courier);

    void deleteCourierById(Long id);

    //    Updating operation
    Courier updateCourierById(Courier newDetail, Long id);
}
