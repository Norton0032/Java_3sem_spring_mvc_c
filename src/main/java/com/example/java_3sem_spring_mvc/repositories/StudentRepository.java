package com.example.java_3sem_spring_mvc.repositories;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByIdOrFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrGroup(Long id,
                                                                                                 String firstName,
                                                                                                 String lastName,
                                                                                                 String middleName,
                                                                                                 Group group);
}



