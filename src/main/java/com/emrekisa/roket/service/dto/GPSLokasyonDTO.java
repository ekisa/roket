package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GPSLokasyon entity.
 */
public class GPSLokasyonDTO implements Serializable {

    private Long id;

    @NotNull
    private String enlem;

    @NotNull
    private String boylam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnlem() {
        return enlem;
    }

    public void setEnlem(String enlem) {
        this.enlem = enlem;
    }

    public String getBoylam() {
        return boylam;
    }

    public void setBoylam(String boylam) {
        this.boylam = boylam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GPSLokasyonDTO gPSLokasyonDTO = (GPSLokasyonDTO) o;
        if(gPSLokasyonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gPSLokasyonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GPSLokasyonDTO{" +
            "id=" + getId() +
            ", enlem='" + getEnlem() + "'" +
            ", boylam='" + getBoylam() + "'" +
            "}";
    }
}
