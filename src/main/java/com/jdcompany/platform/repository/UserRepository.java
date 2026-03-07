package com.jdcompany.platform.repository;

import com.jdcompany.platform.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<User> findByProviderAndProviderId(String provider, String providerId);
}
