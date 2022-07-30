package station3.assignment.house.application.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.house.domain.service.dto.HouseDTO;

@Component
@RequiredArgsConstructor
public class HouseFacade {
    
//    private final HouseService houseService;
    
    /**
     * 내방 등록
     * @param command: 등록할 내방 정보
     * @return HouseTokenInfo: 방 대체 식별키
     */
    public Mono<HouseDTO.HouseTokenInfo> houseRegister(HouseCommand.HouseRegister command) {
//        return houseService.houseRegister(command); // 내방 등록 처리
        return null;
    }
}
