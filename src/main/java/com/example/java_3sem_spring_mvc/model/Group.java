package com.example.java_3sem_spring_mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @SequenceGenerator(name = "groups_seq", sequenceName = "groups_sequence", allocationSize = 1)
    @GeneratedValue(generator = "groups_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "groupName")
    private String groupName;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private List<Student> students;

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", students=" + students+
                '}';
    }
}

