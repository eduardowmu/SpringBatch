package com.edu.batch.newcarshop.batchapi.configuration.tasklet;

import com.edu.batch.newcarshop.batchapi.dto.CarroDto;
import com.edu.batch.newcarshop.batchapi.utils.CsvFileUtils;
import com.edu.batch.newcarshop.batchapi.validate.CarroValidate;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import javax.naming.Context;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class CarroValidateTasklet implements Tasklet, StepExecutionListener {
    private List<CarroDto> carroDtoList;
    private String fileName;

    public CarroValidateTasklet() {}

    public CarroValidateTasklet(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.carroDtoList = new ArrayList<>();
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        CsvFileUtils csvIn = new CsvFileUtils(this.fileName, true);

        CarroDto carroDto = csvIn.read();

        while(carroDto != null) {
            this.carroDtoList.add(carroDto);
            carroDto = csvIn.read();
        }

        csvIn.closeReader();

        this.carroDtoList = CarroValidate.validate(this.carroDtoList);

        if(carroDtoList.isEmpty()) {
            throw new RuntimeException("A lista de carros validos está vazia!");
        }

        return RepeatStatus.FINISHED;
    }

    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution()
                .getExecutionContext()
                .put("carroInList", this.carroDtoList);
        return ExitStatus.COMPLETED;
    }
}
