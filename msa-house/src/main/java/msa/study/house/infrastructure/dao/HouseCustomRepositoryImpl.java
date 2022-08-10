package msa.study.house.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.House;
import msa.study.house.domain.HouseType;
import msa.study.house.domain.Rental;
import msa.study.house.domain.RentalType;

import java.util.List;

import static org.springframework.data.relational.core.query.Criteria.empty;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Component
@RequiredArgsConstructor
public class HouseCustomRepositoryImpl implements HouseCustomRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Mono<List<Integer>> getHouseIdListOfHousePage(HouseCommand.HousePage command) {

        return template.select(House.class)
            .matching(query(
                this.houseWhereOfHousePagePrepare(command.getHouseType()) // 방 유형 검색
            ))
            .all()
            .flatMap(rental -> Mono.just(rental.getHouseId())) // 방 고유번호만 추출
            .collectList()
            .flatMap(houseIdList ->
                this.getRentalListOfHousePage(command, houseIdList)
                    .flatMap(rental -> Mono.just(rental.getHouseId())) // 임대료 정보의 방 고유번호만 추출
                    .distinct() // 중복 제외
                    .collectList()
            );
    }
    
    @Override
    public Flux<Rental> getRentalListOfHousePage(HouseCommand.HousePage command, List<Integer> houseIdList) {
        return template.select(Rental.class)
            .matching(query(
                this.rentalWhereOfHousePagePrepare(houseIdList, command.getRentalType(), command.getDepositStartRange(), command.getDepositEndRange(), command.getRentStartRange(), command.getRentEndRange())
            ))
            .all();
    }
    
    /**
     * 전체방 페이지의 방 검색 조건
     * @param houseType: 방 유형
     */
    public CriteriaDefinition houseWhereOfHousePagePrepare(HouseType houseType) {
        return houseType != null ? where("houseType").is(houseType) : empty();
    }

    /**
     * 전체방 페이지의 임대료 검색 조건
     * @param houseIdList: 방 고유번호 목록
     * @param rentalType: 임대 유형
     * @param depositStartRange: 보증금 시작 범위
     * @param depositEndRange: 보증금 끝 범위
     * @param rentStartRange: 월세 시작 범위
     * @param rentEndRange: 월세 끝 범위
     */
    public CriteriaDefinition rentalWhereOfHousePagePrepare(List<Integer> houseIdList, RentalType rentalType, Integer depositStartRange, Integer depositEndRange, Integer rentStartRange, Integer rentEndRange) {

        Criteria criteria = empty();

        if (!houseIdList.isEmpty()) criteria = houseIdList.size() == 1 ? criteria.and("houseId").is(houseIdList.get(0)) : criteria.and("houseId").in(houseIdList); // 방 고유번호 목록 검색

        if (rentalType != null) criteria = criteria.and("rentalType").is(rentalType); // 임대 유형 검색

        if (depositStartRange != null && depositEndRange != null) {
            criteria = criteria.and("deposit").between(depositStartRange, depositEndRange); // 보증금 범위 검색
        } else if (depositStartRange != null) {
            criteria = criteria.and("deposit").greaterThanOrEquals(depositStartRange); // 보증금 시작 범위 검색
        } else if (depositEndRange != null) {
            criteria = criteria.and("deposit").lessThanOrEquals(depositEndRange); // 보증금 끝 범위 검색
        }

        if (rentStartRange != null && rentEndRange != null) {
            criteria = criteria.and("rent").between(rentStartRange, rentEndRange); // 월세 범위 검색
        } else if (rentStartRange != null) {
            criteria = criteria.and("rent").greaterThanOrEquals(rentStartRange); // 월세 시작 범위 검색
        } else if (rentEndRange != null) {
            criteria = criteria.and("rent").lessThanOrEquals(rentEndRange); // 월세 끝 범위 검색
        }

        return criteria;
    }
}
