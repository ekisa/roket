package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Mahalle.
 */
@Entity
@Table(name = "mahalle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mahalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mahalle_ad", nullable = false)
    private String mahalleAd;

    @Column(name = "posta_kodu")
    private String postaKodu;

    @Column(name = "semt")
    private String semt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMahalleAd() {
        return mahalleAd;
    }

    public Mahalle mahalleAd(String mahalleAd) {
        this.mahalleAd = mahalleAd;
        return this;
    }

    public void setMahalleAd(String mahalleAd) {
        this.mahalleAd = mahalleAd;
    }

    public String getPostaKodu() {
        return postaKodu;
    }

    public Mahalle postaKodu(String postaKodu) {
        this.postaKodu = postaKodu;
        return this;
    }

    public void setPostaKodu(String postaKodu) {
        this.postaKodu = postaKodu;
    }

    public String getSemt() {
        return semt;
    }

    public Mahalle semt(String semt) {
        this.semt = semt;
        return this;
    }

    public void setSemt(String semt) {
        this.semt = semt;
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
        Mahalle mahalle = (Mahalle) o;
        if (mahalle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mahalle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mahalle{" +
            "id=" + getId() +
            ", mahalleAd='" + getMahalleAd() + "'" +
            ", postaKodu='" + getPostaKodu() + "'" +
            ", semt='" + getSemt() + "'" +
            "}";
    }
}
