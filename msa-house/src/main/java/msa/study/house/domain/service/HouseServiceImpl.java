package msa.study.house.domain.service;

import lombok.RequiredArgsConstructor;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.service.dto.HouseDTO;
import msa.study.house.domain.service.dto.HouseDTOMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseStore houseStore;
    private final HouseReader houseReader;
    private final HouseDTOMapper houseDTOMapper;

    /**
     * 내방 등록 처리
     * @param command: 등록할 내방 정보
     * @return HouseTokenInfo: 방 대체 식별키
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<HouseDTO.HouseTokenInfo> houseRegister(HouseCommand.ExchangedHouseRegister command) {

        return houseStore.houseRegister(command) // 내방 등록
            .map(house -> new HouseDTO.HouseTokenInfo(house.getHouseToken()));
    }

    /**
     * 내방 수정 처리
     * @param command: 수정할 내방 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> houseModify(HouseCommand.HouseModify command) {

        return houseReader.findHouseAggregateInfo(command.getHouseToken()) // 1. 내방 정보 조회
            .flatMap(houseAggregate ->
                houseStore.houseModify(houseAggregate, command) // 2. 내방 수정
            );
    }

    /**
     * 내방 삭제 처리
     * @param houseToken: 방 대체 식별키
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> houseDelete(String houseToken) {

        return houseReader.findHouseAggregateInfo(houseToken) // 1. 내방 정보 조회
            .flatMap(houseStore::houseDelete); // 2. 내방 삭제
    }

    /**
     * 내방 정보 조회 처리
     * @param houseToken: 방 대체 식별키
     * @return HouseInfo: 방 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<HouseDTO.HouseInfo> houseInfo(String houseToken) {

        return houseReader.findHouseAggregateInfo(houseToken) // 1. 내방 정보 조회
            .flatMap(houseAggregate ->
                houseAggregate.getRentalFlux()
                    .collectList()
                    .map(rentalList ->
                        houseDTOMapper.of(houseAggregate.getHouse(), rentalList) // 2. 내보낼 정보 취합
                    )
            );
    }

    /**
     * 내방 목록 조회 처리
     * @param memberId: 회원 고유번호
     * @return HouseList: 방 목록
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<HouseDTO.HouseList> houseList(int memberId) {
        return houseReader.findAllHouseAggregateByMemberId(memberId); // 내방 목록 조회
    }

    /**
     * 전체방 페이지 조회 처리
     * @param command: 조회할 정보
     * @return HousePage: 방 페이지
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<HouseDTO.HousePage> housePage(HouseCommand.HousePage command) {
        return houseReader.findAllHousePage(command); // 전체방 페이지 조회
    }
}
