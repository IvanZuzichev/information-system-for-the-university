package com.mpt.journal.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Collection;

@Entity
@Table(name = "department")
@NoArgsConstructor
public class DepartmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private boolean deleted;
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<TeacherModel> teachers;

    public DepartmentModel(int id, String name, boolean deleted, Collection<TeacherModel> teachers) {
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

    public Collection<TeacherModel> getTeachers() {
        return teachers;
    }

    public void setTeachers(Collection<TeacherModel> teachers) {
        this.teachers = teachers;
    }
}

