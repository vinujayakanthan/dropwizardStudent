package com.ninja.student.resource;


import com.ninja.student.model.Student;
import com.ninja.student.service.StudentService;
import com.ninja.student.transformer.StudentDTO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.List;

@Path("/student/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentService;

    private final StudentDTO studentDTO;

    private final Student student;

    public StudentResource(StudentService studentService, StudentDTO studentDTO, Student student) {
        this.studentService = studentService;
        this.studentDTO = studentDTO;
        this.student = student;
    }

    @GET
    @Path("id")
    @UnitOfWork
    public StudentDTO findStudentById(@QueryParam(value = "id") int id){
        StudentDTO studentDTO = studentService.findStudentById(id);
        return studentDTO;

    }
    @POST
    @Path("update")
    @UnitOfWork
    public StudentDTO updateById(@QueryParam(value = "id") int id ,  @DefaultValue("null") @QueryParam(value = "name") String name, @DefaultValue("0") @QueryParam(value = "contact") BigInteger contact)
    {
        StudentDTO studentDTO1 = studentService.updateStudentById(id,name,contact);
        return studentDTO1;

    }

    @GET
    @Path("findname")
    @UnitOfWork
    public List<StudentDTO> findStudentByName(@QueryParam(value = "name") String name){
        List<StudentDTO> studentDTOList = studentService.findStudentByName(name);
        return studentDTOList;

    }

    @POST
    @Path("save")
    @UnitOfWork
    public String saveStudent(StudentDTO studentDTO){
        if(studentService.saveStudent(studentDTO))  return "success";
        else    return "failed";
    }


}
