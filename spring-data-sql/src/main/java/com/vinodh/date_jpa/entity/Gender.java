package com.vinodh.date_jpa.entity;

/**
 * @author thimmv
 * @createdAt 03-09-2019 10:50
 */
public enum Gender {
    MALE('M'), FEMALE('F'), OTHER('O');

    private final char code;

    Gender(char code) {
        this.code = code;
    }

    public static Gender fromCode(char code) {
        switch (code) {
            case 'm':
            case 'M':
                return MALE;
            case 'f':
            case 'F':
                return FEMALE;
            default:
                return OTHER;
        }
    }

    public char getCode() {
        return code;
    }
}
