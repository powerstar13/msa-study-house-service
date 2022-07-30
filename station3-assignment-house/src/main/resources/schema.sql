CREATE TABLE IF NOT EXISTS house
(
    `houseId`       INT                          NOT NULL    AUTO_INCREMENT COMMENT '방 고유번호',
    `houseToken`    VARCHAR(36)                  NOT NULL    COMMENT '방 대체 식별키',
    `memberId`      INT                          NOT NULL    COMMENT '회원 고유번호',
    `houseAddress`  VARCHAR(255)                 NOT NULL    COMMENT '방 주소',
    `houseType`     VARCHAR(45)                  NOT NULL    COMMENT '방 유형',
    `createdAt`     TIMESTAMP                    NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`     TIMESTAMP                    NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (houseId)
);

CREATE TABLE IF NOT EXISTS rental
(
    `rentalId`    INT                          NOT NULL    AUTO_INCREMENT COMMENT '임대료 고유번호',
    `rentalToken` VARCHAR(36)                  NOT NULL    COMMENT '임대료 대체 식별키',
    `houseId`     INT                          NOT NULL    COMMENT '방 고유번호',
    `rentalType`  VARCHAR(45)                  NOT NULL    COMMENT '임대 유형',
    `deposit`     INT                          NOT NULL    COMMENT '보증금',
    `rent`        INT                          NULL        COMMENT '월세',
    `createdAt`   TIMESTAMP                    NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`   TIMESTAMP                    NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (rentalId),
    CONSTRAINT FK_rental_houseId_house_houseId FOREIGN KEY (houseId)
        REFERENCES house (houseId) ON DELETE RESTRICT ON UPDATE RESTRICT
);
