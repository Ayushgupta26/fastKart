package com.fastkart.seller.config;

import com.fastkart.seller.model.AddProductRequest;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

public class ProductDataReader extends FlatFileItemReader<AddProductRequest> {
    private Validator factory = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public AddProductRequest doRead() throws Exception {
        AddProductRequest addProductRequest = super.doRead();

        if (Objects.isNull(addProductRequest)) return null;

        Set<ConstraintViolation<AddProductRequest>> violations = this.factory.validate(addProductRequest);
        if (!violations.isEmpty()) {
            String errorMsg = String.format("input validation failed for record is '%s'", addProductRequest);
            throw new FlatFileParseException(errorMsg, Objects.toString(addProductRequest));
        } else {
            return addProductRequest;
        }
    }

}
