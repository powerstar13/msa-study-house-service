package station3.assignment.member.domain.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import station3.assignment.member.domain.shared.CommonDateEntity;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member extends CommonDateEntity { // 회원

    @Id
    @Column(value = "memberId")
    private int memberId; // 회원 고유번호

    @Column(value = "memberToken")
    private String memberToken; // 회원 대체 식별키

    @Column(value = "memberType")
    private MemberType memberType; // 회원 유형

    @Column(value = "memberLoginId")
    private String memberLoginId; // 회원 로그인 아이디

    @Column(value = "memberPassword")
    private String memberPassword; // 회원 비밀번호

    @Column(value = "memberName")
    private String memberName; // 회원 이름

    @Column(value = "memberBirth")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate memberBirth; // 회원 생년월일

    @Column(value = "memberPhone")
    private String memberPhone; // 회원 연락처

    /**
     * 대체 식별키 생성
     */
    public void memberTokenRegister() {
        this.memberToken = UUID.randomUUID().toString();
    }

    /**
     * 암호화된 비밀번호 등록
     */
    public void encodedMemberPasswordRegister(String encodedMemberPassword) {
        this.memberPassword = encodedMemberPassword;
    }

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"memberId\":" + memberId
            + ", \"memberToken\":\"" + memberToken + "\""
            + ", \"memberType\":\"" + memberType + "\""
            + ", \"memberLoginId\":\"" + memberLoginId + "\""
            + ", \"memberPassword\":\"" + memberPassword + "\""
            + ", \"memberName\":\"" + memberName + "\""
            + ", \"memberBirth\":" + memberBirth
            + ", \"memberPhone\":\"" + memberPhone + "\""
            + "}";
    }
}
