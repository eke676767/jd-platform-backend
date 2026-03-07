package com.jdcompany.platform.service;

import com.jdcompany.platform.domain.Plan;
import com.jdcompany.platform.domain.Subscription;
import com.jdcompany.platform.domain.SubscriptionStatus;
import com.jdcompany.platform.domain.User;
import com.jdcompany.platform.exception.DuplicateEmailException;
import com.jdcompany.platform.exception.PlanNotFoundException;
import com.jdcompany.platform.repository.PlanRepository;
import com.jdcompany.platform.repository.SubscriptionRepository;
import com.jdcompany.platform.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

	private static final String FREE_PLAN_NAME = "FREE";

	private final UserRepository userRepository;
	private final PlanRepository planRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository,
			PlanRepository planRepository,
			SubscriptionRepository subscriptionRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.planRepository = planRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Long signUp(String email, String rawPassword) {
		if (userRepository.existsByEmail(email)) {
			throw new DuplicateEmailException("이미 사용 중인 이메일입니다: " + email);
		}

		Plan freePlan = planRepository.findByName(FREE_PLAN_NAME)
				.orElseThrow(() -> new PlanNotFoundException(
						"FREE 플랜이 데이터베이스에 존재하지 않습니다. 먼저 FREE 플랜을 등록해 주세요."));

		String encodedPassword = passwordEncoder.encode(rawPassword);
		User user = new User(email, encodedPassword);
		User savedUser = userRepository.save(user);

		Subscription subscription = new Subscription(
				savedUser,
				freePlan,
				SubscriptionStatus.ACTIVE,
				LocalDateTime.now());
		subscriptionRepository.save(subscription);

		return savedUser.getId();
	}
}
