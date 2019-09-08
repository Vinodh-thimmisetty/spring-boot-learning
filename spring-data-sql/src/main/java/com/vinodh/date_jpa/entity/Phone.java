package com.vinodh.date_jpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

/**
 * @author thimmv
 * @createdAt 06-09-2019 18:34
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor()
@EqualsAndHashCode(of = "{phoneNo}")
public class Phone {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String phoneNo, network, state;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns(value = {
            @JoinColumn(
                    name = "EMPLOYEE_ID",
                    referencedColumnName = "employeeId"),
            @JoinColumn(
                    name = "EMPLOYEE_CODE",
                    referencedColumnName = "employeeCode")
    },
            foreignKey = @ForeignKey(name = "EMPLOYEE_COMPOSITE_PHONE_FK"))
    private Employee employee;

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", phoneNo='" + phoneNo + '\'' +
                ", network='" + network + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
