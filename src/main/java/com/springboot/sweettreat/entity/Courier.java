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
    @NotBlank(message = "Name must be provided.")
    private String name;
    @NotNull(message = "A valid start-time must be provided.")
    private LocalTime startTime;
    @NotNull(message = "A valid end-time must be provided.")
    private LocalTime endTime;
    @NotNull(message = "A true or false value must be provided.")
    private Boolean isBoxRefrigerated;
    @Positive(message = "Max distance must be positive value.")
    private double maxDistance;
    @NotNull(message = "Rate must be provided.")
    @Positive(message = "Rate must be a positive value.")
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
