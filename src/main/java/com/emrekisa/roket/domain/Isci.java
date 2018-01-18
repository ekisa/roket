package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Isci.
 */
@Entity
@Table(name = "isci")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Isci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adi")
    private String adi;

    @Column(name = "soyadi")
    private String soyadi;

    @Column(name = "eposta")
    private String eposta;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "teminat_tutari")
    private Long teminatTutari;

    @Column(name = "maas")
    private Long maas;

    @Column(name = "sicil")
    private String sicil;

    @Column(name = "tckn")
    private String tckn;

    @Column(name = "zimmetli_mallar")
    private String zimmetliMallar;

    @ManyToOne
    private Motor motor;

    @NotNull
    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAdi() {
        return adi;
    }

    public Isci adi(String adi) {
        this.adi = adi;
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public Isci soyadi(String soyadi) {
        this.soyadi = soyadi;
        return this;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getEposta() {
        return eposta;
    }

    public Isci eposta(String eposta) {
        this.eposta = eposta;
        return this;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getTelefon() {
        return telefon;
    }

    public Isci telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Long getTeminatTutari() {
        return teminatTutari;
    }

    public Isci teminatTutari(Long teminatTutari) {
        this.teminatTutari = teminatTutari;
        return this;
    }

    public void setTeminatTutari(Long teminatTutari) {
        this.teminatTutari = teminatTutari;
    }

    public Long getMaas() {
        return maas;
    }

    public Isci maas(Long maas) {
        this.maas = maas;
        return this;
    }

    public void setMaas(Long maas) {
        this.maas = maas;
    }

    public String getSicil() {
        return sicil;
    }

    public Isci sicil(String sicil) {
        this.sicil = sicil;
        return this;
    }

    public void setSicil(String sicil) {
        this.sicil = sicil;
    }

    public String getTckn() {
        return tckn;
    }

    public Isci tckn(String tckn) {
        this.tckn = tckn;
        return this;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public String getZimmetliMallar() {
        return zimmetliMallar;
    }

    public Isci zimmetliMallar(String zimmetliMallar) {
        this.zimmetliMallar = zimmetliMallar;
        return this;
    }

    public void setZimmetliMallar(String zimmetliMallar) {
        this.zimmetliMallar = zimmetliMallar;
    }

    public Motor getMotor() {
        return motor;
    }

    public Isci motor(Motor motor) {
        this.motor = motor;
        return this;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
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
        Isci isci = (Isci) o;
        if (isci.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), isci.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Isci{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", soyadi='" + getSoyadi() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", teminatTutari=" + getTeminatTutari() +
            ", maas=" + getMaas() +
            ", sicil='" + getSicil() + "'" +
            ", tckn='" + getTckn() + "'" +
            ", zimmetliMallar='" + getZimmetliMallar() + "'" +
            "}";
    }
}
