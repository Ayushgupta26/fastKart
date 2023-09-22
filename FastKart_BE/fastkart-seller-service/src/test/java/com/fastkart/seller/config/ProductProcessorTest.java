package com.fastkart.seller.config;

import com.fastkart.seller.model.AddProductRequest;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ProductProcessorTest {

    @InjectMocks
    ProductProcessor productProcessor;

    @Test
    void processTest() throws Exception {
        assertNotNull(productProcessor.process(new AddProductRequest()));
    }
}
