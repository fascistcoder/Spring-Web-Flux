package com.example.springwebfluxrest.controllers;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 25/04/22
 */
class CategoryControllerTest {

	WebTestClient webTestClient;

	@Mock
	CategoryRepository categoryRepository;

	CategoryController categoryController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		categoryController = new CategoryController(categoryRepository);
		webTestClient = WebTestClient.bindToController(categoryController).build();
	}

	@Test
	void list() throws Exception{
		BDDMockito.given(categoryRepository.findAll())
				.willReturn(Flux.just(Category.builder().description("Cat1").build(),
						Category.builder().description("Cat2").build()));

		webTestClient.get()
				.uri("/api/v1/categories/")
				.exchange()
				.expectBodyList(Category.class)
				.hasSize(2);
	}

	@Test
	void getById() throws Exception{
		BDDMockito.given(categoryRepository.findById("someId"))
				.willReturn(Mono.just(Category.builder().description("Cat").build()));

		webTestClient.get()
				.uri("/api/v1/categories/someId")
				.exchange()
				.expectBodyList(Category.class);
	}
}