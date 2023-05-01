package com.DesguaceExpress.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
//import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@Audited
@Table(name = "members", uniqueConstraints = {
        @UniqueConstraint(name = "uk_document", columnNames = "document"),
        @UniqueConstraint(name = "uk_phone", columnNames = "phone"),
        @UniqueConstraint(name = "uk_email", columnNames = "email")
})
public class Members implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document", nullable = false)
    private String document;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "create_on", nullable = true)
    @CreationTimestamp
    private LocalDateTime createOn;

    public Members() {
    }

    private Members(Long id, String document, String firstName, String lastName, Long phone, String email) {
        this.id = id;
        this.document = document;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    @Override
    public String toString() {
        return "Members{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", createOn=" + createOn +
                '}';
    }
}
