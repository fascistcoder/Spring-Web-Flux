package com.example.springwebfluxrest.bootstrap;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.domain.Vendor;
import com.example.springwebfluxrest.repositories.CategoryRepository;
import com.example.springwebfluxrest.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 03/03/22
 */
@Component
@AllArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    @Override
    public void run(String... args) {
        loadCategories();
        loadVendors();
    }

    private void loadCategories() {
        categoryRepository.deleteAll()
                .thenMany(Flux.just("Fruits", "Nuts", "Breads", "Meats", "Eggs")
                        .map(name -> new Category(null, name)).
                        flatMap(categoryRepository::save))
                .then(categoryRepository.count())
                .subscribe(categories -> System.out.println(categories + " categories saved"));
    }

    private void loadVendors() {
        vendorRepository.deleteAll()
                .thenMany(Flux.just(Vendor.builder().firstName("Joe").lastName("Buck").build(), Vendor.builder().firstName("Michael")
                        .lastName("Weston").build(), Vendor.builder().firstName("Jessie").lastName("Waters").build(),
                        Vendor.builder().firstName("Jimmy").lastName("Buffet").build()).flatMap(vendorRepository::save))
                .then(vendorRepository.count()).subscribe(vendors -> System.out.println(vendors + " vendors saved"));
    }
}

