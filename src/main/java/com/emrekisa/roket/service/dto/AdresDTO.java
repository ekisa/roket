package com.emrekisa.roket.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Adres entity.
 */
public class AdresDTO implements Serializable {

    private Long id;

    private String bbk;

    private String ickapiNo;

    private String diskapiNo;

    private String site;

    private String sokak;

    private String cadde;

    private String adresTarifi;

    private Long mahalleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBbk() {
        return bbk;
    }

    public void setBbk(String bbk) {
        this.bbk = bbk;
    }

    public String getIckapiNo() {
        return ickapiNo;
    }

    public void setIckapiNo(String ickapiNo) {
        this.ickapiNo = ickapiNo;
    }

    public String getDiskapiNo() {
        return diskapiNo;
    }

    public void setDiskapiNo(String diskapiNo) {
        this.diskapiNo = diskapiNo;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSokak() {
        return sokak;
    }

    public void setSokak(String sokak) {
        this.sokak = sokak;
    }

    public String getCadde() {
        return cadde;
    }

    public void setCadde(String cadde) {
        this.cadde = cadde;
    }

    public String getAdresTarifi() {
        return adresTarifi;
    }

    public void setAdresTarifi(String adresTarifi) {
        this.adresTarifi = adresTarifi;
    }

    public Long getMahalleId() {
        return mahalleId;
    }

    public void setMahalleId(Long mahalleId) {
        this.mahalleId = mahalleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdresDTO adresDTO = (AdresDTO) o;
        if(adresDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adresDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdresDTO{" +
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
