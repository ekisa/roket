package com.emrekisa.roket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fatura.
 */
@Entity
@Table(name = "fatura")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fatura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "yil", nullable = false)
    private String yil;

    @NotNull
    @Column(name = "ay", nullable = false)
    private String ay;

    @OneToOne
    @JoinColumn(unique = true)
    private Musteri musteri;

    @OneToMany(mappedBy = "fatura")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Emir> emirlers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYil() {
        return yil;
    }

    public Fatura yil(String yil) {
        this.yil = yil;
        return this;
    }

    public void setYil(String yil) {
        this.yil = yil;
    }

    public String getAy() {
        return ay;
    }

    public Fatura ay(String ay) {
        this.ay = ay;
        return this;
    }

    public void setAy(String ay) {
        this.ay = ay;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public Fatura musteri(Musteri musteri) {
        this.musteri = musteri;
        return this;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public Set<Emir> getEmirlers() {
        return emirlers;
    }

    public Fatura emirlers(Set<Emir> emirs) {
        this.emirlers = emirs;
        return this;
    }

    public Fatura addEmirler(Emir emir) {
        this.emirlers.add(emir);
        emir.setFatura(this);
        return this;
    }

    public Fatura removeEmirler(Emir emir) {
        this.emirlers.remove(emir);
        emir.setFatura(null);
        return this;
    }

    public void setEmirlers(Set<Emir> emirs) {
        this.emirlers = emirs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fatura fatura = (Fatura) o;
        if (fatura.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fatura.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fatura{" +
            "id=" + getId() +
            ", yil='" + getYil() + "'" +
            ", ay='" + getAy() + "'" +
            "}";
    }
}
