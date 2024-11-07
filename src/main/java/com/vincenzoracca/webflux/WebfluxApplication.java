package com.vincenzoracca.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class WebfluxApplication {

	private static final Logger log = LoggerFactory.getLogger(WebfluxApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}


	@RestController
	public class MockApi {

		@GetMapping
		public Mono<ResponseEntity<Flux<Book>>> findAll() {
			log.info("Call findAll");
			Flux<Book> booksFlux = Flux.range(0, 10)
					.delayElements(Duration.ofMillis(50))
					.map(i -> new Book(i, UUID.randomUUID().toString()));

			return Mono.just(ResponseEntity.ok(booksFlux));
		}

	}

	record Book(int bookId, String bookName){}

}
