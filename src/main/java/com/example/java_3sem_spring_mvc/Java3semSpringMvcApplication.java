package com.example.java_3sem_spring_mvc;

import com.example.java_3sem_spring_mvc.dao.StudentDAO;
import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class Java3semSpringMvcApplication {
    //http://localhost:8080
    public static void main(String[] args) {
        SpringApplication.run(Java3semSpringMvcApplication.class, args);

    }

}
