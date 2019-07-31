package com.ninja.student.dao;

import com.ninja.student.model.Student;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import java.util.List;

public class StudentDao extends AbstractDAO<Student> {

    public StudentDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Student findById(int id){
        Criteria criteria = criteria()
                .add(Restrictions.eq("id",id))
                .add(Restrictions.eq("deleted",(byte)1));
        criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
        List<Student> list = (List<Student>)criteria.list();

        if(list!=null && !list.isEmpty())
            return (Student) criteria.list().get(0);
        return null;
    }

    public boolean update(Student student){
        Student student1 = persist(student);
        if(student1!=null)  return true;
        else    return false;
    }

    public List<Student> findByName(String name){
        String sql = "Select name,contact from student where name like \"%"+name+"%\"";

        SQLQuery query = currentSession().createSQLQuery(sql);
        query.setResultTransformer(Transformers.aliasToBean(Student.class));
        List<Student> studentList = query.list();

        return studentList;
    }
}
