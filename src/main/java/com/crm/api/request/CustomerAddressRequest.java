package com.crm.api.request;

import jakarta.validation.constraints.Size;

public class CustomerAddressRequest {

    @Size(max = 100, message = "Country must be at most 100 characters")
    public String country;

    @Size(max = 100, message = "City must be at most 100 characters")
    public String city;

    @Size(max = 20, message = "Postal code must be at most 20 characters")
    public String postalCode;

    @Size(max = 255, message = "Address line1 must be at most 255 characters")
    public String line1;

    @Size(max = 255, message = "Address line2 must be at most 255 characters")
    public String line2;
}