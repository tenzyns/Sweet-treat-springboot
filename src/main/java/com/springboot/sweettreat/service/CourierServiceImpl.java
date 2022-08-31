package com.springboot.sweettreat.service;

import com.springboot.sweettreat.entity.Courier;
import com.springboot.sweettreat.exception.ApplicationNotFoundException;
import com.springboot.sweettreat.repository.CourierRepository;
import com.springboot.sweettreat.web.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.*;
import java.util.stream.Collectors;

@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    private List<Courier> sortedList;
    private BigDecimal courierCost;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    static {
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(Controller.class.getSimpleName() + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
            Filter filterAll = s -> true;
            fileHandler.setFilter(filterAll);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.addHandler(fileHandler);
    }
    public void courierAvailable() {
        //if available return screenedCouriers else return failMsg;
    }
    @Override
    public List<Courier> listCouriers(String time, double distance, boolean refrigeration) {
        List<Courier> list = (List<Courier>) courierRepository.findAll();
        LocalTime orderTime = LocalTime.parse(time);
        ArrayList<Courier> screenedCouriers = new ArrayList<>();
        ArrayList<String> failMsg = new ArrayList<>();

        for (Courier i : list) {
            if(refrigeration && orderTime.isAfter(i.getStartTime()) && orderTime.isBefore(i.getEndTime()) &&
                    i.getIsBoxRefrigerated() && i.getMaxDistance() >= distance) {
                screenedCouriers.add(i);
            } else if(!refrigeration && orderTime.isAfter(i.getStartTime()) && orderTime.isBefore(i.getEndTime())
                    && i.getMaxDistance() >= distance) {
                screenedCouriers.add(i);
            } else if(i.getMaxDistance() < distance) {
                failMsg.add(i.getName() + " can't deliver beyond " + i.getMaxDistance() + " miles");
            } else if(orderTime.isBefore(i.getStartTime()) || orderTime.isAfter(i.getEndTime())) {
                failMsg.add(i.getName() + " is only available for these hours: " + i.getStartTime() +"-" + i.getEndTime());
            }
        }
        if(screenedCouriers.size() == 0) {//if no courier satisfies the requirement
            try {
                throw new IllegalArgumentException("Courier not available for your requirement due to the following reasons: \n" + failMsg);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Unable to select a suitable courier for this order, made at " + orderTime + ", for a distance of " + distance + " miles, refrigeration requirement: " + refrigeration);
                e.printStackTrace();
            }
        } else {

            for (Courier i: screenedCouriers) {
                System.out.println(i);
            }
            System.out.println("<---Before and after sorting ---> ");
            sortedList = screenedCouriers.stream().sorted(Comparator.comparing(Courier::getRatePerMile)).collect(Collectors.toList());
            sortedList.forEach(System.out::println);
        }

//        Courier cheapest = screenedCouriers.get(0);
//        int size = screenedCouriers.size();
//        for (int j = 1; j < size; j++) {
//            if (screenedCouriers.get(j).getRatePerMile().compareTo(cheapest.getRatePerMile()) < 0) {
//                cheapest = screenedCouriers.get(j);
//            }
//        }
//        setCourierCost(BigDecimal.valueOf(distance).multiply(cheapest.getRatePerMile()).setScale(2, RoundingMode.HALF_EVEN));
//        LOGGER.log(Level.INFO, "Most suitable courier for this order is "+ cheapest.getName() + " for the request time " + orderTime + " and will cost £" + courierCost);


        return sortedList;
    }

    private void setCourierCost(BigDecimal cost){
        this.courierCost = cost;
    }
    @Override
    public Courier findCourier(long id) {
        Optional<Courier> optionalCourier = courierRepository.findById(id);
        if(optionalCourier.isPresent())
        return optionalCourier.get();
        else
            throw new ApplicationNotFoundException("Courier not found");
    }
}
