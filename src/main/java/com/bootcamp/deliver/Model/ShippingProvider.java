package com.bootcamp.deliver.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ShippingProvider {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private Double initialPrice;

    @Column
    private Double initialDistance;

    @Column
    private Double ratePerKilo;

    @Column
    private Double ratePerPallet;

    @Column
    private Double discountFactor;

  
}
