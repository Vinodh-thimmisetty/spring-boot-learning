package com.vinodh.date_jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author thimmv
 * @createdAt 07-09-2019 20:03
 */
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    @NonNull
    private String statusCode, errorMessage, errorDescription;
}
