package com.emrekisa.roket.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Isci entity.
 */
public class IsciDTO implements Serializable {

    private Long id;

    private String adi;

    private String soyadi;

    private String eposta;

    private String telefon;

    private Long teminatTutari;

    private Long maas;

    private String sicil;

    private String tckn;

    private String zimmetliMallar;

    private Long motorId;
    private Long userId;
    private String login;
    private String motorAciklama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotorAciklama() {
        return motorAciklama;
    }

    public void setMotorAciklama(String motorAciklama) {
        this.motorAciklama = motorAciklama;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
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

    public Long getTeminatTutari() {
        return teminatTutari;
    }

    public void setTeminatTutari(Long teminatTutari) {
        this.teminatTutari = teminatTutari;
    }

    public Long getMaas() {
        return maas;
    }

    public void setMaas(Long maas) {
        this.maas = maas;
    }

    public String getSicil() {
        return sicil;
    }

    public void setSicil(String sicil) {
        this.sicil = sicil;
    }

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public String getZimmetliMallar() {
        return zimmetliMallar;
    }

    public void setZimmetliMallar(String zimmetliMallar) {
        this.zimmetliMallar = zimmetliMallar;
    }

    public Long getMotorId() {
        return motorId;
    }

    public void setMotorId(Long motorId) {
        this.motorId = motorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IsciDTO isciDTO = (IsciDTO) o;
        if(isciDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), isciDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IsciDTO{" +
            "id=" + getId() +
            ", adi='" + getAdi() + "'" +
            ", soyadi='" + getSoyadi() + "'" +
            ", eposta='" + getEposta() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", teminatTutari=" + getTeminatTutari() +
            ", maas=" + getMaas() +
            ", sicil='" + getSicil() + "'" +
            ", tckn='" + getTckn() + "'" +
            ", zimmetliMallar='" + getZimmetliMallar() + "'" +
            "}";
    }
}
