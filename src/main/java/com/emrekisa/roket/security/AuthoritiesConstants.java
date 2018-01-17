package com.emrekisa.roket.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";
    public static final String USER_MERKEZ = "ROLE_USER_MERKEZ";
    public static final String USER_KURYE = "ROLE_USER_KURYE";
    public static final String USER_MUSTERI = "ROLE_USER_MUSTERI";
    public static final String USER_ISYERI = "ROLE_USER_ISYERI";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
