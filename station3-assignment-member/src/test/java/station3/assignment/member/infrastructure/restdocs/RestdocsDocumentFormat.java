package station3.assignment.member.infrastructure.restdocs;

import org.springframework.restdocs.snippet.Attributes;
import station3.assignment.member.domain.MemberType;

import static org.springframework.restdocs.snippet.Attributes.key;

public class RestdocsDocumentFormat {
    
    public static Attributes.Attribute setFormat(String value) {
        return key("format").value(value);
    }

    public static Attributes.Attribute memberTypeFormat() {
        return setFormat(String.format(
            "%s: %s, %s: %s",
            MemberType.LESSOR.name(), MemberType.LESSOR.getDescription(),
            MemberType.LESSEE.name(), MemberType.LESSEE.getDescription()
        ));
    }
    
    public static Attributes.Attribute birthFormat() {
        return setFormat("yyyy-MM-dd");
    }

    public static Attributes.Attribute phoneFormat() {
        return setFormat("01012341234");
    }
}
