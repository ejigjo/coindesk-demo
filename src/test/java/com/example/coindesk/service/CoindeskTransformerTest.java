package com.example.coindesk.service;

import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.dto.TransformedRateResponse;
import com.example.coindesk.dto.TransformedRateResponse.CurrencyRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CoindeskTransformerTest {

    private CoindeskTransformer transformer;
    private Map<String, String> names;

    @BeforeEach
    void setUp() {
        transformer = new CoindeskTransformer();
        names = new HashMap<>();
        names.put("USD", "美元");
        names.put("GBP", "英鎊");
        names.put("EUR", "歐元");
    }

    @Test
    @DisplayName("ISO 時間轉為 yyyy/MM/dd HH:mm:ss（台北時間）")
    void formatTime() {
        assertThat(transformer.formatTime("2024-09-02T07:07:20+00:00"))
                .isEqualTo("2024/09/02 15:07:20");
    }

    @Test
    @DisplayName("空時間回傳 null")
    void formatTimeNull() {
        assertThat(transformer.formatTime(null)).isNull();
        assertThat(transformer.formatTime("")).isNull();
    }

    @Test
    @DisplayName("組出幣別、中文名稱與匯率")
    void transform() {
        TransformedRateResponse result = transformer.transform(sample(), names);

        System.out.println("更新時間: " + result.getUpdateTime());
        result.getCurrencies().forEach(c ->
                System.out.println(c.getCode() + " / " + c.getChineseName() + " / " + c.getRate()));

        assertThat(result.getUpdateTime()).isEqualTo("2024/09/02 15:07:20");
        assertThat(result.getCurrencies()).hasSize(3);

        CurrencyRate usd = result.getCurrencies().get(0);
        assertThat(usd.getCode()).isEqualTo("USD");
        assertThat(usd.getChineseName()).isEqualTo("美元");
        assertThat(usd.getRate()).isEqualByComparingTo("57756.2984");

        assertThat(result.getCurrencies()).extracting(CurrencyRate::getChineseName)
                .containsExactly("美元", "英鎊", "歐元");
    }

    @Test
    @DisplayName("資料表沒有的幣別，中文名稱為 null 但不影響其他幣別")
    void transformMissingName() {
        names.remove("EUR");
        TransformedRateResponse result = transformer.transform(sample(), names);

        assertThat(result.getCurrencies()).hasSize(3);
        assertThat(result.getCurrencies().get(2).getCode()).isEqualTo("EUR");
        assertThat(result.getCurrencies().get(2).getChineseName()).isNull();
    }

    private CoindeskResponse sample() {
        CoindeskResponse res = new CoindeskResponse();
        CoindeskResponse.Time time = new CoindeskResponse.Time();
        time.setUpdatedISO("2024-09-02T07:07:20+00:00");
        res.setTime(time);

        Map<String, CoindeskResponse.Bpi> bpi = new LinkedHashMap<>();
        bpi.put("USD", bpi("USD", "57756.2984"));
        bpi.put("GBP", bpi("GBP", "43984.0203"));
        bpi.put("EUR", bpi("EUR", "52243.2865"));
        res.setBpi(bpi);
        return res;
    }

    private CoindeskResponse.Bpi bpi(String code, String rate) {
        CoindeskResponse.Bpi b = new CoindeskResponse.Bpi();
        b.setCode(code);
        b.setRateFloat(new BigDecimal(rate));
        return b;
    }
}
