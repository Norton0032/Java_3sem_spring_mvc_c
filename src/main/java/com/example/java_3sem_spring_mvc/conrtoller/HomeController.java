package com.example.java_3sem_spring_mvc.conrtoller;

import com.example.java_3sem_spring_mvc.model.Course;
import com.example.java_3sem_spring_mvc.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/home")
    public String home(Model model) {
        List<Course> list = courseService.filterCourses(null, null);
        List<Course> list_new = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            list_new.add(list.get(list.size() - i - 1));
            if (i==2){
                break;
            }
        }
        model.addAttribute("Courses", list_new);
        return "home";
    }
}
