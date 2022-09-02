package com.springboot.sweettreat.web;

import com.springboot.sweettreat.entity.Courier;
import com.springboot.sweettreat.exception.CourierNotFoundException;
import com.springboot.sweettreat.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/sweettreat")
public class Controller {
    private CourierService courierService;
    @Autowired
    public void setCourierService(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping("/cheapest/{time}/{distance}/{refrigeration}")
    public ResponseEntity<Courier> getCheapestCourier(@PathVariable("time") String time, @PathVariable("distance") double distance, @PathVariable("refrigeration") boolean refrigeration) {
        try {
            Courier cheapest = courierService.cheapestCourier(time, distance, refrigeration);
            return new ResponseEntity<>(cheapest, HttpStatus.OK);
        } catch (CourierNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No couriers found!", e);
        }
    }

    @GetMapping("/list/{time}/{distance}/{refrigeration}")
    public ResponseEntity<List<Courier>> bestCouriers(@PathVariable("time") String time, @PathVariable("distance") double distance, @PathVariable("refrigeration") boolean refrigeration) {
        try {
            List<Courier> list = courierService.listCouriers(time, distance, refrigeration);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (CourierNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Courier not found!", exception);
        }
    }


    @GetMapping("/courier/{id}")
    public ResponseEntity<Courier> getCourier(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(courierService.findCourier(id), HttpStatus.OK);
        } catch (CourierNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier Not found", exception);
        }
    }

}
