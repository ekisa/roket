package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Mahalle entity.
 */
public class MahalleDTO implements Serializable {

    private Long id;

    @NotNull
    private String adi;

    private String postaKodu;

    private String semt;

    private Long ilceId;

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

    public Long getIlceId() {
        return ilceId;
    }

    public void setIlceId(Long ilceId) {
        this.ilceId = ilceId;
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
            ", adi='" + getAdi() + "'" +
            ", postaKodu='" + getPostaKodu() + "'" +
            ", semt='" + getSemt() + "'" +
            "}";
    }
}
