package msa.study.house.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.House;
import msa.study.house.domain.Rental;
import msa.study.house.domain.service.HouseReader;
import msa.study.house.domain.service.dto.HouseDTO;
import msa.study.house.domain.service.dto.HouseDTOMapper;
import msa.study.house.infrastructure.exception.status.ExceptionMessage;
import msa.study.house.infrastructure.exception.status.NotFoundDataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HouseReaderImpl implements HouseReader {

    private final HouseRepository houseRepository;
    private final RentalRepository rentalRepository;
    private final HouseDTOMapper houseDTOMapper;

    /**
     * 방 정보 조회
     * @param houseToken: 방 대체 식별키
     * @return HouseAggregate: 방 애그리거트 정보
     */
    @Override
    public Mono<HouseDTO.HouseAggregate> findHouseAggregateInfo(String houseToken) {

        Mono<House> houseMono = houseRepository.findByHouseToken(houseToken) // 방 정보 조회
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundHouse.getMessage())));

        return houseMono.map(house -> {
            Flux<Rental> rentalFlux = rentalRepository.findAllByHouseId(house.getHouseId()); // 임대료 목록 조회

            return houseDTOMapper.of(house, rentalFlux);
        });
    }

    /**
     * 내방 목록 조회
     * @param memberId: 회원 고유번호
     * @return HouseList: 방 목록
     */
    @Override
    public Mono<HouseDTO.HouseList> findAllHouseAggregateByMemberId(int memberId) {

        Flux<House> houseFlux = houseRepository.findAllByMemberId(memberId); // 방 목록 조회

        return houseFlux
            .flatMap(house ->
                rentalRepository.findAllByHouseId(house.getHouseId()) // 임대료 목록 조회
                    .collectList()
                    .map(rentalList -> houseDTOMapper.of(house, rentalList))
            )
            .collectList()
            .map(HouseDTO.HouseList::new);
    }

    /**
     * 전체방 페이지 조회
     * @param command: 조회할 정보
     * @return HousePage: 방 페이지
     */
    @Override
    public Mono<HouseDTO.HousePage> findAllHousePage(HouseCommand.HousePage command) {

        PageRequest pageRequest = PageRequest.of(command.getPageForPageable(), command.getSize());

        return houseRepository.getHouseIdListOfHousePage(command)
            .flatMap(houseIdList ->
                houseRepository.findAllByHouseIdIn(houseIdList, pageRequest)
                    .flatMap(house ->
                        rentalRepository.getRentalListOfHousePage(command, List.of(house.getHouseId())) // 임대료 목록 조회
                            .collectList()
                            .map(rentalList -> houseDTOMapper.of(house, rentalList))
                    )
                    .collectList()
                    .map(houseInfoList -> {
                        Page<HouseDTO.HouseInfo> housePage = new PageImpl<>(houseInfoList, pageRequest, houseIdList.size());

                        HouseDTO.pageInfo pageInfo = HouseDTO.pageInfo.builder() // 페이지 정보 구성
                            .currentSize(housePage.getNumberOfElements())
                            .currentPage(command.getPage())
                            .totalCount(housePage.getTotalElements())
                            .totalPage(housePage.getTotalPages())
                            .build();

                        return houseDTOMapper.of(pageInfo, housePage.getContent());
                    })
            );
    }
}
