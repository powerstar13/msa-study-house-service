package station3.assignment.member.domain.house;

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
