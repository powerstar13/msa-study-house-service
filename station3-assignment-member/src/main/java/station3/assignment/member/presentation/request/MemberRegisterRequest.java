package station3.assignment.member.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import station3.assignment.member.domain.MemberType;
import station3.assignment.member.infrastructure.exception.status.BadRequestException;
import station3.assignment.member.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.member.presentation.shared.request.RequestVerify;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegisterRequest implements RequestVerify {

    private MemberType memberType; // 회원 유형
    private String memberLoginId; // 회원 로그인 아이디
    private String memberPassword; // 회원 비밀번호
    private String memberName; // 회원 이름
    private LocalDate memberBirth; // 회원 생년월일
    private String memberPhone; // 회원 연락처

    @Override
    public void verify() {
        if (memberType == null) throw new BadRequestException(ExceptionMessage.IsRequiredMemberType.getMessage());
        if (StringUtils.isBlank(memberLoginId)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberLoginId.getMessage());
        if (StringUtils.isBlank(memberPassword)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberPassword.getMessage());
        if (StringUtils.isBlank(memberName)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberName.getMessage());
        if (memberBirth == null) throw new BadRequestException(ExceptionMessage.IsRequiredMemberBirth.getMessage());
        if (StringUtils.isBlank(memberPhone)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberPhone.getMessage());
    }

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
