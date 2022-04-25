package com.example.springwebfluxrest.controllers;

import com.example.springwebfluxrest.domain.Category;
import com.example.springwebfluxrest.repositories.CategoryRepository;
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
public class CategoryController {

	private static final String BASE_URL = "/api/v1/categories/";
	private final CategoryRepository categoryRepository;

	@GetMapping(BASE_URL)
	Flux<Category> list() {
		return categoryRepository.findAll();
	}

	@GetMapping(BASE_URL + "{id}")
	Mono<Category> getById(@PathVariable String id) {
		return categoryRepository.findById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(BASE_URL)
	Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
		return categoryRepository.saveAll(categoryStream).then();
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(BASE_URL + "{id}")
	Mono<Category> update(String id, @RequestBody Category category){
		category.setId(id);
		return categoryRepository.save(category);
	}
}
