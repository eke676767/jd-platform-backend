package com.jdcompany.platform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id", nullable = false)
	private Plan plan;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SubscriptionStatus status;

	@Column(nullable = false)
	private LocalDateTime startDate;

	protected Subscription() {
	}

	public Subscription(User user, Plan plan, SubscriptionStatus status, LocalDateTime startDate) {
		this.user = user;
		this.plan = plan;
		this.status = status;
		this.startDate = startDate;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Plan getPlan() {
		return plan;
	}

	public SubscriptionStatus getStatus() {
		return status;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}
}
