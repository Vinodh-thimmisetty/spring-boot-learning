package com.vinodh.date_jpa.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author thimmv
 * @createdAt 07-09-2019 19:47
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterAndPagination {
    private String search, sort, fields, offset, limit;
    private List<String> filter;

    private FilterAndPagination formatParams(Map<String, String> queryParams) {
        FilterAndPagination filterAndPagination = new FilterAndPagination();
        filterAndPagination.setSearch(queryParams.getOrDefault("search", ""));
        filterAndPagination.setSearch(queryParams.getOrDefault("sort", ""));
        filterAndPagination.setSearch(queryParams.getOrDefault("offset", ""));
        filterAndPagination.setSearch(queryParams.getOrDefault("limit", ""));
        filterAndPagination.setSearch(queryParams.getOrDefault("fields", ""));
        filterAndPagination.setSearch(queryParams.getOrDefault("filters", ""));
        return null;
    }
}
