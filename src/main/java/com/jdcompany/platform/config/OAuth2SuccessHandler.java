package com.jdcompany.platform.config;

import com.jdcompany.platform.entity.User;
import com.jdcompany.platform.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final String frontendUrl;

	public OAuth2SuccessHandler(
			JwtTokenProvider jwtTokenProvider,
			UserRepository userRepository,
			@Value("${app.frontend-url}") String frontendUrl) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userRepository = userRepository;
		this.frontendUrl = frontendUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

		String provider = "google";
		String providerId = oidcUser.getSubject();

		User user = userRepository.findByProviderAndProviderId(provider, providerId)
				.orElseThrow(() -> new RuntimeException("User not found after OAuth login"));

		String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());

		String redirectUrl = frontendUrl + "/auth/callback?token=" + token;
		getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
