package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;


    public List<Group> filterGroups(Long id, String name) {
        if (id == null && name == null) {
            return groupRepository.findAll();
        } else {
            return groupRepository.findByIdOrGroupNameContaining(id, name);
        }
    }

    public void createGroup(Group group) {
        groupRepository.save(group);
    }

    public void removeGroup(Long index) {
        groupRepository.deleteById(index);
    }

    public String getGroup(Long index){
        return groupRepository.findById(index).toString();
    }

    public void updateGroup(int index, Group group){

    }
}
