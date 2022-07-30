package station3.assignment.house.infrastructure.router;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RouterPathPattern {
    
    HOUSE_REGISTER("/house", "/register", "/house/register");
    
    private final String path1;
    private final String path2;
    private final String fullPath;
}
