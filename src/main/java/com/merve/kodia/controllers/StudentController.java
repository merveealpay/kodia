package com.merve.kodia.controllers;

import com.merve.kodia.HibernateUtil;
import com.merve.kodia.entities.StudentsEntity;
import com.merve.kodia.entities.UniversitiesEntity;
import okhttp3.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class StudentController {

    private Session session;
    private Transaction transaction;

    @GetMapping("/students")
    public ResponseEntity getStudents()
    {

        HttpHeaders headers = new HttpHeaders();
        JSONArray arrStudents = new JSONArray();

        try {

            session = HibernateUtil.getSession();
            List list = session.getNamedQuery("allStudents")
                    .list();

            for(Object obj : list){

                if(obj instanceof StudentsEntity){

                    StudentsEntity student = (StudentsEntity)obj;

                    JSONObject objStudent = new JSONObject();
                    objStudent.put("id",student.getId())
                            .put("name",student.getName())
                            .put("university",student.getUniversity().getName());

                    arrStudents.put(objStudent);


                }

            }

            headers.add("Description","Başarıyla tüm öğrenciler getirildi");
            return new ResponseEntity<String>(arrStudents.toString(),headers, HttpStatus.OK);

        }
        catch (Exception e)
        {

            return new ResponseEntity<String>(e.getLocalizedMessage(),headers,HttpStatus.BAD_REQUEST);

        }
        finally {

            if(session != null && session.isOpen())
                session.close();

        }
    }

    @PostMapping("/students")
    public ResponseEntity addStudents(@RequestBody String body)
    {


        HttpHeaders headers = new HttpHeaders();

        try{

            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            JSONObject objStudent = new JSONObject(body);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String name = objStudent.optString("name", null);
                Date startedAt = sdf.parse(objStudent.optString("started_at", null));
                int universityId = objStudent.optInt("university");

                JSONArray arrError = new JSONArray();

                JSONObject objError = new JSONObject();
                JSONArray arrErr = new JSONArray();

                if (name == null) {
                    arrErr.put("Öğrenci adı boş bırakılamaz");
                    objError.put("key", "name")
                            .put("errors", arrErr);

                    arrError.put(arrErr);
                }

                arrErr = new JSONArray();
                objError = new JSONObject();

                if (startedAt == null) {

                    arrErr.put("Başlangıç tarihi geçersiz olamaz");

                } else {

                    if (startedAt.before(Date.from(Instant.now()))) {
                        arrErr.put("Başlangıç tarihi şimdiki tarihten önce olamaz");
                    }

                }

                if (arrErr.length() !=0){

                    objError.put("key", "started_value").put("errors", arrErr);
                    arrError.put(objError);
                }

                if (arrError.length() != 0) {

                    JSONObject obj = new JSONObject();
                    obj.put("status", "error")
                            .put("message", "Öğrenci eklenirken hata oluştu")
                            .put("errors", arrError);

                    headers.add("Description", "Öğrenci eklenirken hata oluştu");
                    return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.BAD_REQUEST);
                }

                UniversitiesEntity university = (UniversitiesEntity)
                        session.getNamedQuery("universityById")
                                .setParameter("id", universityId)
                                .uniqueResult();

                if (university == null) {


                    OkHttpClient client = new OkHttpClient();
                    Request req = new Request.Builder()
                            .url("https://gitlab.com/kodiasoft/intern/2019/snippets/1859421/raw")
                            .get()
                            .build();
                    Response response = client.newCall(req).execute();


                    if(response.body() == null)
                        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);

                    String responseBody = response.body().string();
                    JSONArray arr = new JSONArray(responseBody);


                    for(int i=0; i<arr.length(); i++){

                        JSONObject obj = arr.getJSONObject(i);

                        int idUni;
                        String nameUni;
                        String cityUni;
                        Date foundedAtUni;
                        String typeUni;
                        String webPageUni;

                        idUni = obj.optInt("id",-1);
                        nameUni = obj.optString("name",null);
                        cityUni = obj.optString("city",null);
                        foundedAtUni = (new SimpleDateFormat("yyyy")).parse(obj.optString("founded_at",null));
                        typeUni = obj.optString("type",null);
                        webPageUni = obj.optString("web_page",null);

                        if(universityId == idUni){

                            university = new UniversitiesEntity();
                            university.setCity(cityUni);
                            university.setFoundedAt(foundedAtUni);
                            university.setCreatedAt(Date.from(Instant.now()));
                            university.setUpdatedAt(Date.from(Instant.now()));
                            university.setId(idUni);
                            university.setName(nameUni);
                            university.setType(typeUni);
                            university.setWebPage(webPageUni);

                            university.setApiId(universityId);

                            break;

                        }


                    }

                }


                university.setApiId(universityId);

                StudentsEntity student = new StudentsEntity();
                student.setCreatedAt(Date.from(Instant.now()));
                student.setStartedAt(startedAt);
                student.setUpdatedAt(Date.from(Instant.now()));
                student.setUniversity(university);
                student.setName(name);

                session.saveOrUpdate(student);

                headers.add("Description", "Başarıyla öğrenci eklendi");

                JSONObject obj = new JSONObject();
                obj.put("id", student.getId())
                        .put("status", "success")
                        .put("message", name + " adlı öğrenci " + university.getName() + "ne başarıyla eklendi");

                return new ResponseEntity<String>(obj.toString(), headers, HttpStatus.OK);


        }catch (Exception e){

            if(transaction != null && transaction.isActive())
                transaction.rollback();

            return new ResponseEntity<String>(e.getLocalizedMessage(),headers,HttpStatus.BAD_REQUEST);

        }finally {

            if(transaction != null && transaction.isActive())
                transaction.commit();

            if(session != null && session.isOpen())
                session.close();

        }

    }

    @GetMapping("/students/{id}") //gonderilen id'ye karsılık ogrenci donduren fonksıyon.
    public ResponseEntity getStudentById(@PathVariable("id") int id)
    {

        HttpHeaders headers = new HttpHeaders();

        try{

            session = HibernateUtil.getSession();
            StudentsEntity student = (StudentsEntity)
                    session.getNamedQuery("studentById")
                    .setParameter("id",id)
                    .uniqueResult();

            if(student == null){

                JSONObject obj = new JSONObject();
                obj.put("status","error")
                        .put("message",id+" numaralı öğrenci kaydı bulunamadı");

                headers.add("Description","Öğrenci bulunamadı");

                return new ResponseEntity<String>(obj.toString(),headers,HttpStatus.NOT_FOUND);

            }else{

                UniversitiesEntity university = student.getUniversity();

                JSONObject objUniversity = new JSONObject();
                objUniversity.put("id",university.getId())
                        .put("name",university.getName())
                        .put("founded_at",university.getName())
                        .put("type",university.getType());

                JSONObject obj = new JSONObject();
                obj.put("id",student.getId())
                        .put("name",student.getName())
                        .put("university",objUniversity)
                        .put("started_at",student.getStartedAt());


                headers.add("Description","Öğrenciye ait detay ve üniversite bilgileri getirildi");
                return new ResponseEntity<String>(obj.toString(),headers,HttpStatus.OK);
            }

        }catch (Exception e){

            return new ResponseEntity<String>(e.getLocalizedMessage(),headers,HttpStatus.BAD_REQUEST);

        }finally {

            if(session != null && session.isOpen())
                session.close();

        }


    }





}
