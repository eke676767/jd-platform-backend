package com.jdcompany.platform.config;

import com.jdcompany.platform.domain.Plan;
import com.jdcompany.platform.repository.PlanRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PlanSeeder implements ApplicationRunner {

	private static final String FREE_PLAN_NAME = "FREE";
	private static final Logger log = LoggerFactory.getLogger(PlanSeeder.class);

	private final PlanRepository planRepository;

	public PlanSeeder(PlanRepository planRepository) {
		this.planRepository = planRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (planRepository.findByName(FREE_PLAN_NAME).isEmpty()) {
			planRepository.save(new Plan(FREE_PLAN_NAME));
			log.info("FREE 플랜이 데이터베이스에 등록되었습니다.");
		}
	}
}
