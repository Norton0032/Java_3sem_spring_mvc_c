package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import com.example.java_3sem_spring_mvc.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> filterStudents(Long id, String firstName, String lastName, String middleName, Group group) {
        if (id == null && firstName == null && lastName == null && group == null && middleName == null) {
            log.info("Студенты выведены без фильтра");
            return studentRepository.findAll();
        } else {
            log.info("Студенты выведены с фильтром id=" + id + " name=" + firstName+" "+ lastName+" "+middleName + " groups=" + group);
            return studentRepository.findByIdOrFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrGroup(id, firstName, lastName, middleName, group);
        }
    }

    public void createStudent(Student student) {

        log.info("Студент создан");
        studentRepository.save(student);
    }

    public void removeStudent(Long index) {

        log.info("Студент удалён по id: " + index);
        studentRepository.deleteById(index);
    }

    public String getStudent(Long index){
        log.info("Зайшли на страничку студента по id=: " + index);
        return studentRepository.findById(index).toString();

    }

    public void updateStudent(int index, Student student){

    }

}
