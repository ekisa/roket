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

import com.emrekisa.roket.domain.enumeration.KURYE_STATU;

/**
 * A Kurye.
 */
@Entity
@Table(name = "kurye")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Kurye implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statu", nullable = false)
    private KURYE_STATU statu;

    @OneToOne
    @JoinColumn(unique = true)
    private Merkez merkez;

    @OneToOne
    @JoinColumn(unique = true)
    private Isci isci;

    @OneToOne
    @JoinColumn(unique = true)
    private GPSLokasyon gpsLokasyon;

    @OneToMany(mappedBy = "kurye")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<KuryeGecmisi> statuGecmisis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KURYE_STATU getStatu() {
        return statu;
    }

    public Kurye statu(KURYE_STATU statu) {
        this.statu = statu;
        return this;
    }

    public void setStatu(KURYE_STATU statu) {
        this.statu = statu;
    }

    public Merkez getMerkez() {
        return merkez;
    }

    public Kurye merkez(Merkez merkez) {
        this.merkez = merkez;
        return this;
    }

    public void setMerkez(Merkez merkez) {
        this.merkez = merkez;
    }

    public Isci getIsci() {
        return isci;
    }

    public Kurye isci(Isci isci) {
        this.isci = isci;
        return this;
    }

    public void setIsci(Isci isci) {
        this.isci = isci;
    }

    public GPSLokasyon getGpsLokasyon() {
        return gpsLokasyon;
    }

    public Kurye gpsLokasyon(GPSLokasyon gPSLokasyon) {
        this.gpsLokasyon = gPSLokasyon;
        return this;
    }

    public void setGpsLokasyon(GPSLokasyon gPSLokasyon) {
        this.gpsLokasyon = gPSLokasyon;
    }

    public Set<KuryeGecmisi> getStatuGecmisis() {
        return statuGecmisis;
    }

    public Kurye statuGecmisis(Set<KuryeGecmisi> kuryeGecmisis) {
        this.statuGecmisis = kuryeGecmisis;
        return this;
    }

    public Kurye addStatuGecmisi(KuryeGecmisi kuryeGecmisi) {
        this.statuGecmisis.add(kuryeGecmisi);
        kuryeGecmisi.setKurye(this);
        return this;
    }

    public Kurye removeStatuGecmisi(KuryeGecmisi kuryeGecmisi) {
        this.statuGecmisis.remove(kuryeGecmisi);
        kuryeGecmisi.setKurye(null);
        return this;
    }

    public void setStatuGecmisis(Set<KuryeGecmisi> kuryeGecmisis) {
        this.statuGecmisis = kuryeGecmisis;
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
        Kurye kurye = (Kurye) o;
        if (kurye.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kurye.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kurye{" +
            "id=" + getId() +
            ", statu='" + getStatu() + "'" +
            "}";
    }
}
