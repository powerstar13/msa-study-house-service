package station3.assignment.member.domain.house;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RentalType {

    JEONSE("전세"),
    MONTHLY("월세");

    private final String description;
}
