package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author： yuan
 * @Description: swagger2配置类
 * @Date: Created in 10:57 2017/12/22
 * @Modified By:
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    ApiInfo apiInfo = new ApiInfo("测试api",
            "ssm测试接口文档",
            "", "", new Contact("", "", ""), "", "");

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("RESTFUL API")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build();
    }

}
