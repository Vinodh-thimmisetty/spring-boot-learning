package com.vinodh.date_jpa.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vinodh.date_jpa.entity.converters.GenderTypeConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author thimmv
 * @createdAt 07-09-2019 07:55
 */
@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedEntityGraphs({
        @NamedEntityGraph(name = "phones-jobProfiles", attributeNodes = {
                @NamedAttributeNode("phones"),
                @NamedAttributeNode("jobProfile")
        })
})
public class Employee {

    @EmbeddedId
    private CompositePrimaryKey id;

    @Enumerated(value = EnumType.STRING)
    @Convert(converter = GenderTypeConverter.class)
    private Gender gender;

    @Email
    @NonNull
    private String email;

    @Embedded
    private Name name;

    @JsonManagedReference("employee")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Set<Phone> phones = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee", orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private JobProfile jobProfile;

    @Temporal(value = TemporalType.DATE)
    private Date joinDate = new Date();

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setEmployee(this);
    }

    public void removePhone(Phone phone) {
        phones.remove(phone);
        phone.setEmployee(null);
    }

    public void addJobProfile(JobProfile jobProfile) {
        jobProfile.setEmployee(this);
        this.jobProfile = jobProfile;
    }

    public void removeJobProfile() {
        if (this.jobProfile != null) {
            this.jobProfile.setEmployee(null);
            this.jobProfile = null;
        }
    }


}
