DROP TABLE IF EXISTS member;

CREATE TABLE IF NOT EXISTS member
(
    `memberId`        INT                         NOT NULL    AUTO_INCREMENT COMMENT '회원 고유번호',
    `memberToken`     VARCHAR(36)                 NOT NULL    COMMENT '회원 대체 식별키',
    `memberType`      VARCHAR(45)                 NOT NULL    COMMENT '회원 유형',
    `memberLoginId`   VARCHAR(45)                 NOT NULL    COMMENT '회원 로그인 아이디',
    `memberPassword`  VARCHAR(255)                NOT NULL    COMMENT '회원 비밀번호',
    `memberName`      VARCHAR(45)                 NOT NULL    COMMENT '회원 이름',
    `memberBirth`     DATE                        NOT NULL    COMMENT '회원 생년월일',
    `memberPhone`     VARCHAR(45)                 NOT NULL    COMMENT '회원 연락처',
    `createdAt`       TIMESTAMP                   NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`       TIMESTAMP                   NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (memberId)
);

DROP TABLE IF EXISTS house;

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

ALTER TABLE house
    ADD CONSTRAINT FK_house_memberId_member_memberId FOREIGN KEY (memberId)
        REFERENCES member (memberId) ON DELETE RESTRICT ON UPDATE RESTRICT;

DROP TABLE IF EXISTS rental;

CREATE TABLE IF NOT EXISTS rental
(
    `rentalId`    INT                          NOT NULL    AUTO_INCREMENT COMMENT '임대료 고유번호',
    `houseId`     INT                          NOT NULL    COMMENT '방 고유번호',
    `rentalType`  VARCHAR(45)                  NOT NULL    COMMENT '임대 유형',
    `deposit`     INT                          NOT NULL    COMMENT '보증금',
    `rent`        INT                          NULL        COMMENT '월세',
    `createdAt`   TIMESTAMP                    NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`   TIMESTAMP                    NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (rentalId)
);

ALTER TABLE rental
    ADD CONSTRAINT FK_rental_houseId_house_houseId FOREIGN KEY (houseId)
        REFERENCES house (houseId) ON DELETE RESTRICT ON UPDATE RESTRICT;

INSERT INTO member (memberToken, memberType, memberLoginId, memberPassword, memberName, memberBirth, memberPhone)
VALUES (UUID(), 'LESSOR', 'lessor@gmail.com', '$2a$10$oKmFs6UNvZhEoKhJYI5rxOmNU6/c8oUpkVYry.PBKcY1ZuHMToppa', '임대인이름', '19910101', '01012341234');
INSERT INTO member (memberToken, memberType, memberLoginId, memberPassword, memberName, memberBirth, memberPhone)
VALUES (UUID(), 'LESSEE', 'lessee@gmail.com', '$2a$10$oKmFs6UNvZhEoKhJYI5rxOmNU6/c8oUpkVYry.PBKcY1ZuHMToppa', '임차인이름', '19880101', '01056785678');