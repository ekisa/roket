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
 * A Isyeri.
 */
@Entity
@Table(name = "isyeri")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Isyeri implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "adi", nullable = false)
    private String adi;

    @NotNull
    @Column(name = "telefon", nullable = false)
    private String telefon;

    @Column(name = "aciklama")
    private String aciklama;

    @OneToMany(mappedBy = "isyeri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Emir> emirlers = new HashSet<>();

    @ManyToOne
    private Merkez merkez;

    @ManyToOne
    private GPSLokasyon gpsLokasyon;

    @ManyToOne
    private Adres adres;

    @ManyToOne
    private Musteri musteri;


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

    public String getAdi() {
        return adi;
    }

    public Isyeri adi(String adi) {
        this.adi = adi;
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getTelefon() {
        return telefon;
    }

    public Isyeri telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Isyeri aciklama(String aciklama) {
        this.aciklama = aciklama;
        return this;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Set<Emir> getEmirlers() {
        return emirlers;
    }

    public Isyeri emirlers(Set<Emir> emirs) {
        this.emirlers = emirs;
        return this;
    }

    public Isyeri addEmirler(Emir emir) {
        this.emirlers.add(emir);
        emir.setIsyeri(this);
        return this;
    }

    public Isyeri removeEmirler(Emir emir) {
        this.emirlers.remove(emir);
        emir.setIsyeri(null);
        return this;
    }

    public void setEmirlers(Set<Emir> emirs) {
        this.emirlers = emirs;
    }

    public Merkez getMerkez() {
        return merkez;
    }

    public Isyeri merkez(Merkez merkez) {
        this.merkez = merkez;
        return this;
    }

    public void setMerkez(Merkez merkez) {
        this.merkez = merkez;
    }

    public GPSLokasyon getGpsLokasyon() {
        return gpsLokasyon;
    }

    public Isyeri gpsLokasyon(GPSLokasyon gPSLokasyon) {
        this.gpsLokasyon = gPSLokasyon;
        return this;
    }

    public void setGpsLokasyon(GPSLokasyon gPSLokasyon) {
        this.gpsLokasyon = gPSLokasyon;
    }

    public Adres getAdres() {
        return adres;
    }

    public Isyeri adres(Adres adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public Isyeri musteri(Musteri musteri) {
        this.musteri = musteri;
        return this;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Isyeri isyeri = (Isyeri) o;
        if (isyeri.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), isyeri.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Isyeri{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            "}";
    }
}
