package com.example.backoffice.backofficeapi;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
//@Import(SpringDataRestConfiguration.class)
//@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Value("${keycloak.auth-server-url}")
    private String accessTokenUri;


    public static final String securitySchemaOAuth2 = "oauth2schema";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc ="accessEverything";

    @Bean
    public Docket api() {
//        ParameterBuilder aParameterBuilder = new ParameterBuilder();
//        aParameterBuilder.name("Authorization")                 // name of header
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")               // type - header
//                .defaultValue("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJFQ0RTQVNIQTUxMiJ9.eyJsYXN0X25hbWVfa2giOiLhnp/hnrvhnoEiLCJnZW5kZXIiOiJNIiwiaXNzIjoiQ2FtRFgtQ2FtRGlnaUtleSIsInBlcnNvbmFsX2NvZGUiOiI1NDg1NDU0NjQ1IiwibmJmIjoxNTg0OTMyNjIwMDI4LCJmaXJzdF9uYW1lX2VuIjoiQ2hoYW5kYXJhIiwibW9iaWxlX3Bob25lIjoiKzg1NTExMTExMTExIiwiZG9tYWluIjoic3AuZGVtby5jYW1keC5pbyIsImxhc3RfbmFtZV9lbiI6IlNvayIsImZpcnN0X25hbWVfa2giOiLhnoXhn5DhnpPhn5LhnpHhnorhnrbhnprhn4nhnrYiLCJleHAiOjE1ODU3OTY2MjAwMjgsImlhdCI6MTU4NDkzMjYyMDAyOCwiZW1haWwiOiJkYXJheW9uZy50aXRoQHRlY2hvc3RhcnR1cC5jZW50ZXIiLCJqdGkiOiJmYTdhNzZhZS05NzJhLTQ0ZWYtODgwZi01NGUxYWQ1Y2E1NWEifQ==.MIGHAkFAmAWjKPZ3wJppguqZuAVB41Z1txU0VEDYD9r1pcMi7Dg/lJslpOT/iHhbaUnc6fRr1DQm/WN53ky7lGL2KVysMAJCAMAgakRaZVK8QyOYX+8ua6gh48wOpCKpiDvA/krQwqwoApk6jkPvTUO3JMCBhBC0VH2EVcRT1hqEw6jdfLIuop1L")
//                .required(true)                // for compulsory
//                .build();
//        java.util.List<Parameter> aParameters = new ArrayList<>();
//        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metadata())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()))
               // .globalOperationParameters(aParameters)
                .securityContexts(Collections.singletonList(securityContext()));
    }



    @Bean
    public SecurityScheme apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, "apiKey", "header");
    }

    @Bean
    public SecurityScheme apiCookieKey() {
        return new ApiKey(HttpHeaders.COOKIE, "apiKey", "cookie");
    }

    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("openid", "openid"));

        List<GrantType> grantTypes = new ArrayList();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
        grantTypes.add(passwordCredentialsGrant);

        return new OAuth("oauth2", authorizationScopeList, grantTypes);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/api/v1/**"))
                .build();
    }



    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[4];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");
        authorizationScopes[3] = new AuthorizationScope("openid", "openid");
        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration
                ("spring-boot-demo", "2927b550-f82a-4ecd-a119-3c71f5a4ac98", "SpringBootKeycloak", "SpringBootKeycloak", "Bearer access token", ApiKeyVehicle.HEADER, HttpHeaders.AUTHORIZATION," ");
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder().title("Authentication API").description("")
                .termsOfServiceUrl("https://www.example.com/api")
                .contact(new Contact("Developers", "https://projects.spring.io/spring-boot/", ""))
                .license("Open Source")
                .licenseUrl("\"https://www.apache.org/licenses/LICENSE-2.0")
                .version("1.0.0")
                .build();

    }

//    @Bean
//    UiConfiguration uiConfig() {
//        return UiConfigurationBuilder.builder()
//                .deepLinking(true)
//                .displayOperationId(false)
//                .defaultModelsExpandDepth(1)
//                .defaultModelExpandDepth(1)
//                .defaultModelRendering(ModelRendering.EXAMPLE)
//                .displayRequestDuration(false)
//                .docExpansion(DocExpansion.NONE)
//                .filter(true)
//                .maxDisplayedTags(null)
//                .operationsSorter(OperationsSorter.ALPHA)
//                .showExtensions(false)
//                .tagsSorter(TagsSorter.ALPHA)
//                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
//                .validatorUrl(null)
//                .build();
//    }
}
