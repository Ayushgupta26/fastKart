package com.fastkart.seller.config;

import com.fastkart.seller.entity.Product;
import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.repository.ProductRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ProductRepository productRepository;

    @Bean
    @StepScope
    public FlatFileItemReader<AddProductRequest> reader(@Value("#{jobParameters['inputFile']}") String inputFile) {
        FlatFileItemReader<AddProductRequest> itemReader = new ProductDataReader();
        itemReader.setResource(new FileSystemResource(inputFile));
        itemReader.setName("csvProductDataReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<AddProductRequest> lineMapper() {
        DefaultLineMapper<AddProductRequest> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("productName", "productCategory", "productDescription", "minBid");
        BeanWrapperFieldSetMapper<AddProductRequest> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(AddProductRequest.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    @StepScope
    public ProductProcessor processor(@Value("#{jobParameters['sellerName']}") String inputFile) {
        return new ProductProcessor(inputFile);
    }

    @Bean
    public RepositoryItemWriter<Product> writer() {
        RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
        writer.setRepository(productRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1(ItemReader<AddProductRequest> reader, ItemProcessor<AddProductRequest, Product> processor, ItemWriter<Product> writer) {
        return stepBuilderFactory.get("step").<AddProductRequest, Product>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job runJob(Step step) {
        return jobBuilderFactory.get("importProducts")
                .flow(step).end().build();
    }
}
