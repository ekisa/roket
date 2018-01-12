package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GPSLokasyon.
 */
@Entity
@Table(name = "gps_lokasyon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GPSLokasyon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "enlem", nullable = false)
    private String enlem;

    @NotNull
    @Column(name = "boylam", nullable = false)
    private String boylam;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnlem() {
        return enlem;
    }

    public GPSLokasyon enlem(String enlem) {
        this.enlem = enlem;
        return this;
    }

    public void setEnlem(String enlem) {
        this.enlem = enlem;
    }

    public String getBoylam() {
        return boylam;
    }

    public GPSLokasyon boylam(String boylam) {
        this.boylam = boylam;
        return this;
    }

    public void setBoylam(String boylam) {
        this.boylam = boylam;
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
        GPSLokasyon gPSLokasyon = (GPSLokasyon) o;
        if (gPSLokasyon.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gPSLokasyon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GPSLokasyon{" +
            "id=" + getId() +
            ", enlem='" + getEnlem() + "'" +
            ", boylam='" + getBoylam() + "'" +
            "}";
    }
}
