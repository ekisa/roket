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
 * A Musteri.
 */
@Entity
@Table(name = "musteri")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Musteri implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "unvan", nullable = false)
    private String unvan;

    @NotNull
    @Column(name = "eposta", nullable = false)
    private String eposta;

    @NotNull
    @Column(name = "telefon", nullable = false)
    private String telefon;

    @OneToOne
    @JoinColumn(unique = true)
    private Adres adres;

    @OneToMany(mappedBy = "musteri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Isyeri> isyerleris = new HashSet<>();

    @OneToMany(mappedBy = "musteri")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fatura> faturalars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnvan() {
        return unvan;
    }

    public Musteri unvan(String unvan) {
        this.unvan = unvan;
        return this;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getEposta() {
        return eposta;
    }

    public Musteri eposta(String eposta) {
        this.eposta = eposta;
        return this;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getTelefon() {
        return telefon;
    }

    public Musteri telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Adres getAdres() {
        return adres;
    }

    public Musteri adres(Adres adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Set<Isyeri> getIsyerleris() {
        return isyerleris;
    }

    public Musteri isyerleris(Set<Isyeri> isyeris) {
        this.isyerleris = isyeris;
        return this;
    }

    public Musteri addIsyerleri(Isyeri isyeri) {
        this.isyerleris.add(isyeri);
        isyeri.setMusteri(this);
        return this;
    }

    public Musteri removeIsyerleri(Isyeri isyeri) {
        this.isyerleris.remove(isyeri);
        isyeri.setMusteri(null);
        return this;
    }

    public void setIsyerleris(Set<Isyeri> isyeris) {
        this.isyerleris = isyeris;
    }

    public Set<Fatura> getFaturalars() {
        return faturalars;
    }

    public Musteri faturalars(Set<Fatura> faturas) {
        this.faturalars = faturas;
        return this;
    }

    public Musteri addFaturalar(Fatura fatura) {
        this.faturalars.add(fatura);
        fatura.setMusteri(this);
        return this;
    }

    public Musteri removeFaturalar(Fatura fatura) {
        this.faturalars.remove(fatura);
        fatura.setMusteri(null);
        return this;
    }

    public void setFaturalars(Set<Fatura> faturas) {
        this.faturalars = faturas;
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
        Musteri musteri = (Musteri) o;
        if (musteri.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), musteri.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Musteri{" +
            "id=" + getId() +
            ", unvan='" + getUnvan() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", telefon='" + getTelefon() + "'" +
            "}";
    }
}
