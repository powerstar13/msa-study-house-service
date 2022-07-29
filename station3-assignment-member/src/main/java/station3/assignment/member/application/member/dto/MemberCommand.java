package station3.assignment.member.application.member.dto;

import lombok.Builder;
import lombok.Getter;
import station3.assignment.member.domain.member.MemberType;

import java.time.LocalDate;

public class MemberCommand {

    @Getter
    @Builder
    public static class MemberRegister {

        private MemberType memberType; // 회원 유형
        private String memberLoginId; // 회원 로그인 아이디
        private String memberPassword; // 회원 비밀번호
        private String memberName; // 회원 이름
        private LocalDate memberBirth; // 회원 생년월일
        private String memberPhone; // 회원 연락처

        @Override
        public String toString() {
            return "{"
                + "\"memberType\":\"" + memberType + "\""
                + ", \"memberLoginId\":\"" + memberLoginId + "\""
                + ", \"memberPassword\":\"" + memberPassword + "\""
                + ", \"memberName\":\"" + memberName + "\""
                + ", \"memberBirth\":" + memberBirth
                + ", \"memberPhone\":\"" + memberPhone + "\""
                + "}";
        }
    }
}
