package com.emrekisa.roket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.emrekisa.roket.domain.enumeration.EMIR_STATU;

import com.emrekisa.roket.domain.enumeration.BOYUT;

/**
 * A Emir.
 */
@Entity
@Table(name = "emir")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Emir implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statu", nullable = false)
    private EMIR_STATU statu;

    @ManyToOne
    private Kurye kurye;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "boyut", nullable = false)
    private BOYUT boyut;

    @ManyToOne
    private Isyeri isyeri;

    @OneToMany(mappedBy = "emir")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmirGecmisi> emirGecmisis = new HashSet<>();

    @ManyToOne
    private Adres adres;

    @Column(name="acik_adres", length=1024, nullable = false)
    @NotNull
    private String acikAdres;

    @Column(name="adres_tarifi", length=1024)
    private String adresTarifi;

    @ManyToOne
    private GPSLokasyon gpsLokasyon;

    @ManyToOne
    private Fatura fatura;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EMIR_STATU getStatu() {
        return statu;
    }

    public Emir statu(EMIR_STATU statu) {
        if (statu == null) {
            this.statu = EMIR_STATU.HAZIR;
        }
        this.statu = statu;
        this.ilkEmirGecmisiniYarat();
        return this;
    }

    private void ilkEmirGecmisiniYarat() {
        EmirGecmisi emirGecmisi = new EmirGecmisi();
        emirGecmisi.setEmir(this);
        emirGecmisi.setStatu(this.statu);
        emirGecmisi.setZaman(Instant.now());
        this.emirGecmisis.add(emirGecmisi);
    }

    public String getAcikAdres() {
        return acikAdres;
    }

    public void setAcikAdres(String acikAdres) {
        this.acikAdres = acikAdres;
    }

    public String getAdresTarifi() {
        return adresTarifi;
    }

    public void setAdresTarifi(String adresTarifi) {
        this.adresTarifi = adresTarifi;
    }

    public void setStatu(EMIR_STATU statu) {
        this.statu = statu;
    }

    public BOYUT getBoyut() {
        return boyut;
    }

    public Emir boyut(BOYUT boyut) {
        this.boyut = boyut;
        return this;
    }

    public void setBoyut(BOYUT boyut) {
        this.boyut = boyut;
    }

    public Isyeri getIsyeri() {
        return isyeri;
    }

    public Emir isyeri(Isyeri isyeri) {
        this.isyeri = isyeri;
        return this;
    }

    public void setIsyeri(Isyeri isyeri) {
        this.isyeri = isyeri;
    }

    public Set<EmirGecmisi> getEmirGecmisis() {
        return emirGecmisis;
    }

    public Emir emirGecmisis(Set<EmirGecmisi> emirGecmisis) {
        this.emirGecmisis = emirGecmisis;
        return this;
    }

    public Emir addEmirGecmisi(EmirGecmisi emirGecmisi) {
        this.emirGecmisis.add(emirGecmisi);
        emirGecmisi.setEmir(this);
        return this;
    }

    public Emir removeEmirGecmisi(EmirGecmisi emirGecmisi) {
        this.emirGecmisis.remove(emirGecmisi);
        emirGecmisi.setEmir(null);
        return this;
    }

    public void setEmirGecmisis(Set<EmirGecmisi> emirGecmisis) {
        this.emirGecmisis = emirGecmisis;
    }

    public Adres getAdres() {
        return adres;
    }

    public Emir adres(Adres adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public GPSLokasyon getGpsLokasyon() {
        return gpsLokasyon;
    }

    public Emir gpsLokasyon(GPSLokasyon gPSLokasyon) {
        this.gpsLokasyon = gPSLokasyon;
        return this;
    }

    public void setGpsLokasyon(GPSLokasyon gPSLokasyon) {
        this.gpsLokasyon = gPSLokasyon;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public Emir fatura(Fatura fatura) {
        this.fatura = fatura;
        return this;
    }
    public Kurye getKurye() {
        return kurye;
    }

    public void setKurye(Kurye kurye) {
        this.kurye = kurye;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
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
        Emir emir = (Emir) o;
        if (emir.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emir.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Emir{" +
            "id=" + getId() +
            ", statu='" + getStatu() + "'" +
            ", boyut='" + getBoyut() + "'" +
            "}";
    }
}
