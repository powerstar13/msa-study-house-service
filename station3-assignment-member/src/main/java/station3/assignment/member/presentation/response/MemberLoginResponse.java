package station3.assignment.member.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import station3.assignment.member.presentation.shared.response.SuccessResponse;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResponse extends SuccessResponse {
    
    private String memberToken; // 회원 대체 식별키
    private String memberName; // 회원 이름
    private String accessToken; // 액세스 토큰
    private String refreshToken; // 리프레시 토큰

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"memberToken\":\"" + memberToken + "\""
            + ", \"memberName\":\"" + memberName + "\""
            + ", \"accessToken\":\"" + accessToken + "\""
            + ", \"refreshToken\":\"" + refreshToken + "\""
            + "}";
    }
}
