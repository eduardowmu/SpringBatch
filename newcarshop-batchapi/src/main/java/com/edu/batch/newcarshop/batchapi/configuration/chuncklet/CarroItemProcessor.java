package com.edu.batch.newcarshop.batchapi.configuration.chuncklet;

import com.edu.batch.newcarshop.batchapi.converter.CarroConverter;
import com.edu.batch.newcarshop.batchapi.dto.CarroDto;
import com.edu.batch.newcarshop.batchapi.model.Carro;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import java.util.logging.Logger;

public class CarroItemProcessor implements ItemProcessor<CarroDto, Carro>, StepExecutionListener {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CarroItemProcessor.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Iniciando o PROCESSOR...");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Finalizando Processor");
        return ExitStatus.COMPLETED;
    }

    @Override
    public Carro process(CarroDto carroDto) throws Exception {
        return CarroConverter.getCarro(carroDto);
    }
}
