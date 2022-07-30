package station3.assignment.house.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.application.dto.HouseCommandMapper;
import station3.assignment.house.domain.service.HouseService;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.infrastructure.exception.status.BadRequestException;
import station3.assignment.house.infrastructure.webClient.MemberWebClientService;

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
}
