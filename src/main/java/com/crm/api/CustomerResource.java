package com.crm.api;

import com.crm.api.request.CreateCustomerRequest;
import com.crm.api.request.PatchCustomerRequest;
import com.crm.api.request.PatchCustomerStatusRequest;
import com.crm.api.response.ApiResponse;
import com.crm.api.response.CustomerResponse;
import com.crm.api.response.MetaResponse;
import com.crm.api.response.PagedMetaResponse;
import com.crm.service.CustomerService;
import com.crm.util.TimeUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/api/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @POST
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP"})
    public Response create(@NotNull @Valid CreateCustomerRequest request) {
        CustomerResponse data = customerService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso())))
                .build();
    }

    @GET
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP"})
    public Response list(
            @QueryParam("q") String q,
            @QueryParam("status") String status,
            @QueryParam("segment") String segment,
            @QueryParam("ownerUserId") UUID ownerUserId,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("size") int size
    ) {
        List<CustomerResponse> data = customerService.list(q, status, segment, ownerUserId, page, size);
        long totalElements = customerService.count(q, status, segment, ownerUserId);
        int totalPages = (int) Math.ceil((double) totalElements / size);

        return Response.ok(new ApiResponse<>(data,
                        new PagedMetaResponse(TimeUtil.nowIso(), page, size, totalElements, totalPages)))
                .build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP"})
    public Response getById(@PathParam("id") UUID id) {
        CustomerResponse data = customerService.getById(id);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP"})
    public Response patch(@PathParam("id") UUID id, @NotNull @Valid PatchCustomerRequest request) {
        CustomerResponse data = customerService.patch(id, request);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @PATCH
    @Path("/{id}/status")
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP"})
    public Response patchStatus(@PathParam("id") UUID id, @NotNull @Valid PatchCustomerStatusRequest request) {
        CustomerResponse data = customerService.patchStatus(id, request);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }
}