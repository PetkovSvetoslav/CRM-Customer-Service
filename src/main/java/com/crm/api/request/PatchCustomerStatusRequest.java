package com.crm.api.request;

import jakarta.validation.constraints.NotNull;

public class PatchCustomerStatusRequest {

    @NotNull(message = "Status is required")
    public String status;
}