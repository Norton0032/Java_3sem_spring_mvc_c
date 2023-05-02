package com.example.java_3sem_spring_mvc.dao;

import com.example.java_3sem_spring_mvc.model.Group;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupDAO {
    private final SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    public List<Group> getGroupList() {
        return session.createQuery("FROM Group",
                Group.class).getResultList();

    }
    @Transactional
    public void createGroup(Group group) {
        Transaction transaction = session.beginTransaction();
        session.merge(group);
        transaction.commit();
    }
    @Transactional
    public void removeGroup(Long index) {
        Transaction transaction = session.beginTransaction();
        Group team = session.get(Group.class, index);
        session.remove(team);
        transaction.commit();
    }

    public String getGroup(int index){
        return session.get(Group.class, index).toString();
    }
    @Transactional
    public void updateGroup(int index, Group group){
        Transaction tx1 = session.beginTransaction();
        session.update(group);
        tx1.commit();
        session.close();
    }
}
