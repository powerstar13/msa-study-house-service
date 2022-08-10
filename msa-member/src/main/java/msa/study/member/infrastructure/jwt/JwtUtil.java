package msa.study.member.infrastructure.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secretKey.accessToken}")
    private String accessTokenSecretKey;
    @Value("${jwt.secretKey.refreshToken}")
    private String refreshTokenSecretKey;

    // access 토큰 생성 요청
    public String createAccessToken(String memberToken) {
        // access 토큰 유효시간 2시간
        final long accessTokenValidTime = 2 * 60 * 60 * 1000L;

        return createToken(accessTokenValidTime, memberToken, accessTokenSecretKey);
    }

    // refresh 토큰 생성 요청
    public String createRefreshToken(String memberToken) {
        // refresh 토큰 유효시간 2주
        final long refreshTokenValidTime = 2 * 7 * 24 * 60 * 60 * 1000L;

        return createToken(refreshTokenValidTime, memberToken, refreshTokenSecretKey);
    }

    private String createToken(long interval, String memberToken, String secretKey) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + interval);
        SecretKey secretKeyEncode = this.getSecretKeyEncode(secretKey);
    
        return Jwts.builder()
            .claim(ClaimType.MEMBER_TOKEN.getName(), memberToken)
            .setIssuedAt(now) // 토큰 발행시간 정보
            .setExpiration(expiration) // 만료시간
            .signWith(secretKeyEncode, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret 값 셋팅
            .compact();
    }
    
    private SecretKey getSecretKeyEncode(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
