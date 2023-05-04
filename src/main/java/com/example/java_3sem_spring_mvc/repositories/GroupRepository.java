package com.example.java_3sem_spring_mvc.repositories;

import com.example.java_3sem_spring_mvc.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByIdOrGroupNameContaining(Long id, String groupName);

}
