package com.example.java_3sem_spring_mvc.conrtoller;

import com.example.java_3sem_spring_mvc.model.Course;
import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import com.example.java_3sem_spring_mvc.services.CourseService;
import com.example.java_3sem_spring_mvc.services.GroupService;
import com.example.java_3sem_spring_mvc.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private StudentService studentService;
    @GetMapping
    public String getCourseList_with_paramName(@RequestParam(required = false) Long id,
                                              @RequestParam(required = false) String courseName,
                                              Model model) {
        model.addAttribute("Courses", courseService.filterCourses(id, courseName));
        return "course/courses";
    }

    @GetMapping("/{index}")
    public String getCourse(@PathVariable Long index, Model model){
        Course course = courseService.getCourse(index).get();
        model.addAttribute("Course", course);
        model.addAttribute("CourseIndex", index);
        return "course/course";
    }
    @GetMapping("/new")
    public String newCourse(Model model){
        model.addAttribute("newCourse", new Course());
        return "course/newCourse";
    }
    @GetMapping("/{index}/edit")
    public String editCourse(Model model, @PathVariable Long index){
        model.addAttribute("CourseEdit", courseService.getCourse(index).get());
        model.addAttribute("CourseEditIndex", index);
        return "course/editCourse";
    }
    @PostMapping
    public void createCourse(@RequestBody Course course) {
        courseService.createCourse(course);
    }
    @PostMapping("/new")
    public String createGroup_with_get(@ModelAttribute("newCourse") @Valid Course course,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "course/newCourse";
        }

        courseService.createCourse(course);
        return "redirect:/courses";
    }
    @PatchMapping("/{index}")
    public String updateCourse(@ModelAttribute("CourseEdit") @Valid Course course,
                               BindingResult bindingResult,
                               @PathVariable Long index){
        if (bindingResult.hasErrors()){
            return "course/editCourse";
        }
        courseService.updateCourse(index, course);
        return "redirect:/courses";
    }
    @DeleteMapping("/{index}")
    public String deleteCourse(@PathVariable Long index){
        Course c = courseService.getCourse(index).get();
        List<Group> gr_for_rem = c.getGroups();
        while (gr_for_rem.size()!=0){
            Group g = gr_for_rem.get(0);
            List<Student> st_for_rem = g.getStudents();
            while (st_for_rem.size()!=0){
                studentService.removeStudent(st_for_rem.get(0).getId());
                st_for_rem.remove(0);
            }
            groupService.removeGroup(g.getId());
            gr_for_rem.remove(0);
        }
        courseService.removeCourse(index);
        return "redirect:/courses";
    }
}
