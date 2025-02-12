package org.gil.mgm.users.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String profession;
    @Column(name = "date_created")
    private LocalDate dateCreated;
    private String country;
    private String city;

    public UserEntity(Long id, String firstname, String lastname, String email, String profession, LocalDate dateCreated, String country, String city) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.profession = profession;
        this.dateCreated = dateCreated;
        this.country = country;
        this.city = city;
    }

    public UserEntity() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getProfession() {
        return profession;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
