package com.example.java_3sem_spring_mvc.services;

import com.example.java_3sem_spring_mvc.model.Group;
import com.example.java_3sem_spring_mvc.model.Student;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@ManagedResource
public class LoadDataService {
    private final StudentService studentService;
    private final GroupService groupService;

    private final String DIRECTORY = "loaded_data";

    @Autowired
    public LoadDataService(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @Scheduled(fixedDelay = 2 * 60_000)  // 30 minutes
    @ManagedOperation(description = "Clear and load data to csv files")
    @Transactional(readOnly = true)
    public void clearAndLoadData() {
        File directory = new File(DIRECTORY);
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                file.delete();
            }
        } else {
            directory.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(DIRECTORY + "/students.csv", StandardCharsets.UTF_8)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            List<Student> students = studentService.getAllStudents();
            csvPrinter.printRecord("id", "first_name", "last_name", "middleName", "group_id");
            for (Student student : students) {
                csvPrinter.printRecord(student.getId(), student.getFirstName(), student.getLastName(), student.getMiddleName(), student.getGroup().getId());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter writer = new PrintWriter(DIRECTORY + "/groups.csv", StandardCharsets.UTF_8)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            List<Group> groups = groupService.getAllGroups();
            csvPrinter.printRecord("id", "name");
            for (Group group : groups) {
                csvPrinter.printRecord(group.getId(), group.getGroupName());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
