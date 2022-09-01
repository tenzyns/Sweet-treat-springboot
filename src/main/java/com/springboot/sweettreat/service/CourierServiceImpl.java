package com.springboot.sweettreat.service;

import com.springboot.sweettreat.entity.Courier;
import com.springboot.sweettreat.exception.CourierNotFoundException;
import com.springboot.sweettreat.repository.CourierRepository;
import com.springboot.sweettreat.web.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public List<Courier> availableCouriers(String time, double distance, boolean refrigeration) {
        //if available return screenedCouriers else return failMsg;
        List<Courier> list = (List<Courier>) courierRepository.findAll();
        LocalTime orderTime = LocalTime.parse(time);
        ArrayList<Courier> screenedCouriers = new ArrayList<>();

        for (Courier i : list) {
            if(refrigeration && orderTime.isAfter(i.getStartTime()) && orderTime.isBefore(i.getEndTime()) &&
                    i.getIsBoxRefrigerated() && i.getMaxDistance() >= distance) {
                screenedCouriers.add(i);
            } else if(!refrigeration && orderTime.isAfter(i.getStartTime()) && orderTime.isBefore(i.getEndTime())
                    && i.getMaxDistance() >= distance) {
                screenedCouriers.add(i);
            }
        }
        return screenedCouriers;
    }
    @Override
    public List<Courier> listCouriers(String time, double distance, boolean refrigeration) {
        List<Courier> screenedList = availableCouriers(time, distance, refrigeration);
        List<Courier> sortedList = new ArrayList<>();

        if (screenedList.size() == 0) {//if no courier satisfies the requirement

            LOGGER.log(Level.WARNING, "Unable to select a suitable courier for this order, made at " + time + ", for a distance of " + distance + " miles, refrigeration requirement: " + refrigeration);

            throw new IllegalArgumentException("Courier not available for your requirement due to time or distance constraint");

        } else {
            for (Courier i : screenedList) {
                System.out.println(i);
            }
            System.out.println("<---Before and after sorting ---> ");
            sortedList = screenedList.stream().sorted(Comparator.comparing(Courier::getRatePerMile)).collect(Collectors.toList());
            sortedList.forEach(System.out::println);
            return sortedList;
        }

    }
    @Override
    public Courier cheapestCourier(String time, double distance, boolean refrigeration) {
        List<Courier> screenedList = availableCouriers(time, distance, refrigeration);
        int size = screenedList.size();
        if (size == 0) {
            throw new IllegalArgumentException("Courier not available due to time and/or distance constraint");
        } else {
            Courier cheapest = screenedList.get(0);
            for (int j = 1; j < size; j++) {
                if (screenedList.get(j).getRatePerMile().compareTo(cheapest.getRatePerMile()) < 0) {
                    cheapest = screenedList.get(j);
                }
            }
            setCourierCost(BigDecimal.valueOf(distance).multiply(cheapest.getRatePerMile()).setScale(2, RoundingMode.HALF_EVEN));
            LOGGER.log(Level.INFO, "Most suitable courier for this order is " + cheapest.getName() + " for the request time " + time + " and will cost £" + courierCost);
            return cheapest;
        }
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
            throw new CourierNotFoundException("Courier not found");
    }
}
