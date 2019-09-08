package com.vinodh.date_jpa.repositories;

import com.vinodh.date_jpa.entity.CompositePrimaryKey;
import com.vinodh.date_jpa.entity.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thimmv
 * @createdAt 07-09-2019 20:32
 */
@Repository
public interface EmployeeRepositories extends PagingAndSortingRepository<Employee, CompositePrimaryKey> {

    @EntityGraph(attributePaths = {"phones", "jobProfile"})
    List<Employee> findByIdEmployeeCode(long empCode);


}
