package com.jdcompany.platform.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column
	private String password;

	@Column(length = 20)
	private String provider;

	@Column(name = "provider_id")
	private String providerId;

	@OneToMany(mappedBy = "user", fetch = jakarta.persistence.FetchType.LAZY)
	private List<Subscription> subscriptions = new ArrayList<>();

	protected User() {
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.provider = "LOCAL";
	}

	public User(String email, String provider, String providerId) {
		this.email = email;
		this.provider = provider;
		this.providerId = providerId;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public String getProvider() {
		return provider != null ? provider : "LOCAL";
	}

	public String getProviderId() {
		return providerId;
	}
}
