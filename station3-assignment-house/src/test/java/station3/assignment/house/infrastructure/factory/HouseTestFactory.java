package station3.assignment.house.infrastructure.factory;

import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.HouseType;
import station3.assignment.house.domain.Rental;
import station3.assignment.house.domain.RentalType;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.infrastructure.webClient.response.ExchangeMemberTokenResponse;
import station3.assignment.house.presentation.request.HouseModifyRequest;
import station3.assignment.house.presentation.request.HouseRegisterRequest;
import station3.assignment.house.presentation.request.dto.RentalModifyRequest;
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
     * 임대료 수정 정보 구성
     * @param rentalToken: 임대료 대체 식별키
     * @param rentalType: 임대 유형
     * @param deposit: 보증금
     * @param rent: 월세
     */
    private static HouseCommand.RentalModifyDTO rentalModifyDTOCommand(String rentalToken, RentalType rentalType, Integer deposit, Integer rent) {

        return HouseCommand.RentalModifyDTO.builder()
            .rentalToken(rentalToken)
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
            rentalRegisterDTOCommand(RentalType.JEONSE, 5000, null),
            rentalRegisterDTOCommand(RentalType.MONTHLY, 1000, 50)
        );
    }

    /**
     * 임대료 수정 정보 목록
     */
    private static List<HouseCommand.RentalModifyDTO> rentalModifyDTOCommandList() {

        return Arrays.asList(
            rentalModifyDTOCommand("rentalToken", RentalType.JEONSE, 6000, null),
            rentalModifyDTOCommand(null, RentalType.MONTHLY, 1500, 40)
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
     * 방 수정 정보
     */
    public static HouseCommand.HouseModify houseModifyCommand() {

        return HouseCommand.HouseModify.builder()
            .houseToken("houseToken")
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalModifyDTOList(rentalModifyDTOCommandList())
            .build();
    }

    /**
     * 회원 고유번호를 담을 방 등록 정보
     */
    public static HouseCommand.ExchangedHouseRegister exchangedHouseRegisterCommand() {

        return HouseCommand.ExchangedHouseRegister.builder()
            .memberId(1)
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
     * 임대료 구성
     * @param rentalId: 임대료 고유번호
     * @param houseId: 방 고유번호
     * @param rentalType: 임대 유형
     * @param deposit: 보증금
     * @param rent: 월세
     */
    public static Rental rental(int rentalId, int houseId, RentalType rentalType, Integer deposit, Integer rent) {

        return Rental.builder()
            .rentalId(rentalId)
            .rentalToken(UUID.randomUUID().toString())
            .houseId(houseId)
            .rentalType(rentalType)
            .deposit(deposit)
            .rent(rent)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Mono<Rental> rentalMono() {
        return Mono.just(rental(1, 1, RentalType.JEONSE, 5000, null));
    }

    public static Flux<Rental> rentalFlux() {

        return Flux.just(
            rental(1, 1, RentalType.JEONSE, 5000, null),
            rental(2, 1, RentalType.MONTHLY, 1000, 50)
        );
    }

    /**
     * 방 애그리거트
     */
    public static HouseDTO.HouseAggregate houseAggregate() {

        return HouseDTO.HouseAggregate.builder()
            .house(house())
            .rentalFlux(rentalFlux())
            .build();
    }

    public static Mono<HouseDTO.HouseAggregate> houseAggregateMono() {
        return Mono.just(houseAggregate());
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
            rentalRegisterRequest(RentalType.MONTHLY, 1000, 50)
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

    /**
     * 회원 고유번호 가져오기 통신 결과
     */
    public static ExchangeMemberTokenResponse exchangeMemberTokenResponse() {

        return ExchangeMemberTokenResponse.builder()
            .rt(HttpStatus.OK.value())
            .rtMsg(HttpStatus.OK.getReasonPhrase())
            .memberId(1)
            .build();
    }

    public static Mono<ExchangeMemberTokenResponse> exchangeMemberTokenResponseMono() {
        return Mono.just(exchangeMemberTokenResponse());
    }

    /**
     * 임대료 수정 Request 구성
     * @param rentalToken: 임대료 대체 식별키 (수정할 경우 필수)
     * @param rentalType: 임대 유형
     * @param deposit: 보증금
     * @param rent: 월세
     */
    private static RentalModifyRequest rentalModifyRequest(String rentalToken, RentalType rentalType, Integer deposit, Integer rent) {

        return RentalModifyRequest.builder()
            .rentalToken(rentalToken)
            .rentalType(rentalType)
            .deposit(deposit)
            .rent(rent)
            .build();
    }

    /**
     * 임대료 수정 Request 목록
     */
    private static List<RentalModifyRequest> rentalModifyRequestList() {

        return Arrays.asList(
            rentalModifyRequest("rentalToken", RentalType.JEONSE, 6000, null),
            rentalModifyRequest(null, RentalType.MONTHLY, 1500, 40)
        );
    }

    /**
     * 방 수정 Request
     */
    public static HouseModifyRequest houseModifyRequest() {

        return HouseModifyRequest.builder()
            .houseToken(houseToken)
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalModifyRequestList(rentalModifyRequestList())
            .build();
    }
}
