package com.vinodh.date_jpa.entity.converters;


import com.vinodh.date_jpa.entity.Gender;

import javax.persistence.AttributeConverter;

/**
 * @author thimmv
 * @createdAt 07-09-2019 09:03
 */
public class GenderTypeConverter implements AttributeConverter<Gender, Character> {

    public Character convertToDatabaseColumn(Gender attribute) {
        return attribute.getCode();
    }

    public Gender convertToEntityAttribute(Character dbData) {
        return Gender.fromCode(dbData);
    }
}