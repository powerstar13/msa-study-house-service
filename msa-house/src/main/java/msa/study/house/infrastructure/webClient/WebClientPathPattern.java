package msa.study.house.infrastructure.webClient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebClientPathPattern {
    // Member Domain
    EXCHANGE_MEMBER_TOKEN("/exchange/member-token/{memberToken}");

    private final String fullPath;
}
