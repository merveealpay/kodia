package com.merve.kodia.controllers;

import com.merve.kodia.HibernateUtil;
import com.merve.kodia.entities.StudentsEntity;
import com.merve.kodia.entities.UniversitiesEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.validator.constraints.CodePointLength;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class UniversityController {

    private Session session;
    private Transaction transaction;


    @GetMapping("/universities")
    public ResponseEntity getUniversities()
    {


        JSONArray arrUniversities = new JSONArray();
        HttpHeaders headers = new HttpHeaders();

        try{

            session  = HibernateUtil.getSession();

            List list = session.getNamedQuery("allUniversities")
                    .list();

            for(Object object : list){

                if(object instanceof UniversitiesEntity){

                    UniversitiesEntity university = (UniversitiesEntity)object;
                    JSONObject objUniversity = new JSONObject();
                    objUniversity.put("id",university.getId())
                            .put("name",university.getName());

                    arrUniversities.put(objUniversity);

                }

            }

            headers.add("Description","Başarıyla tüm üniversiteler getirildi");

            return new ResponseEntity<String>(arrUniversities.toString(),headers, HttpStatus.OK);

        }catch (Exception e){


            return new ResponseEntity<String>(e.getLocalizedMessage(),headers,HttpStatus.BAD_REQUEST);

        }finally {

            if(session != null && session.isOpen())
                session.close();

        }


    }

    @GetMapping("/universities/{id}")
    public ResponseEntity getUniversityById(@PathVariable("id") int id)
    {

        JSONObject objUniversity = new JSONObject();
        HttpHeaders headers = new HttpHeaders();

        try{

            session = HibernateUtil.getSession();
            UniversitiesEntity university = (UniversitiesEntity)
                    session.getNamedQuery("universityById")
                    .setParameter("id",id)
                    .uniqueResult();

            if(university == null){


                headers.add("Description","Üniversite bulunamadı");
                objUniversity.put("status","error")
                        .put("message",id+" numaralı üniversite kaydı bulunamadı");

                return new ResponseEntity<String>(objUniversity.toString(),headers,HttpStatus.NOT_FOUND);

            }else{

                List<StudentsEntity> students = university.getStudents();
                JSONArray arrStudents = new JSONArray();
                for(StudentsEntity student : students){

                    JSONObject objStudent = new JSONObject();
                    objStudent.put("id",student.getId())
                            .put("name",student.getName())
                            .put("started_at",student.getStartedAt());

                    arrStudents.put(objStudent);

                }

                objUniversity.put("id",university.getId())
                        .put("api_id",university.getApiId())
                        .put("name",university.getName())
                        .put("city",university.getCity())
                        .put("founded_at",university.getFoundedAt())
                        .put("web_page",university.getWebPage())
                        .put("type",university.getType())
                        .put("students",arrStudents);

                headers.add("Description","Başarıyla üniversiteye ait detaylar ve öğrenciler getirildi");
                return new ResponseEntity<String>(objUniversity.toString(),headers,HttpStatus.OK);

            }


        }catch (Exception e)
        {

            return new ResponseEntity<String>(e.getLocalizedMessage(),headers,HttpStatus.BAD_REQUEST);


        }finally {

            if (transaction !=null && transaction.isActive())
                transaction.commit();

            if (session != null && session.isOpen())
                session.close();


        }

    }






}
