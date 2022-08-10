package msa.study.member.domain.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberDTO {

    @Getter
    @Builder
    public static class MemberTokenInfo {

        private String memberToken; // 회원 대체 식별키
        private String accessToken; // 액세스 토큰
        private String refreshToken; // 리프레시 토큰

        /**
         * JWT 발행
         */
        public void jwtRegister(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        @Override
        public String toString() {
            return "{"
                + "\"memberToken\":\"" + memberToken + "\""
                + ", \"accessToken\":\"" + accessToken + "\""
                + ", \"refreshToken\":\"" + refreshToken + "\""
                + "}";
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberIdInfo {

        private int memberId; // 회원 고유번호

        @Override
        public String toString() {
            return "{"
                + "\"memberId\":" + memberId
                + "}";
        }
    }

    @Getter
    @Builder
    public static class MemberLoginInfo {

        private String memberToken; // 회원 대체 식별키
        private String memberName; // 회원 이름
        private String accessToken; // 액세스 토큰
        private String refreshToken; // 리프레시 토큰

        @Override
        public String toString() {
            return "{"
                + "\"memberToken\":\"" + memberToken + "\""
                + ", \"memberName\":\"" + memberName + "\""
                + ", \"accessToken\":\"" + accessToken + "\""
                + ", \"refreshToken\":\"" + refreshToken + "\""
                + "}";
        }
    }
}
