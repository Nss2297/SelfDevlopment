# Gateway Project

This project is an API gateway for all the micro-services of provider communication portal (JISR).


## Adding new route:

To add new route it must be added in [`application.yaml`](/src/main/resources/application.yaml#L24)

it should be under `gateway.routes`:
```yaml
gateway:
  routes:
    # routes should be here
```

The route attributes are according to the class [`RouteProperties.Route`](/src/main/java/com/waseel/jisr/model/RoutesProperties.java#L29)

### Attribute (`routeName`):
- required
- should be unique per route
### Attribute (`path`):
- the path to be routed to a micro-service, for example:
    ```yaml
    gateway:
      routes:
        - route: /oauth/**
          #...
    ```
    this means that any request that starts with `/oauth/` will be used to route to a micro-service.
- not required if `pathRegex` is provided
### Attribute (`pathRegex`):
- a path in the form of a regex, for example:
    ```yaml
    gateway:
      routes:
        - route: \/payers\/.*\/contracts.*
          #...
    ```
- not required if `path` is provided
### Attributes (`rewritePathRegex`) & (`rewritePathReplacement`):
- used if the route must mapped to another route before transferring the request to a micro service, for example:
    ```yaml
    gateway:
      routes:
        - route: /oauth/**
          rewrite-path-regex: /oauth(?<segment>/?.*)
          rewrite-path-replacement: ${segment}
          #...
    ```
    this means the segment `/oauth` will be removed, and the micro service will not see.
- both are not required, but if one of them is provided the other must not be null.
### Attribute (`permitPaths`):
- a list of paths that should not be filtered by the security filter, therefore, requests without `Authorization` header can call those paths.
- not required
### Attribute (`uri`):
- required
- the micro service uri to be routed to, for example:
    ```yaml
    gateway:
      routes:
        - uri: http://localhost:8086
          #...
    ```
    note that it is better to have the value of all the uris in [`pom.xml`](/pom.xml) file as a `property` for each profile, since each profile will have a different uri, for example... in [`pom.xml`](/pom.xml):
    ```xml
    <profiles>
		<profile>
			<id>dev</id>
			<activation>
				<activeByDefault>true</activeByDefault>
				<property>
					<name>dev</name>
				</property>
			</activation>
			<properties>
				<oauthUrl>http://localhost:8086</oauthUrl>
			</properties>
		</profile>
    </profiles>
    ```
    and then in [`application.yaml`](/src/main/resources/application.yaml):
    ```yaml
    gateway:
      routes:
        - uri: "@oauthUrl@"
          #...
    ```
