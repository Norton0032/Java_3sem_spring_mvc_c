package com.example.java_3sem_spring_mvc.repositories;

import com.example.java_3sem_spring_mvc.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByIdOrCourseNameContaining(Long id, String courseName);
}


