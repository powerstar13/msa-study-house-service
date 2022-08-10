package msa.study.member.infrastructure.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClaimType {
    
    MEMBER_TOKEN("memberToken", "회원 대체 식별키");
    
    private final String name;
    private final String description;
}
