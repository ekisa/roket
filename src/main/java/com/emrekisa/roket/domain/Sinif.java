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
 * A Sinif.
 */
@Entity
@Table(name = "sinif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sinif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sinif_adi", nullable = false)
    private String sinifAdi;

    @ManyToMany(mappedBy = "siniflaris")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ogrenci> ogrencilers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSinifAdi() {
        return sinifAdi;
    }

    public Sinif sinifAdi(String sinifAdi) {
        this.sinifAdi = sinifAdi;
        return this;
    }

    public void setSinifAdi(String sinifAdi) {
        this.sinifAdi = sinifAdi;
    }

    public Set<Ogrenci> getOgrencilers() {
        return ogrencilers;
    }

    public Sinif ogrencilers(Set<Ogrenci> ogrencis) {
        this.ogrencilers = ogrencis;
        return this;
    }

    public Sinif addOgrenciler(Ogrenci ogrenci) {
        this.ogrencilers.add(ogrenci);
        ogrenci.getSiniflaris().add(this);
        return this;
    }

    public Sinif removeOgrenciler(Ogrenci ogrenci) {
        this.ogrencilers.remove(ogrenci);
        ogrenci.getSiniflaris().remove(this);
        return this;
    }

    public void setOgrencilers(Set<Ogrenci> ogrencis) {
        this.ogrencilers = ogrencis;
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
        Sinif sinif = (Sinif) o;
        if (sinif.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sinif.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sinif{" +
            "id=" + getId() +
            ", sinifAdi='" + getSinifAdi() + "'" +
            "}";
    }
}
