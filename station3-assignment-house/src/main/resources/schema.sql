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

INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff81', 1, '방 주소1', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff82', 1, '방 주소2', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff83', 1, '방 주소3', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff84', 1, '방 주소4', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff85', 1, '방 주소5', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff86', 1, '방 주소6', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff87', 1, '수정할 방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES ('4ff3f414-7a5e-4ce1-8234-76788b5eff88', 1, '삭제할 방 주소', 'THREE');

INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES ('8143d59c-2e61-4157-8380-9da06713c841', 1, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES ('8143d59c-2e61-4157-8380-9da06713c842', 2, 'JEONSE', 4000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES ('8143d59c-2e61-4157-8380-9da06713c843', 3, 'JEONSE', 6000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES ('8143d59c-2e61-4157-8380-9da06713c844', 4, 'JEONSE', 10000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES ('8143d59c-2e61-4157-8380-9da06713c845', 5, 'JEONSE', 14000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES ('8143d59c-2e61-4157-8380-9da06713c846', 6, 'JEONSE', 20000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES ('8143d59c-2e61-4157-8380-9da06713c847', 7, 'JEONSE', 21000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES ('8143d59c-2e61-4157-8380-9da06713c848', 8, 'JEONSE', 22000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES ('8143d59c-2e61-4157-8380-9da06713c849', 1, 'MONTHLY', 500, 35);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES ('8143d59c-2e61-4157-8380-9da06713c810', 2, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES ('8143d59c-2e61-4157-8380-9da06713c811', 3, 'MONTHLY', 1500, 55);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES ('8143d59c-2e61-4157-8380-9da06713c812', 4, 'MONTHLY', 2000, 60);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES ('8143d59c-2e61-4157-8380-9da06713c813', 5, 'MONTHLY', 2500, 75);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES ('8143d59c-2e61-4157-8380-9da06713c814', 6, 'MONTHLY', 3000, 80);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES ('8143d59c-2e61-4157-8380-9da06713c815', 7, 'MONTHLY', 3100, 70);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES ('8143d59c-2e61-4157-8380-9da06713c816', 8, 'MONTHLY', 3200, 65);
