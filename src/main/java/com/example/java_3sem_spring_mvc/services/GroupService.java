package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;
    private final EmailService emailService;
    @Value("Max32rus37@yandex.ru")
    private String EMAIL;


    @Transactional(readOnly = true)
    public List<Group> filterGroups(Long id, String name) {
        if (id == null && name == null) {
            log.info("Группы выведены без фильтра");
            return groupRepository.findAll();
        } else {
            log.info("Группы выведены с фильтром: id=" + id + " name=" + name);
            return groupRepository.findByIdOrGroupNameContaining(id, name);
        }
    }
    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Transactional
    public void createGroup(Group group) {
        groupRepository.save(group);
        log.info("Группа создана");
        //emailService.sendEmail(EMAIL, "Группа создана", group.toString());
    }

    @Transactional
    public void removeGroup(Long index) {
        log.info("Группа удалена id=: " + index);
        groupRepository.deleteById(index);
    }

    @Transactional(readOnly = true)
    public String getGroup(Long index){
        log.info("Зайшли в группу по id=: " + index);
        return groupRepository.findById(index).toString();
    }

    public void updateGroup(int index, Group group){

    }
}
