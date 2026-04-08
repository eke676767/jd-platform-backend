package com.jdcompany.platform.controller;

import com.jdcompany.platform.entity.Subscription;
import com.jdcompany.platform.repository.SubscriptionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

	private final SubscriptionRepository subscriptionRepository;

	public SubscriptionController(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
	}

	@GetMapping
	public Map<String, Object> getSubscription(Authentication authentication) {
		Long userId = (Long) authentication.getPrincipal();

		return subscriptionRepository.findByUserIdAndStatus(userId, "active")
				.map(sub -> Map.<String, Object>of(
						"plan", sub.getPlan(),
						"status", sub.getStatus(),
						"startedAt", sub.getStartedAt().toString()
				))
				.orElse(Map.of(
						"plan", "free",
						"status", "none"
				));
	}
}
