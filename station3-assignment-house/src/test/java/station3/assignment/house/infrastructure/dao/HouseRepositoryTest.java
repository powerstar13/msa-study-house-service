package station3.assignment.house.infrastructure.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.domain.RentalType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HouseRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @DisplayName("전체방 페이지 조회")
    @Test
    void housePage() {
        HouseCommand.HousePage command = HouseCommand.HousePage.builder()
            .houseType(HouseType.ONE)
            .rentalType(RentalType.JEONSE)
            .build();

        StepVerifier.create(houseRepository.housePage(command).log())
            .thenConsumeWhile(house -> true)
            .verifyComplete();
    }
}