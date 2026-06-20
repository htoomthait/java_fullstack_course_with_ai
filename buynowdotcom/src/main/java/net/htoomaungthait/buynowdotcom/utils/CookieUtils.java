package net.htoomaungthait.buynowdotcom.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class CookieUtils {

    @Value("${app.useSecureCookie}")
    private  boolean useSecureCookie;

    @Value("${api.prefix}")
    private String API_PREFIX;

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken, long maxAge){
        if(response == null){
            throw new IllegalArgumentException("HttpServletResponse cannot be null");
        }

        log.info("Adding refresh token cookie for {} ", refreshToken);
        String sameSite =  useSecureCookie ? "None" : "Lax";


        long maxAgeSeconds = maxAge / 1000;

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(useSecureCookie)
                .path( "/")
                .maxAge(maxAgeSeconds)
                .sameSite(sameSite)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void logCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        System.out.println("Cookies: " + (cookies != null ? Arrays.toString(cookies) : "null"));

        if(cookies != null){
            for(Cookie cookie : cookies ){
                System.out.println("Cookie name: " +  cookie.getName() + ", value: " + cookie.getValue());
            }
        }


    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("Names of the cookie found: " + cookie.getName());
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void  setResponseHeader(HttpServletResponse response,  Cookie refreshTokenCookie, String sameSite){
        StringBuilder cookieHeader = new StringBuilder();
        cookieHeader.append(refreshTokenCookie.getName()).append("=")
                .append("; HttpOnly: Path=").append(refreshTokenCookie.getPath())
                .append("; Max-Age=").append(refreshTokenCookie.getMaxAge())
                .append(useSecureCookie ? "; Secure" : "")
                .append("; SameSite=").append(sameSite);

        response.setHeader("Set-Cookie", cookieHeader.toString());

    }
}
