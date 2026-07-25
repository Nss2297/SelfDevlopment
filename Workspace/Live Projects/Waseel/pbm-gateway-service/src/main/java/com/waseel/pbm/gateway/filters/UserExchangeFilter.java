package com.waseel.pbm.gateway.filters;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

@Component
public class UserExchangeFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		return ReactiveSecurityContextHolder.getContext().map(sc -> sc.getAuthentication()).flatMap(authentication -> {
			if (authentication != null && authentication instanceof BearerTokenAuthentication) {
				Map<String, Object> tokenAttributes = ((BearerTokenAuthentication) authentication).getTokenAttributes();

				String authoritiesString = null;

				if (tokenAttributes.get("authorities") != null
						&& tokenAttributes.get("authorities") instanceof JSONArray) {
					JSONArray authorities = (JSONArray) tokenAttributes.get("authorities");
					authoritiesString = authorities.stream()
							.map(authority -> ((JSONObject) authority).get("authority").toString())
							.reduce((authority1, authority2) -> authority1.concat(",").concat(authority2)).orElse(null);
				}

				String accName = (String) tokenAttributes.get("accName");

				if (accName != null) {
					accName = encodeValue(accName);
				}
				ServerHttpRequest request = exchange.getRequest().mutate()
						.header("accId", (String) tokenAttributes.get("accId") + "")
						.header("patientId", (String) tokenAttributes.get("patientId"))
						.header("accName", accName)
						.header("accCode", (String) tokenAttributes.get("accCode") + "")
						.header("accCategory", (String) tokenAttributes.get("accCategory") + "")
						.header("username", (String) tokenAttributes.get("username") + "")
						.header("email", (String) tokenAttributes.get("email") + "")
						.header("authorities", authoritiesString)
						.build();

				return chain.filter(exchange.mutate().request(request).build());
			}
			return chain.filter(exchange);
		}).switchIfEmpty(chain.filter(exchange));
	}

	private String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (Exception e) {
			return value;
		}
	}

}
