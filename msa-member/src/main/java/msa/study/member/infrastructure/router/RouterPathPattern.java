package msa.study.member.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    AUTH_ROOT("/auth", "/auth/**"),
    AUTH_MEMBER_REGISTER("/member-register", "/auth/member-register"),
    AUTH_MEMBER_LOGIN("/login", "/auth/login"),

    EXCHANGE_ROOT("/exchange", "/exchange/**"),
    EXCHANGE_MEMBER_TOKEN("/member-token/{memberToken}", "/exchange/member-token/{memberToken}");

    private final String path;
    private final String fullPath;
}
