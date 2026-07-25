package com.waseel.pbm.gateway.model;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("gateway")
public class RoutesProperties {

	private List<Route> routes;

	public RoutesProperties(List<Route> routes) {
		super();
		this.routes = routes;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public static class Route {

		private String routeName;
		private String path;
		private String pathRegex;
		private String rewritePathRegex;
		private String rewritePathReplacement;
		private List<String> permitPaths;
		private String uri;

		public Route() {
			super();
		}

		public String getRouteName() {
			return routeName;
		}

		public void setRouteName(String routeName) {
			this.routeName = routeName;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public String getRewritePathRegex() {
			return rewritePathRegex;
		}

		public void setRewritePathRegex(String rewritePathRegex) {
			this.rewritePathRegex = rewritePathRegex;
		}

		public String getRewritePathReplacement() {
			return rewritePathReplacement;
		}

		public void setRewritePathReplacement(String rewritePathReplacement) {
			this.rewritePathReplacement = rewritePathReplacement;
		}

		public List<String> getPermitPaths() {
			return permitPaths;
		}

		public void setPermitPaths(List<String> permitPaths) {
			this.permitPaths = permitPaths;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getPathRegex() {
			return pathRegex;
		}

		public void setPathRegex(String pathRegex) {
			this.pathRegex = pathRegex;
		}
	}

}
