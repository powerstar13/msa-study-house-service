package station3.assignment.house.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.Rental;
import station3.assignment.house.domain.service.HouseStore;
import station3.assignment.house.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.house.infrastructure.exception.status.RegisterFailException;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HouseStoreImpl implements HouseStore {

    private final HouseRepository houseRepository;
    private final RentalRepository rentalRepository;

    /**
     * 내방 등록 및 임대료 등록
     * @param command: 등록할 내방 정보
     * @return House: 내방 레퍼런스
     */
    @Override
    public Mono<House> houseRegister(HouseCommand.ExchangedHouseRegister command) {

        return houseRepository.save(command.toEntity()) // 내방 등록
            .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailHouse.getMessage())))
            .flatMap(house -> {

                // 등록할 임대료 목록 취합
                List<Rental> rentalList = command.getRentalRegisterDTOList()
                        .stream().map(rentalRegisterDTO ->
                            rentalRegisterDTO.toEntity(house.getHouseId())
                        )
                        .collect(Collectors.toList());

                // 임대료 목록 등록
                rentalRepository.saveAll(rentalList)
                    .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailRental.getMessage())))
                    .subscribeOn(Schedulers.boundedElastic()).subscribe();

                return Mono.just(house);
            });
    }
}
