package com.example.java_3sem_spring_mvc.conrtoller;

import com.example.java_3sem_spring_mvc.model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final List<Student> studentList = new ArrayList<>();

    @GetMapping
    public List<Student> getStudentList() {
        return studentList;
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentList.add(student);
    }

    @DeleteMapping("/{index}")
    public void deleteStudent(@PathVariable int index){
        studentList.remove(index);
    }
}
