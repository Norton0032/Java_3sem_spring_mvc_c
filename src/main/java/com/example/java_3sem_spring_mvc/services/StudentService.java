package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import com.example.java_3sem_spring_mvc.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final EmailService emailService;
    @Value("Max32rus37@yandex.ru")
    private String EMAIL;

    @Transactional
    public List<Student> filterStudents(Long id, String firstName, String lastName, String middleName, Group group) {
        if (id == null && firstName == null && lastName == null && group == null && middleName == null) {
            log.info("Студенты выведены без фильтра");
            return studentRepository.findAll();
        } else {
            log.info("Студенты выведены с фильтром id=" + id + " name=" + firstName+" "+ lastName+" "+middleName + " groups=" + group);
            return studentRepository.findByIdOrFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrGroup(id, firstName, lastName, middleName, group);
        }
    }

    @Transactional
    public void createStudent(Student student) {

        studentRepository.save(student);
        log.info("Студент создан");
        emailService.sendEmail(EMAIL, "Студент создан", student.toString());
    }

    @Transactional
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
