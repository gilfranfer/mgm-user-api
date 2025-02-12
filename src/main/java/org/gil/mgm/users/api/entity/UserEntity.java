package org.gil.mgm.users.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
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

}
