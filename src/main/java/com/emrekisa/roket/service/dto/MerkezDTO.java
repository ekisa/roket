package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Merkez entity.
 */
public class MerkezDTO implements Serializable {

    private Long id;

    @NotNull
    private String adi;

    private Long mahalleId;
    private String mahalleAd;

    private Long gpsLokasyonId;

    private Long adresId;
    private String acikAdres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public Long getMahalleId() {
        return mahalleId;
    }

    public void setMahalleId(Long mahalleId) {
        this.mahalleId = mahalleId;
    }

    public String getMahalleAd() {
        return mahalleAd;
    }

    public void setMahalleAd(String mahalleAd) {
        this.mahalleAd = mahalleAd;
    }

    public Long getGpsLokasyonId() {
        return gpsLokasyonId;
    }

    public void setGpsLokasyonId(Long gPSLokasyonId) {
        this.gpsLokasyonId = gPSLokasyonId;
    }

    public Long getAdresId() {
        return adresId;
    }

    public void setAdresId(Long adresId) {
        this.adresId = adresId;
    }

    public String getAcikAdres() {
        return acikAdres;
    }

    public void setAcikAdres(String acikAdres) {
        this.acikAdres = acikAdres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MerkezDTO merkezDTO = (MerkezDTO) o;
        if(merkezDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), merkezDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MerkezDTO{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            "}";
    }
}
