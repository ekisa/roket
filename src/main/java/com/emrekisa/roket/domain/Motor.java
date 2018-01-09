package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Motor.
 */
@Entity
@Table(name = "motor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Motor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numarasi")
    private String numarasi;

    @Column(name = "marka")
    private String marka;

    @Column(name = "model")
    private String model;

    @Column(name = "yil")
    private String yil;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumarasi() {
        return numarasi;
    }

    public Motor numarasi(String numarasi) {
        this.numarasi = numarasi;
        return this;
    }

    public void setNumarasi(String numarasi) {
        this.numarasi = numarasi;
    }

    public String getMarka() {
        return marka;
    }

    public Motor marka(String marka) {
        this.marka = marka;
        return this;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public Motor model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYil() {
        return yil;
    }

    public Motor yil(String yil) {
        this.yil = yil;
        return this;
    }

    public void setYil(String yil) {
        this.yil = yil;
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
        Motor motor = (Motor) o;
        if (motor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Motor{" +
            "id=" + getId() +
            ", numarasi='" + getNumarasi() + "'" +
            ", marka='" + getMarka() + "'" +
            ", model='" + getModel() + "'" +
            ", yil='" + getYil() + "'" +
            "}";
    }
}
