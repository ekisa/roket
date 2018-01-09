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

import com.emrekisa.roket.domain.enumeration.EMIR_STATU;

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
    private Isyeri isyeri;

    @OneToOne
    @JoinColumn(unique = true)
    private Adres adres;

    @OneToOne
    @JoinColumn(unique = true)
    private GPSLokasyon gpsLokasyon;

    @OneToMany(mappedBy = "emir")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmirGecmisi> emirGecmisis = new HashSet<>();

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
        this.statu = statu;
        return this;
    }

    public void setStatu(EMIR_STATU statu) {
        this.statu = statu;
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

    public Fatura getFatura() {
        return fatura;
    }

    public Emir fatura(Fatura fatura) {
        this.fatura = fatura;
        return this;
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
            "}";
    }
}
