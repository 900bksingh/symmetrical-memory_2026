package in.strikes.crudDtoDemo.controller;

import in.strikes.crudDtoDemo.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import in.strikes.crudDtoDemo.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/testing")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("call");
    }

    //create
    @PostMapping("/create")
    public ResponseEntity<Student> create(@RequestBody Student student){
        Student studentResponse = studentService.createStudent(student);

//        return ResponseEntity.ok(studentResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentResponse);
    }
    //read
    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable Long id){
       Optional<Student> studentResp = studentService.getStudent(id);
       return ResponseEntity.ok(studentResp);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent(){
       List<Student> studentResp =  studentService.getAllStudent();
       if (studentResp.isEmpty()){
           return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok(studentResp);
    }

    //update
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,@RequestBody Student student){
        Student studentResp = studentService.UpdateStudent(id,student);
        if (studentResp == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }
    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        Boolean isDeleted = studentService.deleteStudent(id);
        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Records deleted");
    }
}
