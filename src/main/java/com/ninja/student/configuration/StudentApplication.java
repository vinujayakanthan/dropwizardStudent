package com.ninja.student.configuration;

import com.ninja.student.dao.StudentDao;
import com.ninja.student.model.Student;
import com.ninja.student.resource.StudentResource;
import com.ninja.student.service.StudentService;
import com.ninja.student.transformer.StudentDTO;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

public class StudentApplication extends Application<StudentConfiguration> {

    private static StudentConfiguration studentConfiguration = null;


    public static void main(String[] args) throws Exception {
        new StudentApplication().run(args);
    }

    public static StudentConfiguration getStudentConfiguration() {
        return studentConfiguration;
    }

    public static void setStudentConfiguration(StudentConfiguration studentConfiguration) {
        StudentApplication.studentConfiguration = studentConfiguration;
    }

    public final HibernateBundle<StudentConfiguration> hibernateBundle =
            new HibernateBundle<StudentConfiguration>(
                    Student.class
            ) {
                @Override
                public DataSourceFactory getDataSourceFactory(StudentConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<StudentConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(StudentConfiguration configuration, Environment environment) throws Exception {
        hibernateBundle.getSessionFactory().getStatistics().setStatisticsEnabled(false);

        final StudentDao studentDao = new StudentDao(hibernateBundle.getSessionFactory());
        final StudentDTO studentDTO = new StudentDTO();
        final Student student = new Student();
        final StudentService studentService = new StudentService(studentDao,studentDTO,student);

        environment.jersey().register(new StudentResource(studentService,studentDTO,student));
    }
}
