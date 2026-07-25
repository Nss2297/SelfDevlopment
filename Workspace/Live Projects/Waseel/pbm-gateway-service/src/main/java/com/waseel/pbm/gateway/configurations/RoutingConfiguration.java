package com.waseel.pbm.gateway.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.waseel.pbm.gateway.model.RoutesProperties;

@Configuration
public class RoutingConfiguration {

	@Autowired
	private RoutesProperties routesProperties;

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		var routes = builder.routes();
		routesProperties.getRoutes().forEach(route -> {
			routes.route(route.getRouteName(), r -> {
				BooleanSpec spec;
				if (route.getPath() != null && !route.getPath().isBlank()) {
					spec = r.path(route.getPath());
				} else if (route.getPathRegex() != null && !route.getPathRegex().isBlank()) {
					spec = r.predicate(
							exchange -> exchange.getRequest().getPath().value().matches(route.getPathRegex()));
				} else {
					throw new IllegalStateException("Path or PathRegex must be provided for " + route.getRouteName());
				}

				if (route.getRewritePathRegex() != null && !route.getRewritePathRegex().isBlank()
						&& route.getRewritePathReplacement() != null && !route.getRewritePathReplacement().isBlank()) {
					spec.filters(f -> f.rewritePath(route.getRewritePathRegex(), route.getRewritePathReplacement()));
				}
				return r.uri(route.getUri());
			});
		});
		return routes.build();
	}

}
