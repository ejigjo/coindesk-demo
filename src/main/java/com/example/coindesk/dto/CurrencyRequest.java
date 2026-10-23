package com.example.coindesk.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** 新增 / 修改幣別用的請求內容 */
public class CurrencyRequest {

    @NotBlank(message = "code 不可為空")
    @Size(max = 10, message = "code 長度不可超過 10")
    private String code;

    @NotBlank(message = "chineseName 不可為空")
    @Size(max = 50, message = "chineseName 長度不可超過 50")
    private String chineseName;

    public CurrencyRequest() {
    }

    public CurrencyRequest(String code, String chineseName) {
        this.code = code;
        this.chineseName = chineseName;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getChineseName() { return chineseName; }
    public void setChineseName(String chineseName) { this.chineseName = chineseName; }
}
