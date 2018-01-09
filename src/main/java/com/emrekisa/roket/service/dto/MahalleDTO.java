package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Mahalle entity.
 */
public class MahalleDTO implements Serializable {

    private Long id;

    @NotNull
    private String mahalleAd;

    private String postaKodu;

    private String semt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMahalleAd() {
        return mahalleAd;
    }

    public void setMahalleAd(String mahalleAd) {
        this.mahalleAd = mahalleAd;
    }

    public String getPostaKodu() {
        return postaKodu;
    }

    public void setPostaKodu(String postaKodu) {
        this.postaKodu = postaKodu;
    }

    public String getSemt() {
        return semt;
    }

    public void setSemt(String semt) {
        this.semt = semt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MahalleDTO mahalleDTO = (MahalleDTO) o;
        if(mahalleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mahalleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MahalleDTO{" +
            "id=" + getId() +
            ", mahalleAd='" + getMahalleAd() + "'" +
            ", postaKodu='" + getPostaKodu() + "'" +
            ", semt='" + getSemt() + "'" +
            "}";
    }
}
