package org.sid.billingservice;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repositories.BillRepository;
import org.sid.billingservice.repositories.ProductItemRepository;
import org.sid.billingservice.services.CustomerRestClient;
import org.sid.billingservice.services.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(BillRepository billRepository,
                                 ProductItemRepository productItemRepository,
                                 ProductRestClient productRestClient,
                                 CustomerRestClient customerRestClient){
        return args -> {
            Collection<Product> products = productRestClient.allProducts().getContent();
            Customer customer = customerRestClient.findCutomerById(1L);
            Bill bill = Bill.builder().billDate(new Date()).customerId(customer.getId()).build();
            Bill savedBill = billRepository.save(bill);
            products.forEach(product -> {
                ProductItem productItem = ProductItem.builder()
                        .bill(savedBill)
                        .productId(product.getId())
                        .price(product.getPrice())
                        .discount(Math.random())
                        .quantity(1 + new Random().nextInt(10))
                        .build();
                productItemRepository.save(productItem);
            });
        };
    }

}
