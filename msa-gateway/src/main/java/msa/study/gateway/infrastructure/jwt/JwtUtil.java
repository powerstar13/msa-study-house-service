package msa.study.gateway.infrastructure.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import msa.study.gateway.infrastructure.exception.status.ExceptionMessage;
import msa.study.gateway.infrastructure.exception.status.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey.accessToken}")
    private String accessTokenSecretKey;
    @Value("${jwt.secretKey.refreshToken}")
    private String refreshTokenSecretKey;

    public boolean validateToken(String token, boolean isAccessToken){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(isAccessToken ? accessTokenSecretKey : refreshTokenSecretKey));

        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ExceptionMessage.ExpiredToken.getMessage());
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(ExceptionMessage.MalformedToken.getMessage());
        } catch (SignatureException e) {
            throw new UnauthorizedException(ExceptionMessage.SignatureVerifyToken.getMessage());
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException(ExceptionMessage.IllegalArgumentToken.getMessage());
        } catch (Exception e) {
            throw new UnauthorizedException(ExceptionMessage.VerifyFailToken.getMessage());
        }
    }
}
