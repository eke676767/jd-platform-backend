package com.jdcompany.platform.repository;

import com.jdcompany.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByProviderAndProviderId(String provider, String providerId);

	Optional<User> findByEmail(String email);
}
