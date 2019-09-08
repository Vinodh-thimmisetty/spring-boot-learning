package com.vinodh.date_jpa.controller;

import com.vinodh.date_jpa.domain.EmployeeDTO;
import com.vinodh.date_jpa.domain.SortOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vinodh.date_jpa.domain.SortOrder.ASC;
import static com.vinodh.date_jpa.domain.SortOrder.DESC;
import static org.springframework.util.StringUtils.split;

/**
 * @author thimmv
 * @createdAt 07-09-2019 19:38
 */
@RestController
@RequestMapping("/{version}/employee")
public class EmployeeController {

    @GetMapping("/")
    public ResponseEntity<?> getAllEmployees(@RequestParam(required = false) Map<String, String> queryParams) {
        return ResponseEntity.ok()
                .eTag("sampleEtagCode")
                .lastModified(Instant.EPOCH)
                .body(null);
    }

    @GetMapping("/recently_joined")
    public ResponseEntity<?> recentlyJoinedEmployees(@RequestParam(required = false) Map<String, String> queryParams) {
        return null;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getAllEmployees(@PathVariable String employeeId, @RequestParam(required = false) Map<String, String> queryParams) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveNewEmployee(@RequestBody EmployeeDTO employee) {
        // It should give the updated response back to user.
        return null;
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable String employeeId,
                                            @RequestBody EmployeeDTO employee) {
        // It should give the updated response back to user.
        return null;
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<?> partialUpdateEmployee(@PathVariable String employeeId,
                                                   @RequestBody EmployeeDTO employee) {
        // It should give the updated response back to user.
        return null;
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeId) {
        return null;
    }

    private void parseParams(Map<String, String> qParams) {
        Map<String, SortOrder> sortBy;
        Map<String, String> pagination;
        Set<String> fields;
        String search;

        // Sorting: - indicates Desc order. Multiple fields will be separated by 'COMMA(,)'
        if (qParams.containsKey("sort")) {
            sortBy = new LinkedHashMap<>(); // Order of sorting matters.
            String sort = qParams.remove("sort");
            String[] sortedFields = split(sort, ",");
            for (String field : sortedFields) {
                if ('-' == field.indexOf(0)) {
                    sortBy.put(field.substring(1), DESC);
                } else {
                    sortBy.put(field, ASC);
                }
            }
        }

        // pagination: offset indicates current page; limit indicates no.of records allowed per page.
        if (qParams.containsKey("offset")) {
            pagination = new LinkedHashMap<>();
            pagination.put("offset", qParams.remove("offset"));
            pagination.put("limit", qParams.remove("limit"));
        }

        // List of fields user want's in API response.
        if (qParams.containsKey("fields")) {
            fields = Stream.of(qParams.remove("fields").split(","))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        // Text Search applying REGEX
        if (qParams.containsKey("search")) {
            search = qParams.remove("search");
        }

        // Other than the above query params, remaining are considered as FILTER params.
        Map<String, String> filterBy = qParams;

    }


}
