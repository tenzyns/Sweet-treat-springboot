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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="courier_id")
    private Long id;
    @Column(name = "courier_name", nullable = false)
    private String name;
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
    @Column(name = "is_box_refrigerated", nullable = false)
    private Boolean isBoxRefrigerated;
    @Column(name = "max_distance", nullable = false)
    private double maxDistance;
    @Column(name = "rate_per_mile", nullable = false)
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
