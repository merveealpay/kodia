package com.merve.kodia.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "universities", schema = "kodia", catalog = "")
public class UniversitiesEntity {


    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "api_id")
    private int apiId;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "web_page")
    private String webPage;

    @Column(name = "type")
    private String type;

    @Column(name = "founded_at",columnDefinition = "DATETIME")
    private Date foundedAt;

    @Column(name = "created_at",columnDefinition = "DATETIME")
    private Date createdAt;

    @Column(name = "updated_at",columnDefinition = "DATETIME")
    private Date updatedAt;


    @OneToMany(mappedBy = "university")
    private List<StudentsEntity> students;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getFoundedAt() {
        return foundedAt;
    }

    public void setFoundedAt(Date foundedAt) {
        this.foundedAt = foundedAt;
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

    public List<StudentsEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsEntity> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UniversitiesEntity that = (UniversitiesEntity) o;

        if (id != that.id) return false;
        if (apiId != that.apiId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (webPage != null ? !webPage.equals(that.webPage) : that.webPage != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (foundedAt != null ? !foundedAt.equals(that.foundedAt) : that.foundedAt != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + apiId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (webPage != null ? webPage.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (foundedAt != null ? foundedAt.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}
