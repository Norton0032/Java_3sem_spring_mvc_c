package com.example.java_3sem_spring_mvc.dao;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Table;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentDAO {
    private final SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    public List<Student> getStudentList() {
        return session.createQuery("FROM Student",
                Student.class).getResultList();

    }
    @Transactional
    public void createStudent(Student student) {
        Transaction transaction = session.beginTransaction();
        session.merge(student);
        transaction.commit();
    }
    @Transactional
    public void removeStudent(Long index) {
        Transaction transaction = session.beginTransaction();
        Student team = session.get(Student.class, index);
        session.remove(team);
        transaction.commit();
    }

    public String getStudent(int index){
        return session.get(Student.class, index).toString();
    }
    @Transactional
    public void updateStudent(int index, Student student){
        Transaction tx1 = session.beginTransaction();
        session.update(student);
        tx1.commit();
        session.close();
    }
}
