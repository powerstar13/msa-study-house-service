package station3.assignment.house.infrastructure.factory;

import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.domain.RentalType;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.presentation.request.HouseRegisterRequest;
import station3.assignment.house.presentation.request.dto.RentalRegisterRequest;
import station3.assignment.house.presentation.response.HouseRegisterResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HouseTestFactory {

    private static final HouseType houseType = HouseType.ONE;
    private static final String houseAddress = "방 주소";
    private static final String houseToken = UUID.randomUUID().toString();
    private static final String memberToken = UUID.randomUUID().toString();

    /**
     * 임대료 등록 정보 구성
     * @param rentalType: 임대 유형
     * @param deposit: 보증금
     * @param rent: 월세
     */
    private static HouseCommand.RentalRegisterDTO rentalRegisterDTOCommand(RentalType rentalType, Integer deposit, Integer rent) {

        return HouseCommand.RentalRegisterDTO.builder()
            .rentalType(rentalType)
            .deposit(deposit)
            .rent(rent)
            .build();
    }

    /**
     * 임대료 등록 정보 목록
     */
    private static List<HouseCommand.RentalRegisterDTO> rentalRegisterDTOCommandList() {

        return Arrays.asList(
            rentalRegisterDTOCommand(RentalType.JEONSE, 10000, null),
            rentalRegisterDTOCommand(RentalType.MONTHLY, 2500, 25),
            rentalRegisterDTOCommand(RentalType.MONTHLY, 1000, 55)
        );
    }

    /**
     * 방 등록 정보
     */
    public static HouseCommand.HouseRegister houseRegisterCommand() {

        return HouseCommand.HouseRegister.builder()
            .memberToken("memberToken")
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalRegisterDTOList(rentalRegisterDTOCommandList())
            .build();
    }

    /**
     * 방 정보
     */
    public static House house() {

        return House.builder()
            .houseId(1)
            .houseToken(houseToken)
            .memberId(1)
            .houseAddress(houseAddress)
            .houseType(houseType)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Mono<House> houseMono() {
        return Mono.just(house());
    }

    /**
     * 방 대체 식별키 정보
     */
    public static HouseDTO.HouseTokenInfo houseTokenInfo() {
        return HouseDTO.HouseTokenInfo.builder()
            .houseToken(houseToken)
            .build();
    }

    public static Mono<HouseDTO.HouseTokenInfo> houseTokenInfoMono() {
        return Mono.just(houseTokenInfo());
    }

    /**
     * 임대료 등록 Request 구성
     * @param rentalType: 임대 유형
     * @param deposit: 보증금
     * @param rent: 월세
     */
    private static RentalRegisterRequest rentalRegisterRequest(RentalType rentalType, Integer deposit, Integer rent) {

        return RentalRegisterRequest.builder()
            .rentalType(rentalType)
            .deposit(deposit)
            .rent(rent)
            .build();
    }

    /**
     * 임대료 등록 Request 목록
     */
    private static List<RentalRegisterRequest> rentalRegisterRequestList() {

        return Arrays.asList(
            rentalRegisterRequest(RentalType.JEONSE, 10000, null),
            rentalRegisterRequest(RentalType.MONTHLY, 2500, 25),
            rentalRegisterRequest(RentalType.MONTHLY, 1000, 55)
        );
    }

    /**
     * 방 등록 Request
     */
    public static HouseRegisterRequest houseRegisterRequest() {
        return HouseRegisterRequest.builder()
            .memberToken(memberToken)
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalRegisterRequestList(rentalRegisterRequestList())
            .build();
    }

    /**
     * 방 등록 Response
     */
    public static HouseRegisterResponse houseRegisterResponse() {
        return HouseRegisterResponse.builder()
            .houseToken(houseToken)
            .build();
    }
}
