package msa.study.house.infrastructure.restdocs;

import org.springframework.restdocs.snippet.Attributes;
import msa.study.house.domain.HouseType;
import msa.study.house.domain.RentalType;

import static org.springframework.restdocs.snippet.Attributes.key;

public class RestdocsDocumentFormat {
    
    public static Attributes.Attribute setFormat(String value) {
        return key("format").value(value);
    }

    public static Attributes.Attribute houseTypeFormat() {
        return setFormat(String.format(
            "%s: %s, " +
                "%s: %s, " +
                "%s: %s",
            HouseType.ONE.name(), HouseType.ONE.getDescription(),
            HouseType.TWO.name(), HouseType.TWO.getDescription(),
            HouseType.THREE.name(), HouseType.THREE.getDescription()
        ));
    }

    public static Attributes.Attribute rentalTypeFormat() {
        return setFormat(String.format(
            "%s: %s, " +
                "%s: %s",
            RentalType.JEONSE.name(), RentalType.JEONSE.getDescription(),
            RentalType.MONTHLY.name(), RentalType.MONTHLY.getDescription()
        ));
    }
}
