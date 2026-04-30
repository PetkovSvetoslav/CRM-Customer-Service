package com.crm.api.response;

public class PagedMetaResponse extends MetaResponse {
    public int page;
    public int size;
    public long totalElements;
    public int totalPages;

    public PagedMetaResponse() {
    }

    public PagedMetaResponse(String timestamp, int page, int size, long totalElements, int totalPages) {
        super(timestamp);
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}