package com.bootcamp.deliver.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class warehouse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String country;

    @Column(nullable = false, length = 20)
    private String canton;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 20)
    private String streetnumber;

    @Column(nullable = false, length = 20)
    private Long zipcode;

}
