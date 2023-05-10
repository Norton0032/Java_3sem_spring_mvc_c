package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.repositories.GroupRepository;
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
public class GroupService {
    private final GroupRepository groupRepository;



    @Transactional
    public List<Group> filterGroups(Long id, String name) {
        if (id == null && name == null) {
            log.info("Группы выведены без фильтра");
            return groupRepository.findAll();
        } else {
            log.info("Группы выведены с фильтром: id=" + id + " name=" + name);
            return groupRepository.findByIdOrGroupNameContaining(id, name);
        }
    }

    @Transactional
    public void createGroup(Group group) {
        groupRepository.save(group);
        log.info("Группа создана");
    }

    @Transactional
    public void removeGroup(Long index) {
        log.info("Группа удалена id=: " + index);
        groupRepository.deleteById(index);
    }

    public Optional<Group> getGroup(Long index){
        log.info("Зайшли в группу по id=: " + index);
        return groupRepository.findById(index);
    }

    public void updateGroup(Long index, Group group){
        Group group_for_update = groupRepository.findById(index).get();
        group_for_update.setGroupName(group.getGroupName());
        groupRepository.save(group_for_update);
    }
}
