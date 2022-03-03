package com.example.springwebfluxrest.repositories;

import com.example.springwebfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 03/03/22
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
