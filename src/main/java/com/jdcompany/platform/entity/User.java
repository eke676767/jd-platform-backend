package com.jdcompany.platform.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	private String name;

	private String profileImage;

	@Column(nullable = false)
	private String provider;

	@Column(nullable = false)
	private String providerId;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Builder
	public User(String email, String name, String profileImage, String provider, String providerId) {
		this.email = email;
		this.name = name;
		this.profileImage = profileImage;
		this.provider = provider;
		this.providerId = providerId;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
