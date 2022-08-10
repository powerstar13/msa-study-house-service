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
@Table(name = "rental")
public class Rental extends CommonDateEntity { // 임대료

    @Id
    @Column(value = "rentalId")
    private int rentalId; // 임대료 고유번호

    @Column(value = "rentalToken")
    private String rentalToken; // 임대료 대체 식별키

    @Column(value = "houseId")
    private int houseId; // 방 고유번호

    @Column(value = "rentalType")
    private RentalType rentalType; // 임대 유형

    @Column(value = "deposit")
    private int deposit; // 보증금

    @Column(value = "rent")
    private Integer rent; // 월세

    /**
     * 임대료 정보 수정
     * @param rentalType: 임대 유형
     * @param deposit: 보증금
     * @param rent: 월세
     */
    public void modify(RentalType rentalType, int deposit, Integer rent) {
        this.rentalType = rentalType;
        this.deposit = deposit;
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"rentalId\":" + rentalId
            + ", \"rentalToken\":\"" + rentalToken + "\""
            + ", \"houseId\":" + houseId
            + ", \"rentalType\":\"" + rentalType + "\""
            + ", \"deposit\":" + deposit
            + ", \"rent\":" + rent
            + "}";
    }
}
