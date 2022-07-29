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

INSERT INTO member (memberToken, memberType, memberLoginId, memberPassword, memberName, memberBirth, memberPhone)
VALUES (UUID(), 'LESSOR', 'lessor@gmail.com', '$2a$10$oKmFs6UNvZhEoKhJYI5rxOmNU6/c8oUpkVYry.PBKcY1ZuHMToppa', '임대인이름', '19910101', '01012341234');
INSERT INTO member (memberToken, memberType, memberLoginId, memberPassword, memberName, memberBirth, memberPhone)
VALUES (UUID(), 'LESSEE', 'lessee@gmail.com', '$2a$10$oKmFs6UNvZhEoKhJYI5rxOmNU6/c8oUpkVYry.PBKcY1ZuHMToppa', '임차인이름', '19880101', '01056785678');
