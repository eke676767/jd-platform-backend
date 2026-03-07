package com.jdcompany.platform.repository;

import com.jdcompany.platform.domain.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
