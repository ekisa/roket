package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Ilce entity.
 */
public class IlceDTO implements Serializable {

    private Long id;

    @NotNull
    private String ad;

    private Long ilId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Long getIlId() {
        return ilId;
    }

    public void setIlId(Long ilId) {
        this.ilId = ilId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IlceDTO ilceDTO = (IlceDTO) o;
        if(ilceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ilceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IlceDTO{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            "}";
    }
}
