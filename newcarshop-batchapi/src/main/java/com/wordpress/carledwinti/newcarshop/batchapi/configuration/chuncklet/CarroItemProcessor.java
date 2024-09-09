package com.wordpress.carledwinti.newcarshop.batchapi.configuration.chuncklet;

import com.wordpress.carledwinti.newcarshop.batchapi.converter.CarroConverter;
import com.wordpress.carledwinti.newcarshop.batchapi.dto.CarroDto;
import com.wordpress.carledwinti.newcarshop.batchapi.model.Carro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

public class CarroItemProcessor implements ItemProcessor<CarroDto, Carro>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarroItemProcessor.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Iniciando o PROCESSOR...");
    }

    @Override
    public Carro process(CarroDto carroDto) {
        return CarroConverter.getCarro(carroDto);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Finalizando o PROCESSOR...");
        return ExitStatus.COMPLETED;
    }
}
