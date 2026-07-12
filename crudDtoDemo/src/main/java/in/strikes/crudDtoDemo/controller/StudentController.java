package in.strikes.crudDtoDemo.controller;

import in.strikes.crudDtoDemo.dto.CreateStudentRequestDto;
import in.strikes.crudDtoDemo.dto.CreateStudentResponseDto;
import in.strikes.crudDtoDemo.dto.UpdateStudentRequestDto;
import in.strikes.crudDtoDemo.dto.UpdateStudentResponseDto;
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
    public ResponseEntity<CreateStudentResponseDto> create(@RequestBody CreateStudentRequestDto studentRequestDto){
        CreateStudentResponseDto createStudent = studentService.createStudent(studentRequestDto);

//        return ResponseEntity.ok(studentResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createStudent);
    }
    //read
    @GetMapping("/get")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@RequestParam Long id){
       CreateStudentResponseDto studentResp = studentService.getStudent(id);
       return ResponseEntity.ok(studentResp);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent(){
       List<CreateStudentResponseDto> studentResp =  studentService.getAllStudent();
       if (studentResp.isEmpty()){
           return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok(studentResp);
    }

    //update
    @PutMapping("/update")
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam Long id,@RequestBody UpdateStudentRequestDto studentReq){
        UpdateStudentResponseDto studentResp = studentService.UpdateStudent(id,studentReq);
        if (studentResp == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }
    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
        Boolean isDeleted = studentService.deleteStudent(id);
        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Records deleted");
    }
}
