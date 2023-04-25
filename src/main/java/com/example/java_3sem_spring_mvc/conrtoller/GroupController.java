package com.example.java_3sem_spring_mvc.conrtoller;


import com.example.java_3sem_spring_mvc.model.Group;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/groups")
public class GroupController {
    private final List<Group> groupList = new ArrayList<>();

    @GetMapping
    public List<Group> getGroupList() {
        return groupList;
    }

    @PostMapping
    public void createGroup(@RequestBody Group group) {
        groupList.add(group);
    }

    @DeleteMapping("/{index}")
    public void deleteGroup(@PathVariable int index){
        groupList.remove(index);
    }
}
