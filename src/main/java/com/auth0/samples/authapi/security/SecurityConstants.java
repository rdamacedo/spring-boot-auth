package com.auth0.samples.authapi.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
//	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final long EXPIRATION_TIME = 1_800_000; // 30 min
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";

}
