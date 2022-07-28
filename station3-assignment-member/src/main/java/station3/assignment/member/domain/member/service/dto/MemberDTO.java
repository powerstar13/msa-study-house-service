package station3.assignment.member.domain.member.service.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberDTO {

    @Getter
    @Builder
    public static class MemberTokenInfo {

        private String memberToken;

        @Override
        public String toString() {
            return "{"
                + "\"memberToken\":\"" + memberToken + "\""
                + "}";
        }
    }
}
