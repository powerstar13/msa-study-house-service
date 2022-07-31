package station3.assignment.member.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    AUTH_ROOT("/auth", "/auth/**"),
    AUTH_MEMBER_REGISTER("/member-register", "/auth/member-register"),

    EXCHANGE_ROOT("/exchange", "/exchange/**"),
    EXCHANGE_MEMBER_TOKEN("/member-token", "/exchange/member-token");

    private final String path;
    private final String fullPath;
}
