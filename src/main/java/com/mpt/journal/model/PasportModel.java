package com.mpt.journal.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "pasport")
@NoArgsConstructor
public class PasportModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String series;
    @NotNull
    private String number;
    @NotNull
    private boolean deleted;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name="student_id")
    private StudentModel student;

    public PasportModel(int id, String series, String number, boolean deleted, StudentModel student) {
        this.id = id;
        this.series = series;
        this.number = number;
        this.deleted = deleted;
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }
}