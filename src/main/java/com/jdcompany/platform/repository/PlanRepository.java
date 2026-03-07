package com.jdcompany.platform.repository;

import com.jdcompany.platform.domain.Plan;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {

	Optional<Plan> findByName(String name);
}
