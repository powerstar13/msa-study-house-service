package station3.assignment.member.infrastructure.jwt.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import station3.assignment.member.infrastructure.jwt.JwtUtil;

@Component
@RequiredArgsConstructor
public class TokenStoreImpl implements TokenStore {
    
    private final JwtUtil jwtUtil;
    
    /**
     * 로그인 정보로 각 토큰 발행
     * @param memberToken: 회원 대체 식별키
     * @param isAccessToken: 액세스토큰 여부
     * @return LoginTokenInfo: 로그인 토큰 정보
     */
    @Override
    public String tokenPublish(String memberToken, boolean isAccessToken) {
    
        return isAccessToken ? jwtUtil.createAccessToken(memberToken) : jwtUtil.createRefreshToken(memberToken);
    }
}
