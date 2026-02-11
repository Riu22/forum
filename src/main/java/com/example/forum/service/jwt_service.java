package com.example.forum.service;

import com.example.forum.dto.user_dto; // Importante importar tu record
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class jwt_service {

    private static final String SECRET_KEY = "Y2xhdmVfc2VjcmV0YV9tdXlfZmllcnRlX3BhcmFfZWxfZm9ydW1fcHJvamVjdF8yMDI0";

    public String generate_token(String username, user_dto user_info) {
        Map<String, Object> extra_claims = new HashMap<>();
        extra_claims.put("role", user_info.role());
        extra_claims.put("_id", user_info._id());
        extra_claims.put("email", user_info.email());
        extra_claims.put("name", user_info.name());
        extra_claims.put("__v", user_info.__v());
        extra_claims.put("avatarUrl", user_info.avatarUrl());
        extra_claims.put("id", user_info.id());
        extra_claims.put("permissions", user_info.permissions());

        return create_token(extra_claims, username);
    }

    private String create_token(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(get_signing_key())
                .compact();
    }

    public String extract_user_name(String token) {
        return extract_claim(token, Claims::getSubject);
    }

    public <T> T extract_claim(String token, Function<Claims, T> claims_resolver) {
        final Claims claims = extract_all_claims(token);
        return claims_resolver.apply(claims);
    }

    private Claims extract_all_claims(String token) {
        return Jwts.parser()
                .verifyWith(get_signing_key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean is_token_valid(String token, UserDetails user_details) {
        final String user_name = extract_user_name(token);
        return (user_name.equals(user_details.getUsername())) && !is_token_expired(token);
    }

    private boolean is_token_expired(String token) {
        return extract_claim(token, Claims::getExpiration).before(new Date());
    }

    private SecretKey get_signing_key() {
        byte[] key_bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key_bytes);
    }

    public String generate_token_with_claims(user_dto user_data) {
        Map<String, Object> extra_claims = new HashMap<>();
        extra_claims.put("role", user_data.role());
        extra_claims.put("_id", user_data._id());
        extra_claims.put("email", user_data.email());
        extra_claims.put("name", user_data.name());
        extra_claims.put("__v", user_data.__v());
        extra_claims.put("avatarUrl", user_data.avatarUrl());
        extra_claims.put("id", user_data.id());
        extra_claims.put("permissions", user_data.permissions());

        return create_token(extra_claims, user_data.name()); // Using name as subject
    }
}