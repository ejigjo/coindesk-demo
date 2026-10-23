# coindesk-demo

Spring Boot 專案：呼叫 coindesk API 並進行資料轉換，以及幣別中文名稱對應表的 CRUD。

## 技術
- JDK 8、Maven
- Spring Boot 2.7.18（Web、Data JPA、Validation）
- H2 in-memory 資料庫

## API

| 功能 | Method | Path |
|---|---|---|
| 查詢全部幣別 | GET | `/api/currencies` |
| 查詢單一幣別 | GET | `/api/currencies/{code}` |
| 新增幣別 | POST | `/api/currencies` |
| 修改幣別中文名稱 | PUT | `/api/currencies/{code}` |
| 刪除幣別 | DELETE | `/api/currencies/{code}` |
| coindesk 原始資料 | GET | `/api/coindesk` |
| 轉換後的新 API | GET | `/api/coindesk/transformed` |

新增 / 修改的 request body：
```json
{ "code": "JPY", "chineseName": "日圓" }
```

轉換後 API 回傳範例：
```json
{
  "updateTime": "2024/09/02 15:07:20",
  "currencies": [
    { "code": "USD", "chineseName": "美元", "rate": 57756.2984 },
    { "code": "GBP", "chineseName": "英鎊", "rate": 43984.0203 },
    { "code": "EUR", "chineseName": "歐元", "rate": 52243.2865 }
  ]
}
```

## 測試

| 測試類別 | 內容 |
|---|---|
| `CoindeskTransformerTest` | 資料轉換邏輯單元測試 |
| `CurrencyControllerTest` | 幣別 CRUD API 測試，並印出 request / response |
| `CoindeskControllerTest` | 呼叫 coindesk API 與轉換 API |
