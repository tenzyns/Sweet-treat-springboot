package com.springboot.sweettreat.entity;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
    @NotEmpty(message = "name should be valid")
    private String name;
    @NotNull (message = "Start-time should be valid")
    private LocalTime startTime;
    @NotNull(message = "End-time should be valid")
    private LocalTime endTime;
    @NotNull(message = "true or false should be valid")
    private Boolean isBoxRefrigerated;
    @Positive(message = "Max distance should be valid")
    private double maxDistance;
    @NotNull(message = "rate should be valid")
    @Positive
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
