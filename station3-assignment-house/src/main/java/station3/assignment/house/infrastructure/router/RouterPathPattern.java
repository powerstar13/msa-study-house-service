package station3.assignment.house.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    HOUSE_ROOT("/house", "/house/**"),
    HOUSE_REGISTER("/register", "/house/register"),
    HOUSE_MODIFY("/modify", "/house/modify"),
    HOUSE_DELETE("/delete/{houseToken}", "/house/delete/{houseToken}");

    private final String path;
    private final String fullPath;
}
