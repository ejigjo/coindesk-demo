package com.example.coindesk.repository;

import com.example.coindesk.entity.CurrencyMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyMappingRepository extends JpaRepository<CurrencyMapping, Long> {

    Optional<CurrencyMapping> findByCode(String code);

    boolean existsByCode(String code);

    List<CurrencyMapping> findAllByOrderByCodeAsc();
}
