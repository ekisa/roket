package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.emrekisa.roket.domain.enumeration.EMIR_STATU;
import com.emrekisa.roket.domain.enumeration.BOYUT;

/**
 * A DTO for the Emir entity.
 */
public class EmirDTO implements Serializable {

    private Long id;

    @NotNull
    private EMIR_STATU statu;

    @NotNull
    private BOYUT boyut;

    private Long isyeriId;
    private String isyeriAdi;

    private Long adresId;

    @NotNull
    private String acikAdres;
    private String adresTarifi;

    private Long gpsLokasyonId;

    private Long faturaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EMIR_STATU getStatu() {
        return statu;
    }

    public void setStatu(EMIR_STATU statu) {
        this.statu = statu;
    }

    public BOYUT getBoyut() {
        return boyut;
    }

    public void setBoyut(BOYUT boyut) {
        this.boyut = boyut;
    }

    public Long getIsyeriId() {
        return isyeriId;
    }

    public void setIsyeriId(Long isyeriId) {
        this.isyeriId = isyeriId;
    }

    public String getIsyeriAdi() {
        return isyeriAdi;
    }

    public void setIsyeriAdi(String isyeriAdi) {
        this.isyeriAdi = isyeriAdi;
    }

    public Long getAdresId() {
        return adresId;
    }

    public void setAdresId(Long adresId) {
        this.adresId = adresId;
    }

    public Long getGpsLokasyonId() {
        return gpsLokasyonId;
    }

    public void setGpsLokasyonId(Long gPSLokasyonId) {
        this.gpsLokasyonId = gPSLokasyonId;
    }

    public Long getFaturaId() {
        return faturaId;
    }

    public void setFaturaId(Long faturaId) {
        this.faturaId = faturaId;
    }

    public String getAcikAdres() {
        return acikAdres;
    }

    public void setAcikAdres(String acikAdres) {
        this.acikAdres = acikAdres;
    }

    public String getAdresTarifi() {
        return adresTarifi;
    }

    public void setAdresTarifi(String adresTarifi) {
        this.adresTarifi = adresTarifi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmirDTO emirDTO = (EmirDTO) o;
        if(emirDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emirDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmirDTO{" +
            "id=" + getId() +
            ", statu='" + getStatu() + "'" +
            ", boyut='" + getBoyut() + "'" +
            "}";
    }
}
