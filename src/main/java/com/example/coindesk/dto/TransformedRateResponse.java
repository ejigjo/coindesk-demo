package com.example.coindesk.dto;

import java.math.BigDecimal;
import java.util.List;

public class TransformedRateResponse {

    private String updateTime;
    private List<CurrencyRate> currencies;

    public TransformedRateResponse() {
    }

    public TransformedRateResponse(String updateTime, List<CurrencyRate> currencies) {
        this.updateTime = updateTime;
        this.currencies = currencies;
    }

    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }
    public List<CurrencyRate> getCurrencies() { return currencies; }
    public void setCurrencies(List<CurrencyRate> currencies) { this.currencies = currencies; }

    public static class CurrencyRate {
        private String code;
        private String chineseName;
        private BigDecimal rate;

        public CurrencyRate() {
        }

        public CurrencyRate(String code, String chineseName, BigDecimal rate) {
            this.code = code;
            this.chineseName = chineseName;
            this.rate = rate;
        }

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getChineseName() { return chineseName; }
        public void setChineseName(String chineseName) { this.chineseName = chineseName; }
        public BigDecimal getRate() { return rate; }
        public void setRate(BigDecimal rate) { this.rate = rate; }
    }
}
