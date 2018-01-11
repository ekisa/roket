package com.emrekisa.roket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Adres.
 */
@Entity
@Table(name = "adres")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Adres implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bbk")
    private String bbk;

    @Column(name = "ickapi_no")
    private String ickapiNo;

    @Column(name = "diskapi_no")
    private String diskapiNo;

    @Column(name = "site")
    private String site;

    @Column(name = "sokak")
    private String sokak;

    @Column(name = "cadde")
    private String cadde;

    @Column(name = "adres_tarifi")
    private String adresTarifi;

    @ManyToOne
    private Mahalle mahalle;

    @ManyToOne
    private Musteri musteri;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBbk() {
        return bbk;
    }

    public Adres bbk(String bbk) {
        this.bbk = bbk;
        return this;
    }

    public void setBbk(String bbk) {
        this.bbk = bbk;
    }

    public String getIckapiNo() {
        return ickapiNo;
    }

    public Adres ickapiNo(String ickapiNo) {
        this.ickapiNo = ickapiNo;
        return this;
    }

    public void setIckapiNo(String ickapiNo) {
        this.ickapiNo = ickapiNo;
    }

    public String getDiskapiNo() {
        return diskapiNo;
    }

    public Adres diskapiNo(String diskapiNo) {
        this.diskapiNo = diskapiNo;
        return this;
    }

    public void setDiskapiNo(String diskapiNo) {
        this.diskapiNo = diskapiNo;
    }

    public String getSite() {
        return site;
    }

    public Adres site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSokak() {
        return sokak;
    }

    public Adres sokak(String sokak) {
        this.sokak = sokak;
        return this;
    }

    public void setSokak(String sokak) {
        this.sokak = sokak;
    }

    public String getCadde() {
        return cadde;
    }

    public Adres cadde(String cadde) {
        this.cadde = cadde;
        return this;
    }

    public void setCadde(String cadde) {
        this.cadde = cadde;
    }

    public String getAdresTarifi() {
        return adresTarifi;
    }

    public Adres adresTarifi(String adresTarifi) {
        this.adresTarifi = adresTarifi;
        return this;
    }

    public void setAdresTarifi(String adresTarifi) {
        this.adresTarifi = adresTarifi;
    }

    public Mahalle getMahalle() {
        return mahalle;
    }

    public Adres mahalle(Mahalle mahalle) {
        this.mahalle = mahalle;
        return this;
    }

    public void setMahalle(Mahalle mahalle) {
        this.mahalle = mahalle;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public Adres musteri(Musteri musteri) {
        this.musteri = musteri;
        return this;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
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
        Adres adres = (Adres) o;
        if (adres.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adres.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Adres{" +
            "id=" + getId() +
            ", bbk='" + getBbk() + "'" +
            ", ickapiNo='" + getIckapiNo() + "'" +
            ", diskapiNo='" + getDiskapiNo() + "'" +
            ", site='" + getSite() + "'" +
            ", sokak='" + getSokak() + "'" +
            ", cadde='" + getCadde() + "'" +
            ", adresTarifi='" + getAdresTarifi() + "'" +
            "}";
    }
}
