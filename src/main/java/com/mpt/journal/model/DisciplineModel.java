package com.mpt.journal.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Table(name = "discipline")
@NoArgsConstructor
public class DisciplineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private boolean deleted;
    @ManyToMany
    @JoinTable(name="teacher_discipline",
    joinColumns = @JoinColumn(name="discipline_id"), inverseJoinColumns = @JoinColumn(name="teacher_id"))
    private List<TeacherModel> teachers;


    public DisciplineModel(int id, String name, boolean deleted, List<TeacherModel> teachers) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
        this.teachers = teachers;
    }

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<TeacherModel> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherModel> teachers) {
        this.teachers = teachers;
    }
}
