package com.hexagonal.architecture.pattern.config;

import com.hexagonal.architecture.pattern.adapter.BookJpaAdapter;
import com.hexagonal.architecture.pattern.ports.api.BookServicePort;
import com.hexagonal.architecture.pattern.ports.spi.BookPersistencePort;
import com.hexagonal.architecture.pattern.service.BookServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryMicroServiceConfig {

    @Bean
    public BookPersistencePort bookPersistence(){
        return new BookJpaAdapter();
    }

    @Bean
    public BookServicePort bookService(){
        return new BookServiceImpl(bookPersistence());
    }
}
