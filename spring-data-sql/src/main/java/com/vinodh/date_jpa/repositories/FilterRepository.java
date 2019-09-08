package com.vinodh.date_jpa.repositories;

/**
 * @author thimmv
 * @createdAt 07-09-2019 20:49
 */

import com.vinodh.date_jpa.entity.Employee;
import com.vinodh.date_jpa.entity.Employee_;
import com.vinodh.date_jpa.entity.Name_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FilterRepository {

    @Autowired
    private EntityManager entityManager;


    public List<Employee> applyEmployeeFilter(String firstName,
                                              String lastName,
                                              String email,
                                              Date joinDateTime) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Employee> ecq = cb.createQuery(Employee.class);
        final Root<Employee> employee = ecq.from(Employee.class);


        List<Predicate> filters = new ArrayList<>();

        if (!StringUtils.isEmpty(firstName)) {
            filters.add(cb.equal(cb.upper(employee.get(Employee_.NAME).get(Name_.F_NAME)), firstName.toUpperCase()));
        }

        if (!StringUtils.isEmpty(lastName)) {
            filters.add(cb.equal(cb.upper(employee.get(Employee_.NAME).get(Name_.L_NAME)), lastName.toUpperCase()));
        }

        if (!StringUtils.isEmpty(email)) {
            filters.add(cb.equal(cb.upper(employee.get(Employee_.EMAIL)), email.toUpperCase()));
        }

        if (!StringUtils.isEmpty(joinDateTime)) {
            filters.add(cb.greaterThanOrEqualTo(employee.get(Employee_.joinDate), joinDateTime));
        }

        final CriteriaQuery<Employee> selectQuery = ecq
                .select(employee)
                .where(filters.toArray(new Predicate[filters.size()]));

        // javax.persistence.fetchgraph: Specified Nodes use EAGER; remaining LAZY
        // javax.persistence.loadgraph: Specified Nodes use EAGER; remaining depends on their own fetch type
        return entityManager
                .createQuery(selectQuery)
                .setHint("javax.persistence.loadgraph", entityManager.getEntityGraph("phones-jobProfiles"))
                .getResultList();

    }
}
