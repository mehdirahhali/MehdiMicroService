package org.sid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    //****Configuration statique (sans discovery)****
    // On configure les routes par cette methode ou bien dans le fichier application.yml
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route(r -> r.path("/customers/**").uri("http://localhost:8081/"))
//                .route(r -> r.path("/products/**").uri("http://localhost:8082/"))
//                .build();
//    }

    //****Configuration statique (avec le discovery)****
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route(r -> r.path("/customers/**").uri("lb://CUSTOMER-SERVICE"))
//                .route(r -> r.path("/products/**").uri("lb://PRODUCT-SERVICE"))
//                .build();
//    }


    //****Configuration dynamique (avec le discovery)****
    @Bean
    public DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc,
                                                               DiscoveryLocatorProperties dlp){
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }
}
