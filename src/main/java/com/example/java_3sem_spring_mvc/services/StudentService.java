package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import com.example.java_3sem_spring_mvc.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> filterStudents(Long id, String firstName, String lastName, String middleName, Group group) {
        if (id == null && firstName == null && lastName == null && group == null && middleName == null) {
            return studentRepository.findAll();
        } else {
            return studentRepository.findByIdOrFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrGroup(id, firstName, lastName, middleName, group);
        }
    }

    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    public void removeStudent(Long index) {
        studentRepository.deleteById(index);
    }

    public String getStudent(Long index){
        return studentRepository.findById(index).toString();

    }

    public void updateStudent(int index, Student student){

    }

}
