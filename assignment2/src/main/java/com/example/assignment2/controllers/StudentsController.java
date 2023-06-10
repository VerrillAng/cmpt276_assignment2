package com.example.assignment2.controllers;

// import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.assignment2.models.Student;
import com.example.assignment2.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentsController {

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view") //ENDPOINT
    public String getAllData(Model model){
        // List<Student> students = new ArrayList<>();
        // students.add(new Student("bobby", 10, 100, "red", 2.4, "games", "canadian", "male"));
        System.out.println("Getting all data");
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", students);
        return "students/displayData";
    }

    @GetMapping("/students/display") //ENDPOINT
    public String getAllStudents(Model model){
        // List<Student> students = new ArrayList<>();
        // students.add(new Student("bobby", 10, 100, "red", 2.4, "games", "canadian", "male"));
        System.out.println("Getting all data");
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", students);
        return "students/displayStudents";
    }
    
    @PostMapping("students/add")
    public String addStudent(@RequestParam Map<String, String> newstudent, HttpServletResponse response){
        System.out.println("ADD student");
        String newName = newstudent.get("name");
        Double newWeight = Double.parseDouble(newstudent.get("weight"));
        Double newHeight = Double.parseDouble(newstudent.get("height"));
        String newHairColor = newstudent.get("hairColor");
        double newGpa = Double.parseDouble(newstudent.get("gpa"));
        String newHobby = newstudent.get("hobby");
        String newNationality = newstudent.get("nationality");
        String newGender = newstudent.get("gender");
        // Student newstudent = new Student("bobby", 10, 100, "red", 2.4, "games", "canadian", "male");
        studentRepo.save(new Student(newName, newWeight, newHeight, newHairColor, newGpa, newHobby, newNationality, newGender));
        // studentRepo.save(newstudent);
        response.setStatus(201);
        return "redirect:/add.html";
    }

    @GetMapping("/students/update/{uid}") //ENDPOINT
    public String editStudent(Model model, @PathVariable String uid){
        System.out.println("Getting student " + uid);
        Student student = studentRepo.findById(Integer.parseInt(uid)).get();
        // Optional<Student> student = studentRepo.findById(8);
        // List<Student> student = studentRepo.findAll();
        model.addAttribute("student", student);
        // String asjkdna = student.getName();
        // model.addAttribute("name", );
        return "students/updateStudents";
    }

    @PostMapping("/students/{uid}")
    public String updateStudent(@PathVariable String uid,  @RequestParam Map<String, String> oldStudent){
        Student existingStudent = studentRepo.findById(Integer.parseInt(uid)).get();
        existingStudent.setName(oldStudent.get("name"));
        existingStudent.setWeight(Double.parseDouble(oldStudent.get("weight")));
        existingStudent.setHeight(Double.parseDouble(oldStudent.get("height")));
        existingStudent.setHairColor(oldStudent.get("hairColor"));
        existingStudent.setGpa(Double.parseDouble(oldStudent.get("gpa")));
        existingStudent.setHobby(oldStudent.get("hobby"));
        existingStudent.setNationality(oldStudent.get("nationality"));
        existingStudent.setGender(oldStudent.get("gender"));
        studentRepo.save(existingStudent);

        return "redirect:/students/display";
    }

    @GetMapping("/students/delete/{uid}")
    public String deleteStudent(@PathVariable String uid){
        Student existingStudent = studentRepo.findById(Integer.parseInt(uid)).get();
        studentRepo.delete(existingStudent);

        return "redirect:/students/display";
    }
}
