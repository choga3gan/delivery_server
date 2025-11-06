package com.choga3gan.delivery.global.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스웨거 설정
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        // Security Scheme 정의
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Security Requirement 정의
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        Server server = new Server();
        server.setUrl("https://api3.sparta-project.xyz");

        return new OpenAPI()
                .info(new Info()
                        .title("배달 서비스 REST API")
                        .description("초가삼간 조 배달 서비스")
                        .version("1.0.0")
                        .contact(new Contact().email(""))
                )
                .addSecurityItem(securityRequirement)
                .schemaRequirement("BearerAuth", securityScheme)
                .addServersItem(server);
    }

    @Bean
    public GroupedOpenApi userApi(){

        return GroupedOpenApi.builder()
                .group("user-api") // 스웨거 그룹
                .displayName("회원 API") // 그룹명
                .pathsToMatch("/v1/users/**") // 그룹에 해당되는 API 주소
                .build();
    }

    @Bean
    public GroupedOpenApi roleApi(){
        return GroupedOpenApi.builder()
                .group("role-api")
                .displayName("역할 API")
                .pathsToMatch("/v1/roles/**")
                .build();
    }

    @Bean
    public GroupedOpenApi storeApi(){
        return GroupedOpenApi.builder()
                .group("store-api")
                .displayName("매장 API")
                .pathsToMatch("/v1/stores/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productApi(){
        return GroupedOpenApi.builder()
                .group("product-api")
                .displayName("상품 API")
                .pathsToMatch("/v1/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reviewApi(){
        return GroupedOpenApi.builder()
                .group("review-api")
                .displayName("리뷰 API")
                .pathsToMatch("/v1/review/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cartApi(){
        return GroupedOpenApi.builder()
                .group("cart-api")
                .displayName("카트 API")
                .pathsToMatch("/v1/carts/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi(){
        return GroupedOpenApi.builder()
                .group("order-api")
                .displayName("주문 API")
                .pathsToMatch("/v1/orders/**")
                .build();
    }

    @Bean
    public GroupedOpenApi paymentApi(){
        return GroupedOpenApi.builder()
                .group("payment-api")
                .displayName("결제 API")
                .pathsToMatch("/v1/payments/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoryApi(){
        return GroupedOpenApi.builder()
                .group("category-api")
                .displayName("카테고리 API")
                .pathsToMatch("/v1/categories/**")
                .build();
    }

}