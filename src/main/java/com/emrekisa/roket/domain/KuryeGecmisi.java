package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.emrekisa.roket.domain.enumeration.KURYE_STATU;

/**
 * A KuryeGecmisi.
 */
@Entity
@Table(name = "kurye_gecmisi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KuryeGecmisi implements Serializable {

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
    private KURYE_STATU statu;

    @ManyToOne
    private Kurye kurye;

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

    public KuryeGecmisi zaman(Instant zaman) {
        this.zaman = zaman;
        return this;
    }

    public void setZaman(Instant zaman) {
        this.zaman = zaman;
    }

    public KURYE_STATU getStatu() {
        return statu;
    }

    public KuryeGecmisi statu(KURYE_STATU statu) {
        this.statu = statu;
        return this;
    }

    public void setStatu(KURYE_STATU statu) {
        this.statu = statu;
    }

    public Kurye getKurye() {
        return kurye;
    }

    public KuryeGecmisi kurye(Kurye kurye) {
        this.kurye = kurye;
        return this;
    }

    public void setKurye(Kurye kurye) {
        this.kurye = kurye;
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
        KuryeGecmisi kuryeGecmisi = (KuryeGecmisi) o;
        if (kuryeGecmisi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kuryeGecmisi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KuryeGecmisi{" +
            "id=" + getId() +
            ", zaman='" + getZaman() + "'" +
            ", statu='" + getStatu() + "'" +
            "}";
    }
}
