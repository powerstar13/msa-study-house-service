package station3.assignment.house.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseType {

    ONE("원룸"),
    TWO("투룸"),
    THREE("쓰리룸");

    private final String description;
}
