package com.example.springwebfluxrest.repositories;

import com.example.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 03/03/22
 */
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
