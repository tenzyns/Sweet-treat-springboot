package com.springboot.sweettreat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="courier_id")
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isBoxRefrigerated;
    private double maxDistance;
    private BigDecimal ratePerMile;

    public Courier(){};

    public Courier(String name, String startTime, String endTime, Boolean isBoxRefrigerated, double maxDistance, double ratePerMile) {
        this.name = name;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.isBoxRefrigerated = isBoxRefrigerated;
        this.maxDistance = maxDistance;
        this.ratePerMile = BigDecimal.valueOf(ratePerMile);
    }

    @Override
    public String toString() {
        return "[Courier: " + name + ", cost per mile: £" + ratePerMile + " delivers up to " + maxDistance + " miles]";
    }

}
