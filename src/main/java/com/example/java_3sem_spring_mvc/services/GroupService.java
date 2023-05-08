package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;


    public List<Group> filterGroups(Long id, String name) {
        if (id == null && name == null) {
            log.info("Группы выведены без фильтра");
            return groupRepository.findAll();
        } else {
            log.info("Группы выведены с фильтром: id=" + id + " name=" + name);
            return groupRepository.findByIdOrGroupNameContaining(id, name);
        }
    }

    public void createGroup(Group group) {
        log.info("Группа создана");
        groupRepository.save(group);
    }

    public void removeGroup(Long index) {
        log.info("Группа удалена id=: " + index);
        groupRepository.deleteById(index);
    }

    public String getGroup(Long index){
        log.info("Зайшли в группу по id=: " + index);
        return groupRepository.findById(index).toString();
    }

    public void updateGroup(int index, Group group){

    }
}
