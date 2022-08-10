package msa.study.member.infrastructure.jwt.factory;

public interface TokenStore {
    
    String tokenPublish(String memberToken, boolean isAccessToken);
}
