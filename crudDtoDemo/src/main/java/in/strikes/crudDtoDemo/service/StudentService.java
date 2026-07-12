package in.strikes.crudDtoDemo.service;

import in.strikes.crudDtoDemo.dto.CreateStudentRequestDto;
import in.strikes.crudDtoDemo.dto.CreateStudentResponseDto;
import in.strikes.crudDtoDemo.dto.UpdateStudentRequestDto;
import in.strikes.crudDtoDemo.dto.UpdateStudentResponseDto;
import in.strikes.crudDtoDemo.entity.Student;
import org.springframework.stereotype.Service;
import in.strikes.crudDtoDemo.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentRequestDto){
       Student student= mapToEntity(studentRequestDto);
       student.setCreatedAt(LocalDateTime.now());
       student.setUpdatedAt(LocalDateTime.now());
       Student studentResp = studentRepository.save(student);
       return mapToDto(studentResp);
    }

    public CreateStudentResponseDto getStudent(Long id){

             Optional<Student> studentResp =  studentRepository.findById(id);
             if (studentResp.isPresent()){
                 return mapToDto(studentResp.get());
             }
             return null;
    }
    public List<CreateStudentResponseDto> getAllStudent(){
        List<Student> studentList = studentRepository.findAll();
        return  studentList.stream()
                .map(this::mapToDto)
                .toList();
    }
    public UpdateStudentResponseDto UpdateStudent(Long id, UpdateStudentRequestDto studentReq){
        Optional<Student> existingStudent= studentRepository.findById(id);
        if (existingStudent.isEmpty()){
            return null;
        }
        Student studentToSave = existingStudent.get();
        studentToSave.setName(studentReq.getName());
        studentToSave.setRollNo(studentReq.getRollNo());
        studentToSave.setSubject(studentReq.getSubject());
        studentToSave.setAge(studentReq.getAge());
        studentToSave.setDeleted(false);
        studentToSave.setUpdatedAt(LocalDateTime.now());

        Student savedStudent= studentRepository.save(studentToSave);
        return  mapToUpdateDto(savedStudent);
    }


    public Boolean deleteStudent(Long id) {
       Boolean isStudent = studentRepository.existsById(id);
       if (!isStudent) return false;
       studentRepository.deleteById(id);
       return true;
    }
    private Student mapToEntity(CreateStudentRequestDto studentResponseDto){
        Student student = new Student();
        student.setName(studentResponseDto.getName());
        student.setAge(studentResponseDto.getAge());
        student.setEmail(studentResponseDto.getEmail());
        student.setRollNo(studentResponseDto.getRollNo());
        student.setSubject(studentResponseDto.getSubject());

        student.setDeleted(false);
        // builder design pattern
        return student;

    }
    private CreateStudentResponseDto mapToDto(Student student){
        CreateStudentResponseDto studentResponseDto = new CreateStudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setRollNo(student.getRollNo());
        studentResponseDto.setSubject(student.getSubject());
        studentResponseDto.setMessage("Student save successfully");
        studentResponseDto.setCreatedAt(student.getCreatedAt());
        studentResponseDto.setUpdatedAt(student.getUpdatedAt());
        return studentResponseDto;



    }

    private UpdateStudentResponseDto  mapToUpdateDto(Student student){
        UpdateStudentResponseDto studentResponseDto  = new UpdateStudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setRollNo(student.getRollNo());
        studentResponseDto.setSubject(student.getSubject());
        studentResponseDto.setMessage("Student save successfully");
        studentResponseDto.setCreatedAt(student.getCreatedAt());
        studentResponseDto.setUpdatedAt(student.getUpdatedAt());
        return studentResponseDto;



    }

}

