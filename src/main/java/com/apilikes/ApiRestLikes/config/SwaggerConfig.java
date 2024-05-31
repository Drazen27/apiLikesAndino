package com.apilikes.ApiRestLikes.config;

import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    // @Bean
    // public Docket api() {
    //     return new Docket(DocumentationType.SWAGGER_2)
    //             .select()
    //             .apis(RequestHandlerSelectors.basePackage("com.example.package"))
    //             .paths(PathSelectors.any())
    //             .build()
    //             .apiInfo(apiInfo());
    // }

    // private ApiInfo apiInfo() {
    //     return new ApiInfoBuilder()
    //             .title("Título de la API")
    //             .description("Descripción de la API")
    //             .version("1.0.0")
    //             .termsOfServiceUrl("http://terms.of.service.url")
    //             .license("Licencia de la API")
    //             .licenseUrl("http://url.de.la.licencia")
    //             .contact(new springfox.documentation.service.Contact("Nombre del contacto", "http://url.del.contacto", "correo@contacto.com"))
    //             .build();
    // }
}