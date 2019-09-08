package com.vinodh.date_jpa;

import com.vinodh.date_jpa.entity.CompositePrimaryKey;
import com.vinodh.date_jpa.entity.Employee;
import com.vinodh.date_jpa.entity.Gender;
import com.vinodh.date_jpa.entity.JobProfile;
import com.vinodh.date_jpa.entity.Name;
import com.vinodh.date_jpa.entity.Phone;
import com.vinodh.date_jpa.repositories.EmployeeRepositories;
import com.vinodh.date_jpa.repositories.FilterRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories
public class SpringDataSqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataSqlApplication.class, args);
    }


    @Autowired
    EmployeeRepositories employeeRepositories;

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    FilterRepository filterRepository;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CommandLineRunner commandLineRunner() {
        return (args) -> {

            Employee e1 = new Employee();
            e1.setId(new CompositePrimaryKey(1234l, 12l));
            e1.setGender(Gender.MALE);
            e1.setEmail("vinodh@gmail.com");
            e1.setName(new Name("Vinodh", "Kumar", "Thimmisetty"));
            e1.addPhone(new Phone("1234567890", "BSNL", "AP"));
            e1.addJobProfile(new JobProfile("Sr.Engineer", 5.5f, BigDecimal.valueOf(10_00_000L)));

            EntityManager em = emf.createEntityManager();
            Session session = em.unwrap(Session.class);
            Transaction tx = session.beginTransaction();
            session.save(e1);
            tx.commit();
        };
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public CommandLineRunner commandLineRunner1() {
        return args -> {
            Optional<Employee> emp = employeeRepositories.findById(new CompositePrimaryKey(1234l, 12l));
            emp.ifPresent(System.out::println);

            List<Employee> employees = employeeRepositories.findByIdEmployeeCode(12l);
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }

            List<Employee> eList = filterRepository.applyEmployeeFilter("Vinodh",
                    "Thimmisetty", "vinodh@gmail.com",
                    new SimpleDateFormat("yyyy-mm-dd").parse("2019-09-08"));
            for (Employee employee : eList) {
                System.out.println(employee.toString());
            }

        };
    }
}
