package com.example.java_3sem_spring_mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @Id
    @SequenceGenerator(name = "course_seq", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(generator = "course_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "price")
    private int price;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "courseName")
    private String courseName;
    public Course(String groupName) {
        this.courseName = groupName;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Group> groups;
    public int getLenGr(){
        return groups.size();
    }
    public int getLenSt(){
        int col_st = 0;
        for(int i=0;i<groups.size();i++){
            col_st+=groups.get(i).getLenSt();
        }
        return col_st;
    }
    public String getPriceStr(){
        String pr = Integer.toString(price) + "₽";
        return pr;
    }
}
