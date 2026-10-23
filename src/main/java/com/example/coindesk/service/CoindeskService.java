package com.example.coindesk.service;

import com.example.coindesk.client.CoindeskClient;
import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.dto.TransformedRateResponse;
import org.springframework.stereotype.Service;

@Service
public class CoindeskService {

    private final CoindeskClient client;
    private final CoindeskTransformer transformer;
    private final CurrencyService currencyService;

    public CoindeskService(CoindeskClient client, CoindeskTransformer transformer, CurrencyService currencyService) {
        this.client = client;
        this.transformer = transformer;
        this.currencyService = currencyService;
    }

    public CoindeskResponse getOriginal() {
        return client.fetch();
    }

    public TransformedRateResponse getTransformed() {
        return transformer.transform(client.fetch(), currencyService.getCodeToNameMap());
    }
}
