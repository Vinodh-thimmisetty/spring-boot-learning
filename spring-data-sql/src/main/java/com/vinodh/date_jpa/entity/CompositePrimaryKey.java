package com.vinodh.date_jpa.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author thimmv
 * @createdAt 07-09-2019 11:00
 */
@Embeddable
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class CompositePrimaryKey implements Serializable {

    @NonNull
    private Long employeeId, employeeCode;
}
