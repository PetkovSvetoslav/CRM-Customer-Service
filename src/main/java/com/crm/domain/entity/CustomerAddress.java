package com.crm.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CustomerAddress {

    @Column(name = "address_country", length = 100)
    public String country;

    @Column(name = "address_city", length = 100)
    public String city;

    @Column(name = "address_postal_code", length = 20)
    public String postalCode;

    @Column(name = "address_line1", length = 255)
    public String line1;

    @Column(name = "address_line2", length = 255)
    public String line2;
}