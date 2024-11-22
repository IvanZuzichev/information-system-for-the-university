package com.mpt.journal.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "groop")
@NoArgsConstructor
public class GroopModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @NotNull
        private String name;
        @NotNull
        private boolean deleted;
        @OneToMany(mappedBy = "groopModel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        private Collection<StudentModel> students;

    public GroopModel(int id, String name, boolean deleted, Collection<StudentModel> students) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
        this.students = students;
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

    public Collection<StudentModel> getStudents() {
        return students;
    }

    public void setStudents(Collection<StudentModel> students) {
        this.students = students;
    }
}
