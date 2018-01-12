package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Ogrenci entity.
 */
public class OgrenciDTO implements Serializable {

    private Long id;

    @NotNull
    private String adi;

    private Set<SinifDTO> siniflaris = new HashSet<>();

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

    public Set<SinifDTO> getSiniflaris() {
        return siniflaris;
    }

    public void setSiniflaris(Set<SinifDTO> sinifs) {
        this.siniflaris = sinifs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OgrenciDTO ogrenciDTO = (OgrenciDTO) o;
        if(ogrenciDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ogrenciDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OgrenciDTO{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            "}";
    }
}
