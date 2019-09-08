package com.vinodh.date_jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author thimmv
 * @createdAt 07-09-2019 09:26
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class JobProfile {

    @EmbeddedId
    private CompositePrimaryKey id;

    @NonNull
    private String position;
    @NonNull
    private float experience;
    @NonNull
    private BigDecimal salary;

    @ElementCollection(targetClass = String.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> technologies = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumns(value = {
            @JoinColumn(
                    name = "EMPLOYEE_ID",
                    referencedColumnName = "employeeId"),
            @JoinColumn(
                    name = "EMPLOYEE_CODE",
                    referencedColumnName = "employeeCode")
    },
            foreignKey = @ForeignKey(name = "EMPLOYEE_COMPOSITE_JOB_PROFILE_FK"))
    private Employee employee;

    @Override
    public String toString() {
        return "JobProfile{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", experience=" + experience +
                ", salary=" + salary +
                ", technologies=" + technologies +
                '}';
    }
}
