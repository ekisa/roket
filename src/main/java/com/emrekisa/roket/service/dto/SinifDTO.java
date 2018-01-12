package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Sinif entity.
 */
public class SinifDTO implements Serializable {

    private Long id;

    @NotNull
    private String sinifAdi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSinifAdi() {
        return sinifAdi;
    }

    public void setSinifAdi(String sinifAdi) {
        this.sinifAdi = sinifAdi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SinifDTO sinifDTO = (SinifDTO) o;
        if(sinifDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sinifDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SinifDTO{" +
            "id=" + getId() +
            ", sinifAdi='" + getSinifAdi() + "'" +
            "}";
    }
}
