package com.emrekisa.roket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Isyeri entity.
 */
public class IsyeriDTO implements Serializable {

    private Long id;

    @NotNull
    private String adi;

    @NotNull
    private String telefon;

    private String aciklama;

    private Long merkezId;
    private String merkezAdi;

    private Long gpsLokasyonId;

    //    private Long adresId;
    private String adres;
    private Long musteriId;
    private String musteriUnvan;

    private Long userId;
    private String username;

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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Long getMerkezId() {
        return merkezId;
    }

    public void setMerkezId(Long merkezId) {
        this.merkezId = merkezId;
    }

    public String getMerkezAdi() {
        return merkezAdi;
    }

    public void setMerkezAdi(String merkezAdi) {
        this.merkezAdi = merkezAdi;
    }

    public Long getGpsLokasyonId() {
        return gpsLokasyonId;
    }

    public void setGpsLokasyonId(Long gPSLokasyonId) {
        this.gpsLokasyonId = gPSLokasyonId;
    }

    /*public Long getAdresId() {
        return adresId;
    }

    public void setAdresId(Long adresId) {
        this.adresId = adresId;
    }
*/

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Long getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(Long musteriId) {
        this.musteriId = musteriId;
    }

    public String getMusteriUnvan() {
        return musteriUnvan;
    }

    public void setMusteriUnvan(String musteriUnvan) {
        this.musteriUnvan = musteriUnvan;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IsyeriDTO isyeriDTO = (IsyeriDTO) o;
        if(isyeriDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), isyeriDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IsyeriDTO{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", aciklama='" + getAciklama() + "'" +
            "}";
    }
}
