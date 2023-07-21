package com.example.infrastructure.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

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
                return Flux.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
