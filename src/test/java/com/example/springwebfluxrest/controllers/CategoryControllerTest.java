package com.example.springwebfluxrest.controllers;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.BDDMockito.given;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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

	private Category[] categories(String... descriptions) {
		return Stream.of(descriptions)
				.map((String d) -> Category.builder().description(d).build())
				.toArray(Category[]::new);
	}

	@Test
	void list() throws Exception {

		Category[] categories = categories("Cat1", "Cat2");
		given(categoryRepository.findAll())
				.willReturn(Flux.just(categories));

		webTestClient.get()
				.uri("/api/v1/categories/")
				.exchange()
				.expectBodyList(Category.class)
				.hasSize(2);
	}

	@Test
	void getById() throws Exception {

		given(categoryRepository.findById("someId"))
				.willReturn(Mono.just(Category.builder().description("Cat").build()));

		webTestClient.get()
				.uri("/api/v1/categories/someId")
				.exchange()
				.expectBodyList(Category.class);
	}

	@Test
	void testCreateCategory() throws Exception {

		Category[] categories = categories("Cat1", "Cat2");

		given(categoryRepository.saveAll(any(Publisher.class)))
				.willReturn(Flux.just(categories));

		Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some cate").build());

		webTestClient.post()
				.uri("/api/v1/categories/")
				.body(catToSaveMono, Category.class)
				.exchange()
				.expectStatus()
				.isCreated();
	}

	@Test
	void testUpdate() throws Exception {

		given(categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("Some cate").build());

		webTestClient.put()
				.uri("/api/v1/categories/wew")
				.body(catToUpdateMono, Category.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	void testPatchWithChanges()  throws Exception{
		given(categoryRepository.findById(anyString()))
				.willReturn(Mono.just(Category.builder().description("Description").build()));

		given(categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("New Description").build());

		webTestClient.patch()
				.uri("/api/v1/categories/wew")
				.body(catToUpdateMono, Category.class)
				.exchange()
				.expectStatus()
				.isOk();

		verify(categoryRepository).save(any());
	}

	@Test
	@Disabled
	void testPatchNoChanges() throws Exception {
		given(categoryRepository.findById(anyString()))
				.willReturn(Mono.just(Category.builder().description("description").build()));

		given(categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> catToUpdateMono = Mono.just(Category.builder().build());

		webTestClient.patch()
				.uri("/api/v1/categories/wew")
				.body(catToUpdateMono, Category.class)
				.exchange()
				.expectStatus()
				.isOk();

		verify(categoryRepository, never()).save(any());
	}
}