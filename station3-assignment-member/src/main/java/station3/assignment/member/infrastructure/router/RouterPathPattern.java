package station3.assignment.member.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    MEMBER_REGISTER("/auth", "/member-register", "/auth/member-register"),
    EXCHANGE_MEMBER_TOKEN("/exchange", "/member-token", "/exchange/member-token");

    private final String path1;
    private final String path2;
    private final String fullPath;
}
