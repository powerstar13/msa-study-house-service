package station3.assignment.house.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RentalType {

    JEONSE("전세"),
    MONTHLY("월세");

    private final String description;
}
