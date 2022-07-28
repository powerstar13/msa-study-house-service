package station3.assignment.member.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import station3.assignment.member.presentation.shared.response.CreatedSuccessResponse;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterResponse extends CreatedSuccessResponse {

    private String memberToken; // 회원 대체 식별키

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"memberToken\":\"" + memberToken + "\""
            + "}";
    }
}
