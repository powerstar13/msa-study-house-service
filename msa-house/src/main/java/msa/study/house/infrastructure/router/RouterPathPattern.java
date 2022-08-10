package msa.study.house.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {

    HOUSE_ROOT("/house", "/house/**"),
    HOUSE_REGISTER("/register", "/house/register"),
    HOUSE_MODIFY("/modify", "/house/modify"),
    HOUSE_DELETE("/delete/{houseToken}", "/house/delete/{houseToken}"),
    HOUSE_INFO("/info/{houseToken}", "/house/info/{houseToken}"),
    HOUSE_LIST("/list/{memberToken}", "/house/list/{memberToken}"),
    HOUSE_PAGE("/page", "/house/page");

    private final String path;
    private final String fullPath;
}
