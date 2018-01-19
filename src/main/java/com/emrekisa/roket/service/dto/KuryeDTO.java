package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.emrekisa.roket.domain.enumeration.KURYE_STATU;

/**
 * A DTO for the Kurye entity.
 */
public class KuryeDTO implements Serializable {

    private Long id;

    @NotNull
    private KURYE_STATU statu;

    private String firstName;
    private String lastName;
    private String login;
    private Long userId;

    private Long merkezId;
    private String merkezAd;

    private Long gpsLokasyonId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMerkezAd() {
        return merkezAd;
    }

    public void setMerkezAd(String merkezAd) {
        this.merkezAd = merkezAd;
    }

    public KURYE_STATU getStatu() {
        return statu;
    }

    public void setStatu(KURYE_STATU statu) {
        this.statu = statu;
    }

    public Long getMerkezId() {
        return merkezId;
    }

    public void setMerkezId(Long merkezId) {
        this.merkezId = merkezId;
    }

    public Long getGpsLokasyonId() {
        return gpsLokasyonId;
    }

    public void setGpsLokasyonId(Long gPSLokasyonId) {
        this.gpsLokasyonId = gPSLokasyonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KuryeDTO kuryeDTO = (KuryeDTO) o;
        if(kuryeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kuryeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KuryeDTO{" +
            "id=" + getId() +
            ", statu='" + getStatu() + "'" +
            "}";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
