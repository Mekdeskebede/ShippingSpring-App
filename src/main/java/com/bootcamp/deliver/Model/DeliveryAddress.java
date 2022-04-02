package com.bootcamp.deliver.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String Country;

    @Column(nullable = false, length = 20)
    private String Canton;

    @Column(nullable = false, length = 20)
    private String City;

    @Column(nullable = false, length = 20)
    private String Streetnumber;

    @Column(nullable = false, length = 20)
    private Long Zipcode;

}