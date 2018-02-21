package com.zeroandone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.AppCacheManifestTransformer;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").setCachePeriod(31556926).addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new VersionResourceResolver()
                        //.addFixedVersionStrategy(this.version, "/**/*.js")
                        .addContentVersionStrategy("/**"))
                .addTransformer(new AppCacheManifestTransformer())
                .addTransformer(new CssLinkResourceTransformer());

    }

    @Bean
    public ResourceUrlEncodingFilter filter() {
        return new ResourceUrlEncodingFilter();
    }

}
