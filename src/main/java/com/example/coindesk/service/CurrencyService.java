package com.example.coindesk.service;

import com.example.coindesk.dto.CurrencyRequest;
import com.example.coindesk.entity.CurrencyMapping;
import com.example.coindesk.exception.DuplicateResourceException;
import com.example.coindesk.exception.ResourceNotFoundException;
import com.example.coindesk.repository.CurrencyMappingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final CurrencyMappingRepository repository;

    public CurrencyService(CurrencyMappingRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CurrencyMapping> findAll() {
        return repository.findAllByOrderByCodeAsc();
    }

    @Transactional(readOnly = true)
    public CurrencyMapping findByCode(String code) {
        return repository.findByCode(normalize(code))
                .orElseThrow(() -> new ResourceNotFoundException("找不到幣別：" + code));
    }

    @Transactional
    public CurrencyMapping create(CurrencyRequest request) {
        String code = normalize(request.getCode());
        if (repository.existsByCode(code)) {
            throw new DuplicateResourceException("幣別已存在：" + code);
        }
        return repository.save(new CurrencyMapping(code, request.getChineseName()));
    }

    @Transactional
    public CurrencyMapping update(String code, CurrencyRequest request) {
        CurrencyMapping entity = findByCode(code);
        entity.setChineseName(request.getChineseName());
        return repository.save(entity);
    }

    @Transactional
    public void delete(String code) {
        repository.delete(findByCode(code));
    }

    @Transactional(readOnly = true)
    public Map<String, String> getCodeToNameMap() {
        return repository.findAll().stream()
                .collect(Collectors.toMap(CurrencyMapping::getCode, CurrencyMapping::getChineseName));
    }

    private String normalize(String code) {
        return code == null ? null : code.trim().toUpperCase();
    }
}
