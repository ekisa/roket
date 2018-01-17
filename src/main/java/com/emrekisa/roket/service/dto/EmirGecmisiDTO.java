package com.emrekisa.roket.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.emrekisa.roket.domain.enumeration.EMIR_STATU;

/**
 * A DTO for the EmirGecmisi entity.
 */
public class EmirGecmisiDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant zaman;

    @NotNull
    private EMIR_STATU statu;

    private Long emirId;
    private Long kuryeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getZaman() {
        return zaman;
    }

    public void setZaman(Instant zaman) {
        this.zaman = zaman;
    }

    public EMIR_STATU getStatu() {
        return statu;
    }

    public void setStatu(EMIR_STATU statu) {
        this.statu = statu;
    }

    public Long getEmirId() {
        return emirId;
    }

    public void setEmirId(Long emirId) {
        this.emirId = emirId;
    }

    public Long getKuryeId() {
        return kuryeId;
    }

    public void setKuryeId(Long kuryeId) {
        this.kuryeId = kuryeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmirGecmisiDTO emirGecmisiDTO = (EmirGecmisiDTO) o;
        if(emirGecmisiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emirGecmisiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmirGecmisiDTO{" +
            "id=" + getId() +
            ", zaman='" + getZaman() + "'" +
            ", statu='" + getStatu() + "'" +
            "}";
    }
}
