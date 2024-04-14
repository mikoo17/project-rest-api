package com.project.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


import jakarta.persistence.Index;
@Entity //Indeksujemy kolumny, które są najczęściej wykorzystywane do wyszukiwania studentów
@Table(name = "student",
        indexes = { @Index(name = "idx_nazwisko", columnList = "nazwisko", unique = false),
                @Index(name = "idx_nr_indeksu", columnList = "nr_indeksu", unique = true) })
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Integer studentId;

    @Size(min = 3, max = 50, message = "Imię musi zawierać od {min} do {max} znaków!")
    @Column(name = "imie", nullable = false, length = 50)
    private String imie;

    @Size(min = 3, max = 100, message = "Nazwisko musi zawierać od {min} do {max} znaków!")
    @Column(name = "nazwisko", nullable = false, length = 100)
    private String nazwisko;

    @Size(min = 3, max = 20, message = "Numer indeksu musi zawierać od {min} do {max} znaków!")
    @Column(name = "nr_indeksu", nullable = false, length = 20, unique = true)
    private String nrIndeksu;

    @Size(min = 3, max = 50, message = "Email musi zawierać od {min} do {max} znaków!")
    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "stacjonarny", nullable = true)
    private boolean stacjonarny;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNrIndeksu() {
        return nrIndeksu;
    }

    public void setNrIndeksu(String nrIndeksu) {
        this.nrIndeksu = nrIndeksu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStacjonarny() {
        return stacjonarny;
    }

    public void setStacjonarny(boolean stacjonarny) {
        this.stacjonarny = stacjonarny;
    }






    @ManyToMany(mappedBy = "studenci")
    private Set<Projekt> projekty;
    /* TODO Uzupełnij kod o zmienne reprezentujące pola tabeli student (patrz rys. 3.1),
    . następnie wygeneruj dla nich akcesory i mutatory (Source -> Generate Getters and Setters)
    */
    public Student() {}
    public Student(String imie, String nazwisko, String nrIndeksu, Boolean stacjonarny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
    }
    public Student(String imie, String nazwisko, String nrIndeksu, String email, Boolean stacjonarny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
        this.email = email;
        this.stacjonarny = stacjonarny;
    }
//...
}