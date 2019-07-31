package com.ninja.student.service;

import com.ninja.student.dao.StudentDao;
import com.ninja.student.model.Student;
import com.ninja.student.transformer.StudentDTO;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final StudentDao studentDao;

    private StudentDTO studentDTO;

    private Student student;

    public StudentService(StudentDao studentDao, StudentDTO studentDTO, Student student) {
        this.studentDao = studentDao;
        this.studentDTO = studentDTO;
        this.student = student;
    }

    public StudentDTO findStudentById(int id){

        student = studentDao.findById(id);
        studentDTO.setId(student.getId());
        studentDTO.setContact(student.getContact());
        studentDTO.setName(student.getName());

        return studentDTO;
    }

    public StudentDTO updateStudentById(int id, String name, BigInteger contact){

        student = studentDao.findById(id);

        if(! name.equals("null"))  student.setName(name);

        if(!contact.toString().equals("0"))   student.setContact(contact);

        studentDao.update(student);

        studentDTO.setId(student.getId());
        studentDTO.setContact(student.getContact());
        studentDTO.setName(student.getName());

        return studentDTO;
    }

    public List<StudentDTO> findStudentByName(String name){

        List<Student> studentList = studentDao.findByName(name);

        List<StudentDTO> studentDTOList=new ArrayList<StudentDTO>();

        for(Student s:studentList)
        {
            StudentDTO studentDTO2 = new StudentDTO();
            studentDTO2.setName(s.getName());
            studentDTO2.setContact(s.getContact());
            studentDTOList.add(studentDTO2);
        }

        return studentDTOList;
    }

    public boolean saveStudent(StudentDTO studentDTO){

        student.setName(studentDTO.getName());
        student.setContact(studentDTO.getContact());
        student.setDeleted((byte) 0);
        if(studentDao.update(student))  return true;
        else return false;
    }

}
