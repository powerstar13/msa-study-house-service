package station3.assignment.house.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Component
@RequiredArgsConstructor
public class HouseCustomRepositoryImpl implements HouseCustomRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Flux<House> housePage(HouseCommand.HousePage command) {

        return template.select(House.class)
            .matching(
                query(
                    where("houseType").is(command.getHouseType())
                ).offset(command.getPageForPageable()).limit(command.getSize())
            )
            .all();
    }
}
