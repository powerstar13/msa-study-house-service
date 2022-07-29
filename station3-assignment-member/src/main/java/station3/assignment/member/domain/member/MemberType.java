package station3.assignment.member.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {

    LESSOR("임대인"),
    LESSEE("임차인");

    private final String description;
}
