package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Il entity.
 */
public class IlDTO implements Serializable {

    private Long id;

    @NotNull
    private String ad;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IlDTO ilDTO = (IlDTO) o;
        if(ilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IlDTO{" +
            "id=" + getId() +
            ", ad='" + getAd() + "'" +
            "}";
    }
}
