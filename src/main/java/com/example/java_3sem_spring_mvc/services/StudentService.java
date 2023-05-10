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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final EmailService emailService;
    @Transactional
    public List<Student> filterStudents(Long id, String firstName, String lastName, String middleName, Group group) {
        if (id == null && firstName == null && lastName == null && group == null && middleName == null) {
            log.info("Студенты выведены без фильтра");
            return studentRepository.findAll();
        } else {
            log.info("Студенты выведены с фильтром id=" + id + " name=" + firstName+" "+ lastName+" "+middleName + " groups=" + group);
            return studentRepository.findByIdOrFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrGroup(id,
                    firstName, lastName, middleName, group);
        }
    }
    @Transactional
    public void createStudent(Student student, String group, String course) {
        studentRepository.save(student);
        log.info("Студент создан");
        if (student.getEmail_send() != null) {
            emailService.sendEmail(student.getEmail(), "Вы зачислены на курс по подготовки к ЕГЭ",
                    "Вас зачислили на курс "+course+ ", ваша группы "+ group);
        }
    }
    @Transactional
    public void removeStudent(Long index) {

        log.info("Студент удалён по id: " + index);
        studentRepository.deleteById(index);
    }

    public Optional<Student> getStudent(Long index){
        log.info("Зайшли на страничку студента по id=: " + index);
        return studentRepository.findById(index);

    }

    public void updateStudent(Long index, Student student){
        Student student_for_update = studentRepository.findById(index).get();
        student_for_update.setFirstName(student.getFirstName());
        student_for_update.setLastName(student.getLastName());
        student_for_update.setMiddleName(student.getMiddleName());
        student_for_update.setEmail(student.getEmail());
        studentRepository.save(student_for_update);
    }

}
