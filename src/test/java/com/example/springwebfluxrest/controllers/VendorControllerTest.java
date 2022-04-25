package com.example.springwebfluxrest.controllers;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.domain.Vendor;
import com.example.springwebfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 25/04/22
 */
class VendorControllerTest {

	WebTestClient webTestClient;

	@Mock VendorRepository vendorRepository;

	VendorController vendorController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		vendorController = new VendorController(vendorRepository);
		webTestClient = WebTestClient.bindToController(vendorController).build();
	}

	@Test
	void list() {
		BDDMockito.given(vendorRepository.findAll())
				.willReturn(Flux.just(Vendor.builder().firstName("Pulkit").lastName("Aggarwal").build(),
						Vendor.builder().firstName("Yogita").lastName("Aggarwal").build()));

		webTestClient.get()
				.uri("/api/v1/vendors/")
				.exchange()
				.expectBodyList(Vendor.class)
				.hasSize(2);
	}

	@Test
	void getById() {
		BDDMockito.given(vendorRepository.findById("someId"))
				.willReturn(Mono.just(Vendor.builder().firstName("Pulkit").lastName("Aggarwal").build()));

		webTestClient.get()
				.uri("/api/v1/vendors/someId")
				.exchange()
				.expectBodyList(Vendor.class);
	}
}