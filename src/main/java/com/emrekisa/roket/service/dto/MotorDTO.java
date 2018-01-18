package com.emrekisa.roket.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Motor entity.
 */
public class MotorDTO implements Serializable {

    private Long id;

    private String numarasi;

    private String marka;

    private String model;

    private String yil;

    private String motorAciklama;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumarasi() {
        return numarasi;
    }

    public void setNumarasi(String numarasi) {
        this.numarasi = numarasi;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYil() {
        return yil;
    }

    public void setYil(String yil) {
        this.yil = yil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MotorDTO motorDTO = (MotorDTO) o;
        if(motorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motorDTO.getId());
    }

    public String getMotorAciklama() {
        return this.motorAciklama;
    }

    public void setMotorAciklama(String motorAciklama) {
        this.motorAciklama = motorAciklama;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MotorDTO{" +
            "id=" + getId() +
            ", numarasi='" + getNumarasi() + "'" +
            ", marka='" + getMarka() + "'" +
            ", model='" + getModel() + "'" +
            ", yil='" + getYil() + "'" +
            "}";
    }
}
