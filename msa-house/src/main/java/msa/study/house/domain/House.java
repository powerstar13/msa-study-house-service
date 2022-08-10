package msa.study.house.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import msa.study.house.domain.shared.CommonDateEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "house")
public class House extends CommonDateEntity { // 방

    @Id
    @Column(value = "houseId")
    private int houseId; // 방 고유번호

    @Column(value = "houseToken")
    private String houseToken; // 방 대체 식별키

    @Column(value = "memberId")
    private int memberId; // 회원 고유번호

    @Column(value = "houseAddress")
    private String houseAddress; // 방 주소

    @Column(value = "houseType")
    private HouseType houseType; // 방 유형

    /**
     * 내방 정보 수정
     * @param houseAddress: 방 주소
     * @param houseType: 방 유형
     */
    public void modify(String houseAddress, HouseType houseType) {
        this.houseAddress = houseAddress;
        this.houseType = houseType;
    }

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"houseId\":" + houseId
            + ", \"houseToken\":\"" + houseToken + "\""
            + ", \"memberId\":" + memberId
            + ", \"houseAddress\":\"" + houseAddress + "\""
            + ", \"houseType\":\"" + houseType + "\""
            + "}";
    }
}
