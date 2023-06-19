package com.comfihealth.security.authentication.configs;

import com.comfihealth.security.authentication.configs.keys.JWKManager;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfiguration {

    @Value("${comfihealth.oauth2.issuer.uri}")
    private String oauthIssuerURI;

    // ----
    // We set these values in the properties for now, since we only have one client to register and to make it quick.
    // This will change, and hence will adopt storing these values in a storage

    @Value("${comfihealth.client.redirect-uri}")
    private String clientRedirectURIs;

    @Value("${comfihealth.client.id}")
    private String clientId;

    @Value("${comfihealth.client.secret}")
    private String clientSecret;

    // ---

    private final JWKManager jwkManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                );

        return http.formLogin().and().build();
    }


    @Bean
    public AuthorizationServerSettings oauthProviderSettings() {
        return AuthorizationServerSettings.builder()
                .authorizationEndpoint("/v1/oauth2/authorize")
                .tokenEndpoint("/v1/oauth2/token")
                .issuer(oauthIssuerURI)
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        JWKSet jwkSet = new JWKSet(jwkManager.rsaKey());

        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        var clientEncodedSecret = bCryptPasswordEncoder.encode(clientSecret);

        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(clientEncodedSecret)
                .redirectUris(uris -> uris.add(clientRedirectURIs))
                .authorizationGrantTypes(grantTypes -> grantTypes.addAll(
                        List.of(AuthorizationGrantType.AUTHORIZATION_CODE,
                                AuthorizationGrantType.CLIENT_CREDENTIALS,
                                AuthorizationGrantType.REFRESH_TOKEN
                        )
                ))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .scopes(scopes -> scopes.addAll(
                        List.of(OidcScopes.OPENID,
                                OidcScopes.PROFILE
                        )
                ))
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(true).build()
                )
                .build();

        // TODO: Should save clients in the data store before going prod
        return new InMemoryRegisteredClientRepository(client);
    }
}
