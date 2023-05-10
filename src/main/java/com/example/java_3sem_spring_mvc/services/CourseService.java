package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Course;
import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    @Transactional
    public List<Course> filterCourses(Long id, String name) {
        if (id == null && name == null) {
            log.info("Курсы выведены без фильтра");
            return courseRepository.findAll();
        } else {
            log.info("Курсы выведены с фильтром: id=" + id + " name=" + name);
            return courseRepository.findByIdOrCourseNameContaining(id, name);
        }
    }
    @Transactional
    public void createCourse(Course course) {
        courseRepository.save(course);
        log.info("Курс создан");
    }
    @Transactional
    public void removeCourse(Long index) {
        log.info("Курс удален id=: " + index);
        courseRepository.deleteById(index);
    }
    public Optional<Course> getCourse(Long index){
        log.info("Зайшли в курс по id=: " + index);
        return courseRepository.findById(index);
    }
    public void updateCourse(Long index, Course course){
        Course course_for_update = courseRepository.findById(index).get();
        course_for_update.setCourseName(course.getCourseName());
        course_for_update.setPrice(course.getPrice());
        courseRepository.save(course_for_update);
    }
}
