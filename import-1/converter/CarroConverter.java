package com.wordpress.carledwinti.newcarshop.batchapi.converter;

import com.wordpress.carledwinti.newcarshop.batchapi.dto.CarroDto;
import com.wordpress.carledwinti.newcarshop.batchapi.model.Carro;
import com.wordpress.carledwinti.newcarshop.batchapi.utils.CarroUtils;
import com.wordpress.carledwinti.newcarshop.batchapi.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarroConverter {

    public static String[] getErrorToStringArray(String message) {
        List<String> listOut = new ArrayList<>();
        listOut.add(message);
        listOut.add(DateUtils.getNow());
        return listOut.stream().toArray(String[]::new);
    }

    public static String[] carroToStringArray(CarroDto carro) {
        List<String> listOut = new ArrayList<>();
        listOut.add(carro.getRenavam());
        listOut.add(carro.getMarca());
        listOut.add(carro.getModelo());
        listOut.add(carro.getAnoFabricacao());
        listOut.add(carro.getAnoModelo());
        listOut.add(carro.getValorCompra());
        listOut.add(carro.getInvalid());
        listOut.add(DateUtils.getNow());
        return listOut.stream().toArray(String[]::new);
    }

    public static Carro getCarro(CarroDto carroDto) {

        Carro carro = new Carro();
        carro.setRenavam(carroDto.getRenavam());
        carro.setMarca(carroDto.getMarca());
        carro.setModelo(carroDto.getModelo());
        carro.setAnoFabricacao(CarroUtils.convertToInteger(carroDto.getAnoFabricacao()));
        carro.setAnoModelo(CarroUtils.convertToInteger(carroDto.getAnoModelo()));
        carro.setValorCompra(CarroUtils.convertToBigDecimal(carroDto.getValorCompra()));
        carro.setLoja(CarroUtils.chooseStore(carroDto.getValorCompra()));
        carro.setValorVenda(CarroUtils.calculatePrice(carroDto.getValorCompra()));
        carro.setPercentalMaxDesconto(CarroUtils.calculateDiscount(carroDto.getValorCompra()));
        carro.setDataCadastro(DateUtils.toDate(LocalDateTime.now()));
        return carro;
    }
}