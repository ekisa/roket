package com.emrekisa.roket.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.emrekisa.roket.domain.enumeration.KURYE_STATU;

/**
 * A DTO for the KuryeGecmisi entity.
 */
public class KuryeGecmisiDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant zaman;

    @NotNull
    private KURYE_STATU statu;

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

    public KURYE_STATU getStatu() {
        return statu;
    }

    public void setStatu(KURYE_STATU statu) {
        this.statu = statu;
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

        KuryeGecmisiDTO kuryeGecmisiDTO = (KuryeGecmisiDTO) o;
        if(kuryeGecmisiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kuryeGecmisiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KuryeGecmisiDTO{" +
            "id=" + getId() +
            ", zaman='" + getZaman() + "'" +
            ", statu='" + getStatu() + "'" +
            "}";
    }
}
