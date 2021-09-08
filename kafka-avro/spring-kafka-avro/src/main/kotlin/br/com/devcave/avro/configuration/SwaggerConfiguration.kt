package br.com.devcave.avro.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Controller
@Configuration
class SwaggerConfiguration {
    @Bean
    fun docket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.devcave.avro.controller"))
            .paths(PathSelectors.ant("/**"))
            .build()
    }

    @GetMapping("/")
    fun index(): String = "redirect:swagger-ui/index.html"
}