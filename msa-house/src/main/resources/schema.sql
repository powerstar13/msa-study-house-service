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

INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '원룸방 주소', 'ONE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '투룸방 주소', 'TWO');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');
INSERT INTO house (houseToken, memberId, houseAddress, houseType)
VALUES (UUID(), 2, '쓰리룸방 주소', 'THREE');

INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 9, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 9, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 10, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 10, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 11, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 11, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 12, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 12, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 13, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 13, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 14, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 14, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 15, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 15, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 16, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 16, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 17, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 17, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 18, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 18, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 19, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 19, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 20, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 20, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 21, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 21, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 22, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 22, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 23, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 23, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 24, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 24, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 25, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 25, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 26, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 26, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 27, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 27, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 28, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 28, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 29, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 29, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 30, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 30, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 31, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 31, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 32, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 32, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 33, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 33, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 34, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 34, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 35, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 35, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 36, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 36, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 37, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 37, 'MONTHLY', 1000, 50);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit)
VALUES (UUID(), 38, 'JEONSE', 5000);
INSERT INTO rental (rentalToken, houseId, rentalType, deposit, rent)
VALUES (UUID(), 38, 'MONTHLY', 1000, 50);
