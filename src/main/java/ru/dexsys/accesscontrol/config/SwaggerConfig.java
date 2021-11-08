package ru.dexsys.accesscontrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfo(
                                "Симулятор проверки доступа",
                                "Проверяет доступ в комнату по различным условиям",
                                ApiInfo.DEFAULT.getVersion(),
                                null,
                                new Contact("Ivan Matveev", "https://t.me/Vanadiiii42", "vanadiiii42@gmail.com"),
                                ApiInfo.DEFAULT.getLicense(),
                                ApiInfo.DEFAULT.getLicenseUrl(),
                                ApiInfo.DEFAULT.getVendorExtensions()
                                )
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.dexsys.accesscontrol"))
                .build();
    }
}
