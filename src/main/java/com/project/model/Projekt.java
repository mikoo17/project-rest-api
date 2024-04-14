package com.project.model;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="projekt", indexes = {
        @Index(name="idx_nazwa",columnList = "nazwa"),
        @Index(name="idx_projekt_id", columnList = "projekt_id") // indeksowanie kolumn własnymi indeksami(?)
})
//TODO Indeksować kolumny, które są najczęściej wykorzystywane do wyszukiwania projektów
public class Projekt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="projekt_id") //tylko jeżeli nazwa kolumny w bazie danych ma być inna od nazwy zmiennej
    private Integer projektId;
    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 50)
    private String nazwa;

    @Size(min = 3, max = 1000, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    @Column(name="opis", nullable = true,length = 1000)
    private String opis;
    @CreationTimestamp
    @Column(name = "dataczas_utworzenia", nullable = false, updatable = false)
    private LocalDateTime dataCzasUtworzenia;
    @UpdateTimestamp
    @Column(name = "dataczas_modyfikacji", nullable = true)
    private LocalDateTime dataCzasModyfikacji;


    public Integer getProjektId() {
        return projektId;
    }

    public void setProjektId(Integer projektId) {
        this.projektId = projektId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getDataCzasUtworzenia() {
        return dataCzasUtworzenia;
    }

    public void setDataCzasUtworzenia(LocalDateTime dataCzasUtworzenia) {
        this.dataCzasUtworzenia = dataCzasUtworzenia;
    }

    public LocalDateTime getDataCzasModyfikacji() {
        return dataCzasModyfikacji;
    }

    public void setDataCzasModyfikacji(LocalDateTime dataCzasModyfikacji) {
        this.dataCzasModyfikacji = dataCzasModyfikacji;
    }

    @OneToMany(mappedBy = "projekt")
    @JsonIgnoreProperties({"projekt"})
    private List<Zadanie> zadania;

    public List<Zadanie> getZadania() {
        return zadania;
    }

    public void setZadania(List<Zadanie> zadania) {
        this.zadania = zadania;
    }

    public Set<Student> getStudenci() {
        return studenci;
    }

    public void setStudenci(Set<Student> studenci) {
        this.studenci = studenci;
    }

    @ManyToMany
    @JsonIgnoreProperties({"projekt"})
    @JoinTable(name = "projekt_student",
            joinColumns = {@JoinColumn(name="projekt_id")},
            inverseJoinColumns = {@JoinColumn(name="student_id")})
    private Set<Student> studenci;

    public Projekt() {
    }

    public Projekt(String nazwa, String opis) {
        this.nazwa = nazwa;
        this.opis = opis;
    }
    /*TODO Uzupełnij kod o zmienne reprezentujące pozostałe pola tabeli projekt (patrz rys. 3.1),
. następnie wygeneruj dla nich tzw. akcesory i mutatory (Source -> Generate Getters and Setters),
. ponadto dodaj pusty konstruktor oraz konstruktor ze zmiennymi nazwa i opis.
*/
}
