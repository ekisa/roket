package com.emrekisa.roket.domain.enumeration;

/**
 * The EMIR_STATU enumeration.
 */
public enum EMIR_STATU {
    HAZIR("Hazır"), ATANDI("Atandı"), DAGITIMDA("Dağıtımda"), TESLIM_EDILDI("Teslim Edildi"), IPTAL("İptal"), CEZA("Ceza");

    private String name;

    EMIR_STATU(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static EMIR_STATU getStatuOrDefault(String statu, EMIR_STATU defaultStatu) {
        try {
            return EMIR_STATU.valueOf(statu);
        } catch (Exception e) {
            return defaultStatu;
        }
    }
}
