package com.example.java_3sem_spring_mvc.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students_cur")
public class Student {
    @Id
    @SequenceGenerator(name = "students_seq", sequenceName = "students_cur_sequence", allocationSize = 1)
    @GeneratedValue(generator = "students_seq", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "firstName")
    private String firstName;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "lastName")
    private String lastName;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "middleName")
    private String middleName;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @Transient
    private String email_send;
}
