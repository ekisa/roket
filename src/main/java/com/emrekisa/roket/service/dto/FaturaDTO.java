package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Fatura entity.
 */
public class FaturaDTO implements Serializable {

    private Long id;

    @NotNull
    private String yil;

    @NotNull
    private String ay;

    private Long musteriId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYil() {
        return yil;
    }

    public void setYil(String yil) {
        this.yil = yil;
    }

    public String getAy() {
        return ay;
    }

    public void setAy(String ay) {
        this.ay = ay;
    }

    public Long getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(Long musteriId) {
        this.musteriId = musteriId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FaturaDTO faturaDTO = (FaturaDTO) o;
        if(faturaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faturaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FaturaDTO{" +
            "id=" + getId() +
            ", yil='" + getYil() + "'" +
            ", ay='" + getAy() + "'" +
            "}";
    }
}
