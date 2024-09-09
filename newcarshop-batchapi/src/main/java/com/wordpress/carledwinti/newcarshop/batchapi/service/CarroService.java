package com.wordpress.carledwinti.newcarshop.batchapi.service;

import com.wordpress.carledwinti.newcarshop.batchapi.model.Carro;
import com.wordpress.carledwinti.newcarshop.batchapi.repository.CarroRepository;
import com.wordpress.carledwinti.newcarshop.batchapi.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarroService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarroService.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private CarroRepository carroRepository;

    private static final String A_CADA_10_SEG = "*/10 * * * * *";
    private static final String A_CADA_30_SEG = "*/30 * * * * *";
    private static final String A_CADA_60_SEG = "*/60 * * * * *";
    private static final String AS_8_E_AS_9_HRS_T_OS_DIAS = "0 0 8-9 * * *";
    private static final String A_CADA_HR_T_OS_DIAS = "0 0 * * * *";
    private static final String AS_6_E_AS_18_HRS_T_OS_DIAS = "0 0 6,18 * * *";
    private static final String NO_NATAL_25_DEZ_TODOS_OS_ANOS = "0 0 0 25 12 ?";
    private static final String A_CADA_30_MIN_ENTRE_7_E_11_HRS_T_OS_DIAS = "0 0/30 8-10 * * *";
    private static final String A_CADA_HR_DAS_8_AS_17_HRS_DE_SEGUNDA_A_SEXTA = "0 0 8-17 * * MON-FRI";

    @Scheduled(cron = AS_8_E_AS_9_HRS_T_OS_DIAS)
    public BatchStatus batchExecute(){

        LOGGER.info("Iniciou o Job " + DateUtils.getNow());

        Map<String, JobParameter> map = new HashMap<>();
        map.put("time", new JobParameter(System.currentTimeMillis()));

        try {
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters(map));

            while(jobExecution.isRunning()){
                LOGGER.info("Job em execução...");
            }

            LOGGER.info(DateUtils.getNow());
            return jobExecution.getStatus();
        } catch (Exception e) {
            LOGGER.info("Falha ao tentar executar JOB " + e.getMessage());
            return BatchStatus.FAILED;
        }
    }

    public List<Carro> findAll(){
        return carroRepository.findAll();
    }

}
