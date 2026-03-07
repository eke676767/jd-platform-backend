package com.jdcompany.platform.service;

import com.jdcompany.platform.domain.Plan;
import com.jdcompany.platform.domain.Subscription;
import com.jdcompany.platform.domain.SubscriptionStatus;
import com.jdcompany.platform.domain.User;
import com.jdcompany.platform.repository.PlanRepository;
import com.jdcompany.platform.repository.SubscriptionRepository;
import com.jdcompany.platform.repository.UserRepository;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

	private static final String FREE_PLAN_NAME = "FREE";
	private static final String KAKAO = "kakao";

	private final UserRepository userRepository;
	private final PlanRepository planRepository;
	private final SubscriptionRepository subscriptionRepository;

	public OAuth2UserService(UserRepository userRepository,
			PlanRepository planRepository,
			SubscriptionRepository subscriptionRepository) {
		this.userRepository = userRepository;
		this.planRepository = planRepository;
		this.subscriptionRepository = subscriptionRepository;
	}

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		if (KAKAO.equals(registrationId)) {
			processKakaoUser(oAuth2User);
		}

		return oAuth2User;
	}

	private void processKakaoUser(OAuth2User oAuth2User) {
		String providerId = String.valueOf(oAuth2User.getAttribute("id"));
		String email = extractKakaoEmail(oAuth2User, providerId);

		Optional<User> existingUser = userRepository.findByProviderAndProviderId(KAKAO, providerId);
		if (existingUser.isEmpty()) {
			createKakaoUser(email, providerId);
		}
	}

	private String extractKakaoEmail(OAuth2User oAuth2User, String providerId) {
		Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
		if (kakaoAccount != null && kakaoAccount.get("email") != null) {
			return (String) kakaoAccount.get("email");
		}
		return "kakao_" + providerId + "@oauth.local";
	}

	private void createKakaoUser(String email, String providerId) {
		Plan freePlan = planRepository.findByName(FREE_PLAN_NAME)
				.orElseThrow(() -> new IllegalStateException("FREE 플랜이 존재하지 않습니다."));

		User user = new User(email, KAKAO, providerId);
		User savedUser = userRepository.save(user);

		Subscription subscription = new Subscription(
				savedUser,
				freePlan,
				SubscriptionStatus.ACTIVE,
				LocalDateTime.now());
		subscriptionRepository.save(subscription);
	}
}
