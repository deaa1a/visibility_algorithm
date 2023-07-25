package com.visibility.infrastructure.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import com.visibility.domain.exceptions.GetProductsException;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.awt.color.ProfileDataException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReaderUtil {

    public static <T> Flux<T> readCsvFile(String filePath, Class<T> dtoClass) {
        return Flux.defer(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                        .withType(dtoClass)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                return Flux.fromIterable(csvToBean.parse());
            } catch (IOException e) {
                return Flux.error(new GetProductsException("error reading file",e));
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
