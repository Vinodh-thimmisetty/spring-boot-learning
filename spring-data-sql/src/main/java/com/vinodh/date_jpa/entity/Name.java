package com.vinodh.date_jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * @author thimmv
 * @createdAt 02-09-2019 21:26
 */
@Embeddable
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Name {
    @NonNull
    private String fName, mName, lName;
}
