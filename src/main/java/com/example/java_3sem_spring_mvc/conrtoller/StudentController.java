package com.example.java_3sem_spring_mvc.conrtoller;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.services.StudentService;
import com.example.java_3sem_spring_mvc.model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/students")
public class StudentController {

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
        //Ожидаем параметра на get-запрос, required=false означает, что он необязателен
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
        model.addAttribute("Studen", studentService.getStudent(index).toString());
        model.addAttribute("StudenIndex", index);
        return "student/student";
    }
    @GetMapping("/new")
    public String newStudent(Model model){
        model.addAttribute("newStudent", new Student());
        return "student/newStudent";
    }
    @GetMapping("/{index}/edit")
    public String editStudent(Model model, @PathVariable int index){
        //model.addAttribute("StudentEdit", studentDAO.getStudent(index)); Почему-то не работает
        model.addAttribute("StudentEdit", new Student());
        model.addAttribute("StudentEditIndex", index);
        return "student/editStudent";
    }
    @PostMapping
    public void createStuden(@RequestBody Student studen) {
        studentService.createStudent(studen);
    }
    @PostMapping("/new")
    public String createStuden_with_get(@ModelAttribute("newStudent") @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "student/newStudent";
        }
        studentService.createStudent(student);
        return "redirect:/students";
}
    @PatchMapping("/{index}")
    public String updateStudent(@ModelAttribute("StudentEdit") @Valid Student student, BindingResult bindingResult, @PathVariable int index){
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
