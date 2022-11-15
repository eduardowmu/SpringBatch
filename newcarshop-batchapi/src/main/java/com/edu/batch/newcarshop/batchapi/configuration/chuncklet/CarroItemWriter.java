package com.edu.batch.newcarshop.batchapi.configuration.chuncklet;

import com.edu.batch.newcarshop.batchapi.model.Carro;
import com.edu.batch.newcarshop.batchapi.repository.CarroRepository;
import com.edu.batch.newcarshop.batchapi.utils.CsvFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

public class CarroItemWriter implements ItemWriter<Carro>, StepExecutionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarroItemWriter.class);
    private CsvFileUtils csvSavedCars;

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.csvSavedCars = new CsvFileUtils("savedCars", false);
        LOGGER.info("Finalizando o writer...");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            this.csvSavedCars.closeWriter();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends Carro> list) {
        List<? extends Carro> savedCarroList = carroRepository.saveAll(list);

        savedCarroList.stream().forEach(carro -> {
            try {
                this.csvSavedCars.writer(carro);
            } catch(IOException e) {
                e.printStackTrace();
            }
        });
    }
}