package msa.study.member.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.study.member.presentation.shared.response.SuccessResponse;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeMemberTokenResponse extends SuccessResponse {

    private int memberId; // 회원 고유번호

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"memberId\":" + memberId
            + "}";
    }
}
