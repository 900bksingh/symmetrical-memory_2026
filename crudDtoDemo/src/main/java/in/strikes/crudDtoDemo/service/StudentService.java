package in.strikes.crudDtoDemo.service;

import in.strikes.crudDtoDemo.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import in.strikes.crudDtoDemo.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public Student createStudent(Student studentReq){
        return studentRepository.save(studentReq);
    }

    public Optional<Student> getStudent(Long id){
        return studentRepository.findById(id);
    }
    public List<Student> getAllStudent(){
        List<Student> studentList = studentRepository.findAll();
        return  studentList;
    }
    public Student UpdateStudent(Long id,Student studentReq){
        Optional<Student> existingStudent= studentRepository.findById(id);
        if (existingStudent.isEmpty()){
            return null;
        }
        Student studentToSave = existingStudent.get();
        studentToSave.setName(studentReq.getName());
        studentToSave.setRollNo(studentReq.getRollNo());
        studentToSave.setSubject(studentReq.getSubject());
        studentToSave.setEmail(studentReq.getEmail());
        studentToSave.setAge(studentReq.getAge());

        return studentRepository.save(studentToSave);
    }


    public Boolean deleteStudent(Long id) {
       Boolean isStudent = studentRepository.existsById(id);
       if (!isStudent) return false;
       studentRepository.deleteById(id);
       return true;
    }
}
