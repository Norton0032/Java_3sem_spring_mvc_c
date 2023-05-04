package com.example.java_3sem_spring_mvc.dao;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
    public List<Student> filterStudents(Long id, String firstName, String lastName, String middleName, Long groupId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(root.get("firstName"), "%" + firstName + "%"));
        }

        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(root.get("lastName"), "%" + lastName + "%"));
        }
        if (middleName != null && !middleName.isEmpty()) {
            predicates.add(cb.like(root.get("middleName"), "%" + middleName + "%"));
        }

        if (groupId != null) {
            predicates.add(cb.equal(root.get("groupId"), groupId));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Student> query = session.createQuery(cq);

        return query.getResultList();
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
