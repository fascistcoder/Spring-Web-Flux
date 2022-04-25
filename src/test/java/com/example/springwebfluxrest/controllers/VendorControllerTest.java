package com.example.springwebfluxrest.controllers;

import com.example.springwebfluxrest.domain.Vendor;
import com.example.springwebfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;

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
	void list() throws Exception {
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
	void getById() throws Exception {
		BDDMockito.given(vendorRepository.findById("someId"))
				.willReturn(Mono.just(Vendor.builder().firstName("Pulkit").lastName("Aggarwal").build()));

		webTestClient.get()
				.uri("/api/v1/vendors/someId")
				.exchange()
				.expectBodyList(Vendor.class);
	}

	@Test
	void testCreateVendor() throws Exception {
		BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
				.willReturn(Flux.just(Vendor.builder().firstName("Pulkit").lastName("Aggarwal").build(),
						Vendor.builder().firstName("Yogita").lastName("Aggarwal").build()));

		Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Pu").lastName("AA").build());

		webTestClient.post()
				.uri("/api/v1/vendors")
				.body(vendorMono, Vendor.class)
				.exchange()
				.expectStatus()
				.isCreated();
	}

	@Test
	void testUpdate() throws Exception {
		BDDMockito.given(vendorRepository.save(any(Vendor.class)))
				.willReturn(Mono.just(Vendor.builder().firstName("Pulkit").lastName("Aggarwal").build()));

		Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Pu").lastName("AA").build());

		webTestClient.put()
				.uri("/api/v1/vendors/www")
				.body(vendorMono, Vendor.class)
				.exchange()
				.expectStatus()
				.isOk();

	}
}