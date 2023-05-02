package com.example.java_3sem_spring_mvc.conrtoller;


import com.example.java_3sem_spring_mvc.dao.GroupDAO;
import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupDAO groupDAO;
    @GetMapping
    public String getGroupList_with_paramName(@RequestParam(value = "name", required = false) String i, Model model) {
        //Ожидаем параметра на get-запрос, required=false означает, что он необязателен
        model.addAttribute("GroupsName", i);
        model.addAttribute("Groups", groupDAO.getGroupList());
        return "group/groups";
    }
    @GetMapping("/{index}")
    public String getGroup(@PathVariable int index, Model model){
        model.addAttribute("Group", groupDAO.getGroup(index));
        model.addAttribute("GroupIndex", index);
        return "group/group";
    }
    @GetMapping("/new")
    public String newGroup(Model model){
        model.addAttribute("newGroup", new Group());
        return "group/newGroup";
    }
    @GetMapping("/{index}/edit")
    public String editGroup(Model model, @PathVariable int index){
        model.addAttribute("GroupEdit", new Group());
        model.addAttribute("GroupEditIndex", index);
        return "group/editGroup";
    }
    @PostMapping
    public void createGroup(@RequestBody Group group) {
        groupDAO.createGroup(group);
    }
    @PostMapping("/new")
    public String createGroup_with_get(@ModelAttribute("newGroup") @Valid Group group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "group/newGroup";
        }

        groupDAO.createGroup(group);
        return "redirect:/groups";
    }
    @PatchMapping("/{index}")
    public String updateGroup(@ModelAttribute("GroupEdit") @Valid Group group, BindingResult bindingResult, @PathVariable int index){
        if (bindingResult.hasErrors()){
            return "group/editGroup";
        }
        groupDAO.updateGroup(index, group);
        return "redirect:/groups";
    }
    @DeleteMapping("/{index}")
    public String deleteGroup(@PathVariable Long index){
        groupDAO.removeGroup(index);
        return "redirect:/groups";
    }
}
