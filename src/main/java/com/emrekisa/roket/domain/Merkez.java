package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Merkez.
 */
@Entity
@Table(name = "merkez")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Merkez implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "adi", nullable = false)
    private String adi;

    @OneToOne
    @JoinColumn(unique = true)
    private Adres adres;

    @OneToOne
    @JoinColumn(unique = true)
    private GPSLokasyon gpsLokasyon;

    @ManyToOne
    private Mahalle mahalleler;

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

    public Merkez adi(String adi) {
        this.adi = adi;
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public Adres getAdres() {
        return adres;
    }

    public Merkez adres(Adres adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public GPSLokasyon getGpsLokasyon() {
        return gpsLokasyon;
    }

    public Merkez gpsLokasyon(GPSLokasyon gPSLokasyon) {
        this.gpsLokasyon = gPSLokasyon;
        return this;
    }

    public void setGpsLokasyon(GPSLokasyon gPSLokasyon) {
        this.gpsLokasyon = gPSLokasyon;
    }

    public Mahalle getMahalleler() {
        return mahalleler;
    }

    public Merkez mahalleler(Mahalle mahalle) {
        this.mahalleler = mahalle;
        return this;
    }

    public void setMahalleler(Mahalle mahalle) {
        this.mahalleler = mahalle;
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
        Merkez merkez = (Merkez) o;
        if (merkez.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), merkez.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Merkez{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            "}";
    }
}
