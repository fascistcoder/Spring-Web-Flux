package com.example.springwebfluxrest.controllers;

import com.example.springwebfluxrest.domain.Vendor;
import com.example.springwebfluxrest.repositories.VendorRepository;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 25/04/22
 */
@RestController
@AllArgsConstructor
public class VendorController {

	private final VendorRepository vendorRepository;

	@GetMapping("/api/v1/vendors")
	Flux<Vendor> list() {
		return vendorRepository.findAll();
	}

	@GetMapping("/api/v1/vendors/{id}")
	Mono<Vendor> getById(@PathVariable String id) {
		return vendorRepository.findById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/vendors")
	Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
		return vendorRepository.saveAll(vendorStream).then();
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/api/v1/vendors/{id}")
	Mono<Vendor> update(String id, @RequestBody Vendor vendor) {
		vendor.setId(id);
		return vendorRepository.save(vendor);
	}
}
