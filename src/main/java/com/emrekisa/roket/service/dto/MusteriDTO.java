package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Musteri entity.
 */
public class MusteriDTO implements Serializable {

    private Long id;

    @NotNull
    private String unvan;

    @NotNull
    private String eposta;

    @NotNull
    private String telefon;

    private Long adresId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Long getAdresId() {
        return adresId;
    }

    public void setAdresId(Long adresId) {
        this.adresId = adresId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MusteriDTO musteriDTO = (MusteriDTO) o;
        if(musteriDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), musteriDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MusteriDTO{" +
            "id=" + getId() +
            ", unvan='" + getUnvan() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", telefon='" + getTelefon() + "'" +
            "}";
    }
}
