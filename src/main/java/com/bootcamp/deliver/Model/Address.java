package com.bootcamp.deliver.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

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
