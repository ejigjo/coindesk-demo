-- 幣別與中文名稱對應表
DROP TABLE IF EXISTS currency_mapping;

CREATE TABLE currency_mapping (
    id            BIGINT       AUTO_INCREMENT PRIMARY KEY,
    code          VARCHAR(10)  NOT NULL,
    chinese_name  VARCHAR(50)  NOT NULL,
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_currency_code UNIQUE (code)
);
