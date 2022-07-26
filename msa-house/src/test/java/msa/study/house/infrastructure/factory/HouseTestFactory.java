package msa.study.house.infrastructure.factory;

import msa.study.house.infrastructure.webClient.response.ExchangeMemberTokenResponse;
import msa.study.house.presentation.request.HousePageRequest;
import msa.study.house.presentation.response.HouseListResponse;
import msa.study.house.presentation.response.HousePageResponse;
import msa.study.house.presentation.response.dto.HouseInfoResponseDTO;
import msa.study.house.presentation.shared.response.PageResponseDTO;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.House;
import msa.study.house.domain.HouseType;
import msa.study.house.domain.Rental;
import msa.study.house.domain.RentalType;
import msa.study.house.domain.service.dto.HouseDTO;
import msa.study.house.presentation.request.HouseModifyRequest;
import msa.study.house.presentation.request.HouseRegisterRequest;
import msa.study.house.presentation.request.dto.RentalModifyRequest;
import msa.study.house.presentation.request.dto.RentalRegisterRequest;
import msa.study.house.presentation.response.HouseInfoResponse;
import msa.study.house.presentation.response.HouseRegisterResponse;
import msa.study.house.presentation.response.dto.RentalInfoResponseDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HouseTestFactory {

    private static final HouseType houseType = HouseType.ONE;
    private static final RentalType rentalType = RentalType.MONTHLY;
    private static final String houseAddress = "방 주소";

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
            rentalRegisterDTOCommand(RentalType.JEONSE, 5000, null),
            rentalRegisterDTOCommand(RentalType.MONTHLY, 1000, 50)
        );
    }

    /**
     * 방 등록 정보
     */
    public static HouseCommand.HouseRegister houseRegisterCommand() {

        return HouseCommand.HouseRegister.builder()
            .memberToken(UUID.randomUUID().toString())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalList(rentalRegisterDTOCommandList())
            .build();
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
            .memberToken(UUID.randomUUID().toString())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalList(rentalRegisterRequestList())
            .build();
    }

    /**
     * 방 등록 Response
     */
    public static HouseRegisterResponse houseRegisterResponse() {

        return HouseRegisterResponse.builder()
            .houseToken(UUID.randomUUID().toString())
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
     * 임대료 수정 정보 목록
     */
    private static List<HouseCommand.RentalModifyDTO> rentalModifyDTOCommandList() {

        return Arrays.asList(
            rentalModifyDTOCommand(UUID.randomUUID().toString(), RentalType.JEONSE, 6000, null),
            rentalModifyDTOCommand(null, RentalType.MONTHLY, 1500, 40)
        );
    }

    /**
     * 방 수정 정보
     */
    public static HouseCommand.HouseModify houseModifyCommand() {

        return HouseCommand.HouseModify.builder()
            .houseToken(UUID.randomUUID().toString())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalList(rentalModifyDTOCommandList())
            .build();
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
            rentalModifyRequest(UUID.randomUUID().toString(), RentalType.JEONSE, 6000, null),
            rentalModifyRequest(null, RentalType.MONTHLY, 1500, 40)
        );
    }

    /**
     * 방 수정 Request
     */
    public static HouseModifyRequest houseModifyRequest() {

        return HouseModifyRequest.builder()
            .houseToken(UUID.randomUUID().toString())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalList(rentalModifyRequestList())
            .build();
    }

    /**
     * 회원 고유번호를 담을 방 등록 정보
     */
    public static HouseCommand.ExchangedHouseRegister exchangedHouseRegisterCommand() {

        return HouseCommand.ExchangedHouseRegister.builder()
            .memberId(RandomUtils.nextInt())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalList(rentalRegisterDTOCommandList())
            .build();
    }

    /**
     * 방 정보
     */
    public static House house() {

        return House.builder()
            .houseId(RandomUtils.nextInt())
            .houseToken(UUID.randomUUID().toString())
            .memberId(RandomUtils.nextInt())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Mono<House> houseMono() {
        return Mono.just(house());
    }

    public static Flux<House> houseFlux() {
        return Flux.just(house(), house());
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
        return Mono.just(rental(RandomUtils.nextInt(), RandomUtils.nextInt(), RentalType.JEONSE, 5000, null));
    }

    public static Flux<Rental> rentalFlux() {

        return Flux.just(
            rental(RandomUtils.nextInt(), 1, RentalType.JEONSE, 5000, null),
            rental(RandomUtils.nextInt(), 1, RentalType.MONTHLY, 1000, 50)
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
     * 임대료 정보
     * @param rentalType: 임대 유형
     * @param deposit: 보증금
     * @param rent: 월세
     */
    public static HouseDTO.RentalInfo rentalInfo(RentalType rentalType, Integer deposit, Integer rent) {

        return HouseDTO.RentalInfo.builder()
            .rentalToken(UUID.randomUUID().toString())
            .rentalType(rentalType)
            .deposit(deposit)
            .rent(rent)
            .build();
    }

    public static List<HouseDTO.RentalInfo> rentalInfoList() {

        return Arrays.asList(
            rentalInfo(RentalType.JEONSE, 5000, null),
            rentalInfo( RentalType.MONTHLY, 1000, 50)
        );
    }

    /**
     * 방 정보
     */
    public static HouseDTO.HouseInfo houseInfoDTO() {

        return HouseDTO.HouseInfo.builder()
            .houseToken(UUID.randomUUID().toString())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalList(rentalInfoList())
            .build();
    }

    public static Mono<HouseDTO.HouseInfo> houseInfoMono() {
        return Mono.just(houseInfoDTO());
    }

    /**
     * 방 목록
     */
    public static HouseDTO.HouseList houseListDTO() {

        return HouseDTO.HouseList.builder()
            .houseList(
                Arrays.asList(
                    houseInfoDTO(), houseInfoDTO()
                )
            )
            .build();
    }

    public static Mono<HouseDTO.HouseList> houseListMono() {
        return Mono.just(houseListDTO());
    }

    /**
     * 방 대체 식별키 정보
     */
    public static HouseDTO.HouseTokenInfo houseTokenInfo() {
        return HouseDTO.HouseTokenInfo.builder()
            .houseToken(UUID.randomUUID().toString())
            .build();
    }

    public static Mono<HouseDTO.HouseTokenInfo> houseTokenInfoMono() {
        return Mono.just(houseTokenInfo());
    }

    /**
     * 회원 고유번호 가져오기 통신 결과
     */
    public static ExchangeMemberTokenResponse exchangeMemberTokenResponse() {

        return ExchangeMemberTokenResponse.builder()
            .rt(HttpStatus.OK.value())
            .rtMsg(HttpStatus.OK.getReasonPhrase())
            .memberId(RandomUtils.nextInt())
            .build();
    }

    public static Mono<ExchangeMemberTokenResponse> exchangeMemberTokenResponseMono() {
        return Mono.just(exchangeMemberTokenResponse());
    }

    /**
     * 임대료 정보
     * @param rentalType: 임대 유형
     * @param deposit: 보증금
     * @param rent: 월세
     */
    public static RentalInfoResponseDTO rentalInfoResponseDTO(RentalType rentalType, Integer deposit, Integer rent) {

        return RentalInfoResponseDTO.builder()
            .rentalToken(UUID.randomUUID().toString())
            .rentalType(rentalType)
            .deposit(deposit)
            .rent(rent)
            .build();
    }

    public static List<RentalInfoResponseDTO> rentalInfoResponseDTOList() {

        return Arrays.asList(
            rentalInfoResponseDTO(RentalType.JEONSE, 5000, null),
            rentalInfoResponseDTO( RentalType.MONTHLY, 1000, 50)
        );
    }

    /**
     * 내방 정보 조회 Response
     */
    public static HouseInfoResponse houseInfoResponse() {

        return HouseInfoResponse.builder()
            .houseToken(UUID.randomUUID().toString())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalList(rentalInfoResponseDTOList())
            .build();
    }

    /**
     * 내방 정보 조회 Response
     */
    public static HouseInfoResponseDTO houseInfoResponseDTO() {

        return HouseInfoResponseDTO.builder()
            .houseToken(UUID.randomUUID().toString())
            .houseAddress(houseAddress)
            .houseType(houseType)
            .rentalList(rentalInfoResponseDTOList())
            .build();
    }

    /**
     * 내방 목록 조회 Response
     */
    public static HouseListResponse houseListResponse() {

        return HouseListResponse.builder()
            .houseList(
                Arrays.asList(
                    houseInfoResponseDTO(),
                    houseInfoResponseDTO()
                )
            )
            .build();
    }

    public static HouseCommand.HousePage housePageCommand() {
        return HouseCommand.HousePage.builder()
            .page(1)
            .size(10)
            .houseType(HouseType.ONE)
            .rentalType(RentalType.JEONSE)
            .depositStartRange(1000)
            .depositEndRange(5000)
            .rentStartRange(0)
            .rentEndRange(50)
            .build();
    }

    public static HouseDTO.pageInfo pageInfo() {
        return HouseDTO.pageInfo.builder()
            .currentSize(10)
            .currentPage(1)
            .totalPage(32)
            .totalCount(320)
            .build();
    }

    public static HouseDTO.HousePage housePageDTO() {
        return HouseDTO.HousePage.builder()
            .houseList(
                Arrays.asList(
                    houseInfoDTO(), houseInfoDTO()
                )
            )
            .pageInfo(pageInfo())
            .build();
    }

    public static Mono<HouseDTO.HousePage> housePageMono() {
        return Mono.just(housePageDTO());
    }

    /**
     * 전체방 페이지 조회 Request
     */
    public static HousePageRequest housePageRequest() {
        return HousePageRequest.builder()
            .page(1)
            .size(10)
            .houseType(houseType)
            .rentalType(rentalType)
            .depositStartRange(1000)
            .depositEndRange(20000)
            .rentStartRange(0)
            .rentEndRange(50)
            .build();
    }

    public static PageResponseDTO pageResponseDTO() {
        return PageResponseDTO.builder()
            .currentSize(10)
            .currentPage(1)
            .totalPage(32)
            .totalCount(320)
            .build();
    }

    /**
     * 전체방 페이지 조회 Response
     */
    public static HousePageResponse housePageResponse() {
        return HousePageResponse.builder()
            .pageInfo(pageResponseDTO())
            .houseList(Arrays.asList(houseInfoResponseDTO(), houseInfoResponseDTO()))
            .build();
    }
}
