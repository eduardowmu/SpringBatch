package com.edu.batch.newcarshop.batchapi.configuration;
import com.edu.batch.newcarshop.batchapi.configuration.chuncklet.CarroItemProcessor;
import com.edu.batch.newcarshop.batchapi.configuration.chuncklet.CarroItemReader;
import com.edu.batch.newcarshop.batchapi.configuration.chuncklet.CarroItemWriter;
import com.edu.batch.newcarshop.batchapi.configuration.tasklet.CarroValidateTasklet;
import com.edu.batch.newcarshop.batchapi.dto.CarroDto;
import com.edu.batch.newcarshop.batchapi.model.Carro;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return this.jobBuilderFactory
                .get("carrosJob")
                .start(carroValidateTaskletStep())
                .next(carroEnriquecimentoChunckletStep(carroItemReader(),
                        carroItemProcessor(), carroItemWriter()))
                .build();
    }

    @Bean
    public Step carroValidateTaskletStep() {
        return stepBuilderFactory.get("carroValidateTaskStep")
                .tasklet(new CarroValidateTasklet("carros-import"))
                .build();
    }

    @Bean
    public Step carroEnriquecimentoChunckletStep(
            ItemReader<CarroDto> carroItemReader,
            ItemProcessor<CarroDto, Carro> carroItemProcessor,
            ItemWriter<Carro> carroItemWriter) {
        return stepBuilderFactory.get("carroEnriquecimentoChunckletStep")
                .<CarroDto, Carro>chunk(5)
                .reader(carroItemReader)
                .processor(carroItemProcessor)
                .writer(carroItemWriter)
                .build();
    }

    @Bean
    public ItemReader<CarroDto> carroItemReader() {
        return new CarroItemReader();
    }

    @Bean
    public ItemProcessor<CarroDto, Carro> carroItemProcessor() {
        return new CarroItemProcessor();
    }

    @Bean
    public ItemWriter<Carro> carroItemWriter() {
        return new CarroItemWriter();
    }
}