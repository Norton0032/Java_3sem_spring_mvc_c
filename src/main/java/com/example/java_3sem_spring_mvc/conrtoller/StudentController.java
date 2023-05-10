package com.example.java_3sem_spring_mvc.conrtoller;

import com.example.java_3sem_spring_mvc.model.Course;
import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.services.CourseService;
import com.example.java_3sem_spring_mvc.services.GroupService;
import com.example.java_3sem_spring_mvc.services.StudentService;
import com.example.java_3sem_spring_mvc.model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;

    @Autowired
    private StudentService studentService;
    @GetMapping
    public String getStudenList_with_paramName(@RequestParam(value = "name", required = false) String i,
                                               @RequestParam(required = false) Long id,
                                               @RequestParam(required = false) String firstName,
                                               @RequestParam(required = false) String lastName,
                                               @RequestParam(required = false) String middleName,
                                               @RequestParam(required = false) Long groupId,
                                               Model model) {
        Group group = null;
        if (groupId != null) {
            group = new Group();
            group.setId(groupId);
        }
        model.addAttribute("StudentsName", i);
        model.addAttribute("Studens", studentService.filterStudents(id, firstName, lastName, middleName, group));
        return "student/students";
    }
    @GetMapping("/{index}")
    public String getStuden(@PathVariable Long index, Model model){
        Student student = studentService.getStudent(index).get();
        model.addAttribute("Studen", student);
        model.addAttribute("StudenIndex", index);
        return "student/student";
    }
    @GetMapping("/new")
    public String newStudent(Model model){
        model.addAttribute("newStudent", new Student());
        return "student/newStudent";
    }
    @GetMapping("/{index}/edit")
    public String editStudent(Model model, @PathVariable Long index){
        model.addAttribute("StudentEdit", studentService.getStudent(index));
        model.addAttribute("StudentEditIndex", index);
        return "student/editStudent";
    }
    @PostMapping
    public void createStuden(@RequestBody Student student) {
        boolean fl = false;
        Long id_gr = student.getGroup().getId();
        List<Group> gr= groupService.filterGroups(null, null);
        for (int i = 0;i<gr.size();i++){
            if (gr.get(i).getId()==id_gr){
                fl = true;
                break;
            }
        }
        if (fl != false){
            Group g = groupService.getGroup(student.getGroup().getId()).get();
            String groupname = g.getGroupName();
            String coursename = g.getCourse().getCourseName();
            studentService.createStudent(student, groupname, coursename);
        }
    }
    @PostMapping("/new")
    public String createStuden_with_get(@ModelAttribute("newStudent") @Valid Student student,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "student/newStudent";
        }
        boolean fl = false;
        Long id_gr = student.getGroup().getId();
        List<Group> gr= groupService.filterGroups(null, null);
        for (int i = 0;i<gr.size();i++){
            if (gr.get(i).getId()==id_gr){
                fl = true;
                break;
            }
        }
        if (fl == false){
            return "student/newStudent";
        }

        Group g = groupService.getGroup(student.getGroup().getId()).get();
        String groupname = g.getGroupName();
        String coursename = g.getCourse().getCourseName();
        studentService.createStudent(student, groupname, coursename);
        return "redirect:/students";
}
    @PatchMapping("/{index}")
    public String updateStudent(@ModelAttribute("StudentEdit") @Valid Student student,
                                BindingResult bindingResult, @PathVariable Long index){
        if (bindingResult.hasErrors()){
            return "student/editStudent";
        }
        studentService.updateStudent(index, student);
        return "redirect:/students";
    }
    @DeleteMapping("/{index}")
    public String deleteStuden(@PathVariable Long index){
        studentService.removeStudent(index);
        return "redirect:/students";
    }
}
