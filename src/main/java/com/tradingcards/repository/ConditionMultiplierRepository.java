package com.tradingcards.repository;

import com.tradingcards.model.ConditionMultiplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConditionMultiplierRepository extends JpaRepository<ConditionMultiplier, String> {
    Optional<ConditionMultiplier> findByConditionName(String conditionName);
}