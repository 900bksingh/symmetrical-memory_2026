package in.strikes.crudDtoDemo.service;

import in.strikes.crudDtoDemo.dto.CreateStudentRequestDto;
import in.strikes.crudDtoDemo.dto.CreateStudentResponseDto;
import in.strikes.crudDtoDemo.dto.UpdateStudentRequestDto;
import in.strikes.crudDtoDemo.dto.UpdateStudentResponseDto;
import in.strikes.crudDtoDemo.entity.Student;
import in.strikes.crudDtoDemo.exception.DuplicateResourceException;
import in.strikes.crudDtoDemo.exception.ResourceNotFoundException;
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
       if (emailExists(student)){
           throw new DuplicateResourceException("Student with email " +student.getEmail()+ "already exists");
       }
       Student studentResp = studentRepository.save(student);
       return mapToDto(studentResp);
    }

    public CreateStudentResponseDto getStudent(Long id){

//             Optional<Student> studentResp =  studentRepository.findById(id);
////             if (studentResp.isPresent()){
////                 return mapToDto(studentResp.get());
////             }
////             return null;
//        return mapToDto(studentResp.get());

//        Student studentResp = studentRepository
//                .findById(id)
//                .orElse(null);
//        return mapToDto(studentResp);
        Student studentResp = studentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " +id + " not found."));
        return mapToDto(studentResp);
    }
    public List<CreateStudentResponseDto> getAllStudent(){
        List<Student> studentList = studentRepository.findAll();
        return  studentList.stream()
                .map(this::mapToDto)
                .toList();
    }
    public UpdateStudentResponseDto UpdateStudent(Long id, UpdateStudentRequestDto studentReq){
        Student existingStudent= studentRepository
                                    .findById(id).orElseThrow(()->
                        new ResourceNotFoundException("Student with id " +id + " not found."));
//        if (existingStudent.isEmpty()){
//            return null;
//        }
//        Student studentToSave = existingStudent.get();

        existingStudent.setName(studentReq.getName());
        existingStudent.setRollNo(studentReq.getRollNo());
        existingStudent.setSubject(studentReq.getSubject());
        existingStudent.setAge(studentReq.getAge());
        existingStudent.setDeleted(false);
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student savedStudent= studentRepository.save(existingStudent);
        return  mapToUpdateDto(savedStudent);
    }

    public void deleteStudent(Long id) {
       Student studentToBeDeleted = studentRepository
                                .findById(id)
                                .orElseThrow(()->
                                        new ResourceNotFoundException("Student with id " +id + " not found."));
//       if (!isStudent) return false;
//       studentRepository.deleteById(id);
        studentRepository.delete(studentToBeDeleted);
    }
//    public Boolean deleteStudentSoftly(Long id) {
//        Student studentToBeDeleted = studentRepository
//                .findById(id)
//                .orElseThrow(()->
//                        new ResourceNotFoundException("Student with id " +id + " not found."));
//        studentRepository.delete(studentToBeDeleted);
//
//    }
    private Student mapToEntity(CreateStudentRequestDto studentResponseDto){
        Student student = new Student();
        student.setName(studentResponseDto.getName());
        student.setAge(studentResponseDto.getAge());
        student.setEmail(studentResponseDto.getEmail());
        student.setRollNo(studentResponseDto.getRollNo());
        student.setSubject(studentResponseDto.getSubject());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

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
    private boolean emailExists(Student student){
        return studentRepository.existsByEmail(student.getEmail());
    }


}

