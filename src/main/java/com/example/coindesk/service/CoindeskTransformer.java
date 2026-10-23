package com.example.coindesk.service;

import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.dto.TransformedRateResponse;
import com.example.coindesk.dto.TransformedRateResponse.CurrencyRate;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class CoindeskTransformer {

    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static final ZoneId OUTPUT_ZONE = ZoneId.of("Asia/Taipei");

    public TransformedRateResponse transform(CoindeskResponse source, Map<String, String> codeToName) {
        String updateTime = formatTime(source.getTime() == null ? null : source.getTime().getUpdatedISO());

        Map<String, CoindeskResponse.Bpi> bpi =
                source.getBpi() == null ? Collections.<String, CoindeskResponse.Bpi>emptyMap() : source.getBpi();

        List<CurrencyRate> rates = bpi.entrySet().stream()
                .map(e -> {
                    String code = e.getValue().getCode() != null ? e.getValue().getCode() : e.getKey();
                    String name = codeToName.get(code);
                    return new CurrencyRate(code, name, e.getValue().getRateFloat());
                })
                .collect(Collectors.toList());

        return new TransformedRateResponse(updateTime, rates);
    }

    public String formatTime(String isoTime) {
        if (isoTime == null || isoTime.trim().isEmpty()) {
            return null;
        }
        return OffsetDateTime.parse(isoTime)
                .atZoneSameInstant(OUTPUT_ZONE)
                .format(OUTPUT_FORMAT);
    }
}
