package com.mpt.journal.model;

import jakarta.persistence.*;

import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Table(name = "teacher")
@NoArgsConstructor
public class TeacherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String midlename;
    @NotNull
    private boolean deleted;
    @ManyToMany
    @JoinTable(name="teacher_discipline",
            joinColumns = @JoinColumn(name="teacher_id"), inverseJoinColumns = @JoinColumn(name="discipline_id"))
    private List<DisciplineModel> disciplines;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private DepartmentModel department;

    public TeacherModel(int id, String name, String surname, String midlename, boolean deleted, List<DisciplineModel> disciplines, DepartmentModel department) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.midlename = midlename;
        this.deleted = deleted;
        this.disciplines = disciplines;
        this.department = department;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMidlename() {
        return midlename;
    }

    public void setMidlename(String midlename) {
        this.midlename = midlename;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<DisciplineModel> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<DisciplineModel> disciplines) {
        this.disciplines = disciplines;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }
}
