package com.waseel.pbm.dssservice.configuration;

//@Configuration
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig /* extends ResourceServerConfigurerAdapter */ {

//	private final ResourceServerProperties sso;
//
//	private final OAuth2ClientContext oAuth2ClientContext;
//
//	@Autowired
//	public SecurityConfig(ResourceServerProperties sso, OAuth2ClientContext oAuth2ClientContext) {
//		this.sso = sso;
//		this.oAuth2ClientContext = oAuth2ClientContext;
//	}
//
//	@Bean
//
//	@ConfigurationProperties(prefix = "security.oauth2.client")
//	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
//		return new ClientCredentialsResourceDetails();
//	}
//
//	@Bean
//	public RequestInterceptor oauth2FeignRequestInterceptor() {
//		return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, clientCredentialsResourceDetails());
//	}
//
//	@Bean
//	public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
//		return new OAuth2RestTemplate(clientCredentialsResourceDetails(), oauth2ClientContext);
//	}
//
//	@Bean
//
//	@Primary
//	public ResourceServerTokenServices resourceServerTokenServices() {
//		return new UserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
//	}
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.cors().and().authorizeRequests()
//				.antMatchers("/validate/override", "/validate/cancellation", "/validate/new", "/validate/followup",
//						"/actuator/**", "/error", "/v2/api-docs", "/swagger-resources/**", "/webjars/**")
//				.permitAll().anyRequest().authenticated();
//	}
//
//	@Bean
//	public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
//		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE"));
//		config.setAllowedHeaders(Collections.singletonList("*"));
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
//		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//		return bean;
//	}

}
