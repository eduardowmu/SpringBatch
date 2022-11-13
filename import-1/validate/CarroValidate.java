package com.wordpress.carledwinti.newcarshop.batchapi.validate;

import com.wordpress.carledwinti.newcarshop.batchapi.converter.CarroConverter;
import com.wordpress.carledwinti.newcarshop.batchapi.dto.CarroDto;
import com.wordpress.carledwinti.newcarshop.batchapi.utils.CsvFileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarroValidate {

    public CarroValidate() {}

    public static List<CarroDto> validate(List<CarroDto> carroDtoList) throws IOException {

        List<CarroDto> validList = new ArrayList<>();
        List<CarroDto> invalidList = new ArrayList<>();

        carroDtoList.stream().forEach(dto -> {

            if(isValid(dto)){
                validList.add(dto);
            }else{
                invalidList.add(dto);
            }
        });

        createCsvToInvalidList(invalidList);
        return validList;
    }

    private static void createCsvToInvalidList(List<CarroDto> invalidList) throws IOException {

        if(!invalidList.isEmpty()){

            CsvFileUtils csvOutInvalid = new CsvFileUtils("invalid-import", false);

            invalidList.stream().forEach(carro -> {
                try {
                    csvOutInvalid.writerError(CarroConverter.carroToStringArray(carro));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            csvOutInvalid.closeWriter();
        }
    }

    public void emptyFile(LocalDateTime localDateTime) throws IOException {

        CsvFileUtils emptyCSV = new CsvFileUtils("empty-file",false);

        try {
            emptyCSV.writerError(CarroConverter.getErrorToStringArray("Tentativa de Import de arquivo vazio!"));
            emptyCSV.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValid(CarroDto carroDto) {
        return StringUtils.isNotBlank(carroDto.getRenavam())
                && StringUtils.isNotBlank(carroDto.getMarca())
                && StringUtils.isNotBlank(carroDto.getModelo())
                && StringUtils.isNotBlank(carroDto.getAnoFabricacao())
                && StringUtils.isNotBlank(carroDto.getModelo())
                && StringUtils.isNotBlank(carroDto.getValorCompra());
    }
}