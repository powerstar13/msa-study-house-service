package station3.assignment.member.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import station3.assignment.member.infrastructure.exception.status.BadRequestException;
import station3.assignment.member.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.member.presentation.shared.request.RequestVerify;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequest implements RequestVerify {
    
    private String memberLoginId; // 로그인 아이디
    private String memberPassword; // 비밀번호
    
    @Override
    public void verify() {
        if (StringUtils.isBlank(memberLoginId)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberLoginId.getMessage());
        if (StringUtils.isBlank(memberPassword)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberPassword.getMessage());
    }

    @Override
    public String toString() {
        return "{"
            + "\"memberLoginId\":\"" + memberLoginId + "\""
            + ", \"memberPassword\":\"" + memberPassword + "\""
            + "}";
    }
}
