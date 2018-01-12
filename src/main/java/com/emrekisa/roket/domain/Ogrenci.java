package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ogrenci.
 */
@Entity
@Table(name = "ogrenci")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ogrenci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "adi", nullable = false)
    private String adi;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ogrenci_siniflari",
               joinColumns = @JoinColumn(name="ogrencis_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="siniflaris_id", referencedColumnName="id"))
    private Set<Sinif> siniflaris = new HashSet<>();

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

    public Ogrenci adi(String adi) {
        this.adi = adi;
        return this;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public Set<Sinif> getSiniflaris() {
        return siniflaris;
    }

    public Ogrenci siniflaris(Set<Sinif> sinifs) {
        this.siniflaris = sinifs;
        return this;
    }

    public Ogrenci addSiniflari(Sinif sinif) {
        this.siniflaris.add(sinif);
        sinif.getOgrencilers().add(this);
        return this;
    }

    public Ogrenci removeSiniflari(Sinif sinif) {
        this.siniflaris.remove(sinif);
        sinif.getOgrencilers().remove(this);
        return this;
    }

    public void setSiniflaris(Set<Sinif> sinifs) {
        this.siniflaris = sinifs;
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
        Ogrenci ogrenci = (Ogrenci) o;
        if (ogrenci.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogrenci.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ogrenci{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            "}";
    }
}
