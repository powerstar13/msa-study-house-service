package station3.assignment.member.domain.house;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import station3.assignment.member.domain.shared.CommonDateEntity;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rental")
public class Rental extends CommonDateEntity { // 임대료

    @Column(value = "rentalId")
    private int rentalId; // 임대료 고유번호

    @Column(value = "houseId")
    private int houseId; // 방 고유번호

    @Column(value = "rentalType")
    private RentalType rentalType; // 임대 유형

    @Column(value = "deposit")
    private int deposit; // 보증금

    @Column(value = "rent")
    private Integer rent; // 월세

    @Override
    public String toString() {
        return "{"
            + super.toString().replace("}", "")
            + ", \"rentalId\":" + rentalId
            + ", \"houseId\":" + houseId
            + ", \"rentalType\":\"" + rentalType + "\""
            + ", \"deposit\":" + deposit
            + ", \"rent\":" + rent
            + "}";
    }
}
