package com.springboot.sweettreat.web;

import com.springboot.sweettreat.entity.Courier;
import com.springboot.sweettreat.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class Controller {

    private CourierService courierService;

    @Autowired
    public void setCourierService(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping("/couriers")
    public ResponseEntity<List<Courier>> getAllCouriers() {
        List<Courier> list = courierService.listCouriers();
        return new ResponseEntity<List<Courier>>(list, HttpStatus.OK);
    }

    @GetMapping("/courier/{id}")
    public ResponseEntity<Courier> getCourier(@PathVariable("id") long id)  {
        try {
            return new ResponseEntity<Courier>(courierService.findCourier(id), HttpStatus.OK);
        } catch (ClassNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier Not found");
        }
    }

}
