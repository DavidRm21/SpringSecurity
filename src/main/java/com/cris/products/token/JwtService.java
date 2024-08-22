package com.cris.products.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class JwtService {

    private String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";


    public String getToken(){
        return getToken(Map.of(
                "iss", "OhP",
                "exp", longToUTC(System.currentTimeMillis()+1000*60*24),
        "expir", longToUTC(System.currentTimeMillis()+1000*60*24)));
    }

    private String getToken(Map<String, Object> extractClaims) {
        return Jwts.builder()
                .header().add("kid", "publicKey")
                .add("exper", longToUTC(System.currentTimeMillis()+1000*60*24))
                .and()
                .claims(extractClaims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String longToUTC(Long timestamp){
        Date date = new Date(timestamp); // Multiply by 1000 to convert seconds to milliseconds

        // Format the Date object as a UTC string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormat = sdf.format(date);
        log.info("Formateada. {}", dateFormat);
        return dateFormat;
    }
}
