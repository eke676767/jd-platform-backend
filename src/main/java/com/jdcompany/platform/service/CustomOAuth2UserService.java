package com.jdcompany.platform.service;

import com.jdcompany.platform.entity.User;
import com.jdcompany.platform.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends OidcUserService {

	private final UserRepository userRepository;

	public CustomOAuth2UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId();
		String providerId = oidcUser.getSubject();
		String email = oidcUser.getEmail();
		String name = oidcUser.getFullName();
		String picture = oidcUser.getPicture();

		User user = userRepository.findByProviderAndProviderId(provider, providerId)
				.orElseGet(() -> userRepository.save(
						User.builder()
								.email(email)
								.name(name)
								.profileImage(picture)
								.provider(provider)
								.providerId(providerId)
								.build()
				));

		Map<String, Object> attributes = new HashMap<>(oidcUser.getAttributes());
		attributes.put("userId", user.getId());

		return new DefaultOidcUser(
				Collections.emptyList(),
				oidcUser.getIdToken(),
				oidcUser.getUserInfo(),
				"sub"
		);
	}
}
