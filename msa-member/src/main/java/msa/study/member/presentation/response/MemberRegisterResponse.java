package msa.study.member.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.study.member.presentation.shared.response.CreatedSuccessResponse;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterResponse extends CreatedSuccessResponse {

    private String memberToken; // 회원 대체 식별키
    private String accessToken; // 액세스 토큰
    private String refreshToken; // 리프레시 토큰

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"memberToken\":\"" + memberToken + "\""
            + ", \"accessToken\":\"" + accessToken + "\""
            + ", \"refreshToken\":\"" + refreshToken + "\""
            + "}";
    }
}
