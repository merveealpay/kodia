package com.merve.kodia.controllers;

import com.merve.kodia.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class StudentController {

    private Session session;
    private Transaction transaction;

    @GetMapping("/students")
    public ResponseEntity getStudents()
    {

        List list = null;


        try {

            session = HibernateUtil.getSession();
            list = session.getNamedQuery("allStudents")
                    .list();

        }
        catch (Exception e)
        {

        }
        finally {

            if(session != null && session.isOpen())
                session.close();

        }
        return  ResponseEntity.ok(list);
    }

    @PostMapping("/students")
    public ResponseEntity addStudents(RequestEntity request)
    {
        return ResponseEntity.ok(request.getBody());
    }

    @GetMapping("/students/{id}") //gonderilen id'ye karsılık ogrenci donduren fonksıyon.
    public ResponseEntity getStudentById(@PathVariable("id") int id)
    {
        return ResponseEntity.ok(id);
    }





}
