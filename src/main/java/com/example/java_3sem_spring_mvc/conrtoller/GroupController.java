package com.example.java_3sem_spring_mvc.conrtoller;


import com.example.java_3sem_spring_mvc.model.Course;
import com.example.java_3sem_spring_mvc.model.Student;
import com.example.java_3sem_spring_mvc.services.CourseService;
import com.example.java_3sem_spring_mvc.services.GroupService;
import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private StudentService studentService;
    @GetMapping
    public String getGroupList_with_paramName(@RequestParam(value = "name", required = false) String i,
                                              @RequestParam(required = false) Long id,
                                              @RequestParam(required = false) String groupName,
                                              Model model) {
        model.addAttribute("GroupsName", i);
        model.addAttribute("Groups", groupService.filterGroups(id, groupName));
        return "group/groups";
    }
    @GetMapping("/{index}")
    public String getGroup(@PathVariable Long index, Model model){
        Group group = groupService.getGroup(index).get();
        model.addAttribute("Group", group);
        model.addAttribute("GroupIndex", index);
        return "group/group";
    }
    @GetMapping("/new")
    public String newGroup(Model model){
        model.addAttribute("newGroup", new Group());
        return "group/newGroup";
    }
    @GetMapping("/{index}/edit")
    public String editGroup(Model model, @PathVariable Long index){
        model.addAttribute("GroupEdit", groupService.getGroup(index).get());
        model.addAttribute("GroupEditIndex", index);
        return "group/editGroup";
    }
    @PostMapping
    public void createGroup(@RequestBody Group group) {
        boolean fl = false;
        Long id_cr = group.getCourse().getId();
        List<Course> c= courseService.filterCourses(null, null);
        for (int i = 0;i<c.size();i++){
            if (c.get(i).getId()==id_cr){
                fl = true;
                break;
            }}
        if (fl != false){
            groupService.createGroup(group);
        }}
    @PostMapping("/new")
    public String createGroup_with_get(@ModelAttribute("newGroup") @Valid Group group,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "group/newGroup";}
        boolean fl = false;
        Long id_cr = group.getCourse().getId();
        List<Course> c= courseService.filterCourses(null, null);
        for (int i = 0;i<c.size();i++){
            if (c.get(i).getId()==id_cr){
                fl = true;
                break;
            }}
        if (fl == false){
            return "group/newGroup";}
        groupService.createGroup(group);
        return "redirect:/groups";
    }
    @PatchMapping("/{index}")
    public String updateGroup(@ModelAttribute("GroupEdit") @Valid Group group,
                              BindingResult bindingResult, @PathVariable Long index){
        if (bindingResult.hasErrors()){
            return "group/editGroup";
        }
        groupService.updateGroup(index, group);
        return "redirect:/groups";
    }
    @DeleteMapping("/{index}")
    public String deleteGroup(@PathVariable Long index){
        Group g = groupService.getGroup(index).get();
        List<Student> st_for_rem = g.getStudents();
        while (st_for_rem.size()!=0){
            System.out.println(st_for_rem.size());
            studentService.removeStudent(st_for_rem.get(0).getId());
            st_for_rem.remove(0);
        }
        groupService.removeGroup(index);
        return "redirect:/groups";
    }
}
