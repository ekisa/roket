package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.emrekisa.roket.domain.enumeration.EMIR_STATU;

/**
 * A EmirGecmisi.
 */
@Entity
@Table(name = "emir_gecmisi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmirGecmisi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "zaman", nullable = false)
    private Instant zaman;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statu", nullable = false)
    private EMIR_STATU statu;

    @NotNull
    @Column(name = "kurye")
    private Kurye kurye;

    @ManyToOne
    private Emir emir;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getZaman() {
        return zaman;
    }

    public EmirGecmisi zaman(Instant zaman) {
        this.zaman = zaman;
        return this;
    }

    public void setZaman(Instant zaman) {
        this.zaman = zaman;
    }

    public EMIR_STATU getStatu() {
        return statu;
    }

    public EmirGecmisi statu(EMIR_STATU statu) {
        this.statu = statu;
        return this;
    }

    public void setStatu(EMIR_STATU statu) {
        this.statu = statu;
    }

    public Emir getEmir() {
        return emir;
    }

    public EmirGecmisi emir(Emir emir) {
        this.emir = emir;
        return this;
    }

    public Kurye getKurye() {
        return kurye;
    }

    public void setKurye(Kurye kurye) {
        this.kurye = kurye;
    }

    public void setEmir(Emir emir) {
        this.emir = emir;
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
        EmirGecmisi emirGecmisi = (EmirGecmisi) o;
        if (emirGecmisi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emirGecmisi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmirGecmisi{" +
            "id=" + getId() +
            ", zaman='" + getZaman() + "'" +
            ", statu='" + getStatu() + "'" +
            "}";
    }
}
