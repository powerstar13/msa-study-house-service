package station3.assignment.house.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.Rental;
import station3.assignment.house.domain.service.HouseStore;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.house.infrastructure.exception.status.RegisterFailException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
                return rentalRepository.saveAll(rentalList)
                    .switchIfEmpty(Mono.error(new RegisterFailException(ExceptionMessage.RegisterFailRental.getMessage())))
                    .then(Mono.just(house));
            });
    }

    /**
     * 방 정보 수정
     * @param houseAggregate: 방 애그리거트
     * @param command: 수정할 방 정보
     */
    @Override
    public Mono<Void> houseModify(HouseDTO.HouseAggregate houseAggregate, HouseCommand.HouseModify command) {

        House house = houseAggregate.getHouse();
        house.modify(command.getHouseAddress(), command.getHouseType()); // 수정할 방 정보 반영

        return houseRepository.save(house) // 내방 정보 수정
            .flatMap(updatedHouse -> {

                List<HouseCommand.RentalModifyDTO> rentalModifyDTOList = command.getRentalModifyDTOList();

                return houseAggregate.getRentalFlux()
                    .flatMap(rental -> {

                        if (rentalModifyDTOList == null) {
                            return rentalRepository.delete(rental); // 전달된 임대료 정보가 없을 경우 기존 임대료 정보는 삭제 처리
                        } else {
                            HouseCommand.RentalModifyDTO originRental = rentalModifyDTOList.stream()
                                .filter(rentalModifyDTO ->
                                    rental.getRentalToken().equals(rentalModifyDTO.getRentalToken())
                                )
                                .findFirst()
                                .orElse(null);

                            if (originRental != null) {
                                rental.modify(originRental.getRentalType(), originRental.getDeposit(), originRental.getRent()); // 수정할 임대료 정보 반영
                                rentalModifyDTOList.remove(originRental); // 처리된 임대료 정보는 추가할 목록에서 제외 처리
                                return rentalRepository.save(rental);
                            } else {
                                return rentalRepository.delete(rental); // 목록에서 매칭되지 않은 기존 임대료 정보는 삭제 처리
                            }
                        }
                    })
                    .flatMap(o -> {
                        // 추가할 임대료 정보 취합
                        List<Rental> rentalList = rentalModifyDTOList.stream()
                            .map(rentalModifyDTO -> rentalModifyDTO.toEntity(house.getHouseId()))
                            .collect(Collectors.toList());

                        return rentalRepository.saveAll(rentalList); // 임대료 정보 추가
                    })
                    .then();
            });
    }
}
