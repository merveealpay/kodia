package com.merve.kodia.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


@NamedNativeQueries({

        @NamedNativeQuery(
                name = "allStudents",
                query = "select * from students",
                resultClass = StudentsEntity.class
        ),
        @NamedNativeQuery(
                name = "studentById",
                query = "select * from students where id=:id",
                resultClass = StudentsEntity.class
        )

})


@Entity
@Table(name = "students", schema = "kodia", catalog = "")
public class StudentsEntity {


    @Id
    @GeneratedValue //student veritabanına eklendiginde eklenen id gostermesi icin.
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


    @ManyToOne
    @JoinColumn(name = "university_id")
    private UniversitiesEntity university;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UniversitiesEntity getUniversity() {
        return university;
    }

    public void setUniversity(UniversitiesEntity university) {
        this.university = university;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentsEntity that = (StudentsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (startedAt != null ? !startedAt.equals(that.startedAt) : that.startedAt != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startedAt != null ? startedAt.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}
