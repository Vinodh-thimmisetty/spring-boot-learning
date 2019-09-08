package com.vinodh.date_jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author thimmv
 * @createdAt 07-09-2019 11:10
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@DiscriminatorValue("SW")
public class SoftwareEmployee extends Employee {

    private String ideUsed;
    private boolean isGeek;
}

