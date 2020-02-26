package com.asiewiera.climb4youapp.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(length = 100, name = "street")
    private String street;

    @Column(length = 20, name = "street_number")
    private String streetNumber;

    @Column(name = "flat_number")
    private Integer flatNumber;

}
