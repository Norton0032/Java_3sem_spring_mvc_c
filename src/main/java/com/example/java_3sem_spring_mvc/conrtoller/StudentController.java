package com.example.java_3sem_spring_mvc.conrtoller;

import com.example.java_3sem_spring_mvc.dao.StudentDAO;
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
    private StudentDAO studentDAO;
    @GetMapping
    public String getStudenList_with_paramName(@RequestParam(value = "name", required = false) String i, Model model) {
        //Ожидаем параметра на get-запрос, required=false означает, что он необязателен
        model.addAttribute("StudentsName", i);
        model.addAttribute("Studens", studentDAO.getStudentList());
        System.out.println(studentDAO.getStudentList());
        return "student/students";
    }
    @GetMapping("/{index}")
    public String getStuden(@PathVariable int index, Model model){
        model.addAttribute("Studen", studentDAO.getStudent(index).toString());
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

        studentDAO.createStudent(studen);
    }
    @PostMapping("/new")
    public String createStuden_with_get(@ModelAttribute("newStudent") @Valid Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "student/newStudent";
        }
        studentDAO.createStudent(student);
        return "redirect:/students";
}
    @PatchMapping("/{index}")
    public String updateStudent(@ModelAttribute("StudentEdit") @Valid Student student, BindingResult bindingResult, @PathVariable int index){
        if (bindingResult.hasErrors()){
            return "student/editStudent";
        }
        studentDAO.updateStudent(index, student);
        return "redirect:/students";
    }
    @DeleteMapping("/{index}")
    public String deleteStuden(@PathVariable Long index){
        studentDAO.removeStudent(index);
        return "redirect:/students";
    }
}
