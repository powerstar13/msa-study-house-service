package msa.study.house.application;

import lombok.RequiredArgsConstructor;
import msa.study.house.domain.service.HouseService;
import msa.study.house.domain.service.dto.HouseDTO;
import msa.study.house.infrastructure.exception.status.BadRequestException;
import msa.study.house.infrastructure.webClient.MemberWebClientService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.application.dto.HouseCommandMapper;

@Component
@RequiredArgsConstructor
public class HouseFacade {

    private final MemberWebClientService memberWebClientService;
    private final HouseCommandMapper houseCommandMapper;
    private final HouseService houseService;

    /**
     * 내방 등록
     * @param command: 등록할 내방 정보
     * @return HouseTokenInfo: 방 대체 식별키
     */
    public Mono<HouseDTO.HouseTokenInfo> houseRegister(HouseCommand.HouseRegister command) {

        return memberWebClientService.exchangeMemberToken(command.getMemberToken()) // 1. 회원 대체 식별키로 회원 고유번호 가져오기
            .flatMap(exchangeMemberTokenResponse -> {
                if (exchangeMemberTokenResponse.getRt() != 200) return Mono.error(new BadRequestException(exchangeMemberTokenResponse.getRtMsg()));

                HouseCommand.ExchangedHouseRegister exchangedHouseRegister = houseCommandMapper.of(exchangeMemberTokenResponse.getMemberId(), command);

                return houseService.houseRegister(exchangedHouseRegister); // 2. 내방 등록 처리
            });
    }

    /**
     * 내방 수정
     * @param command: 수정할 내방 정보
     */
    public Mono<Void> houseModify(HouseCommand.HouseModify command) {
        return houseService.houseModify(command); // 내방 수정 처리
    }

    /**
     * 내방 삭제
     * @param houseToken: 삭제할 방 대체 식별키
     */
    public Mono<Void> houseDelete(String houseToken) {
        return houseService.houseDelete(houseToken); // 내방 삭제 처리
    }

    /**
     * 내방 정보 조회
     * @param houseToken: 방 대체 식별키
     */
    public Mono<HouseDTO.HouseInfo> houseInfo(String houseToken) {
        return houseService.houseInfo(houseToken); // 내방 정보 조회 처리
    }

    /**
     * 내방 목록 조회
     * @param memberToken: 등록할 내방 정보
     * @return HouseList: 방 목록
     */
    public Mono<HouseDTO.HouseList> houseList(String memberToken) {

        return memberWebClientService.exchangeMemberToken(memberToken) // 1. 회원 대체 식별키로 회원 고유번호 가져오기
            .flatMap(exchangeMemberTokenResponse -> {
                if (exchangeMemberTokenResponse.getRt() != 200) return Mono.error(new BadRequestException(exchangeMemberTokenResponse.getRtMsg()));

                return houseService.houseList(exchangeMemberTokenResponse.getMemberId()); // 2. 내방 목록 조회 처리
            });
    }

    /**
     * 전체방 페이지 조회
     * @param command: 조회할 정보
     * @return HousePage: 방 페이지
     */
    public Mono<HouseDTO.HousePage> housePage(HouseCommand.HousePage command) {
        return houseService.housePage(command); // 전체방 페이지 조회 처리
    }
}
