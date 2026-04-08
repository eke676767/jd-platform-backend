package com.jdcompany.platform.repository;

import com.jdcompany.platform.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	Optional<Subscription> findByUserIdAndStatus(Long userId, String status);
}
