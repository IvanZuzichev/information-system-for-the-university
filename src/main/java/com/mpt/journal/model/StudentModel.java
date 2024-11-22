package com.mpt.journal.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
@Data
@Entity
@Table(name = "student")
@NoArgsConstructor
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String firstName;
    @NotNull
    private String middleName;
    @NotNull
    private String mail;
    @NotNull
    private boolean deleted;

    @OneToOne(optional = true, mappedBy = "student", cascade = CascadeType.ALL)
    private PasportModel pasportModel;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "groop_id")
    private GroopModel groopModel;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public PasportModel getPasportModel() {
        return pasportModel;
    }

    public void setPasportModel(PasportModel pasportModel) {
        this.pasportModel = pasportModel;
    }

    public GroopModel getGroopModel() {
        return groopModel;
    }

    public void setGroopModel(GroopModel groopModel) {
        this.groopModel = groopModel;
    }

    public StudentModel(int id, String name, String lastName, String firstName, String middleName, String mail, boolean deleted, PasportModel pasportModel, GroopModel groopModel) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.mail = mail;
        this.deleted = deleted;
        this.pasportModel = pasportModel;
        this.groopModel = groopModel;
    }
}
