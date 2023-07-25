package com.visibility.infrastructure.repositories;

import com.visibility.domain.exceptions.GetProductsException;
import com.visibility.infrastructure.repositories.dto.ProductDto;
import com.visibility.infrastructure.repositories.dto.SizeDto;
import com.visibility.infrastructure.repositories.dto.StockDto;
import com.visibility.domain.entities.product.Product;
import com.visibility.domain.entities.product.ProductId;
import com.visibility.domain.entities.product.ProductPriorityPosition;
import com.visibility.domain.entities.size.Size;
import com.visibility.domain.entities.size.SizeBackSoon;
import com.visibility.domain.entities.size.SizeId;
import com.visibility.domain.entities.size.SizeQuantity;
import com.visibility.domain.entities.size.SizeSpecial;
import com.visibility.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.visibility.infrastructure.util.CsvReaderUtil.readCsvFile;

@Repository
public class ProductRepositoryImpl implements ProductRepository {


    @Value("${app.productFilePath}")
    private String productFilePath;

    @Value("${app.sizeFilePath}")
    private String sizeFilePath;

    @Value("${app.stockFilePath}")
    private String stockFilePath;

    @Override
    public Flux<Product> retrieveAllProducts() {

        Flux<ProductDto> productFlux = readCsvFile(getResourcePath(productFilePath), ProductDto.class);
        Flux<SizeDto> sizeFlux = readCsvFile(getResourcePath(sizeFilePath), SizeDto.class);
        Flux<StockDto> stockFlux = readCsvFile(getResourcePath(stockFilePath), StockDto.class);

        return Flux.zip(productFlux.collectList(), sizeFlux.collectList(), stockFlux.collectList())
                .flatMapSequential(tuple -> Flux.fromIterable(generateResult(tuple.getT1(), tuple.getT2(), tuple.getT3())))
                .onErrorResume(ex -> Flux.error(new GetProductsException("Error reading file", ex)));
    }

    private String getResourcePath(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(fileName).getPath();
    }

    public List<Product> generateResult(List<ProductDto> productDtos, List<SizeDto> sizeDtos, List<StockDto> stockDtos) {
        Map<Long, List<SizeDto>> sizesByProductId = sizeDtos.stream()
                .collect(Collectors.groupingBy(SizeDto::getProductId));

        Map<Long, List<StockDto>> stocksBySizeId = stockDtos.stream()
                .collect(Collectors.groupingBy(StockDto::getSizeId));

        return productDtos.stream()
                .map(productDto -> createProduct(productDto, sizesByProductId, stocksBySizeId))
                .collect(Collectors.toList());
    }

    private Product createProduct(ProductDto productDto, Map<Long, List<SizeDto>> sizesByProductId, Map<Long, List<StockDto>> stocksBySizeId) {
        ProductId productId = ProductId.builder().value(productDto.getId().intValue()).build();
        ProductPriorityPosition position = ProductPriorityPosition.builder().value(productDto.getSequence()).build();

        List<SizeDto> sizesForProduct = sizesByProductId.getOrDefault(productDto.getId(), List.of());

        List<Size> sizes = sizesForProduct.stream()
                .map(sizeDto -> createSize(sizeDto, stocksBySizeId))
                .collect(Collectors.toList());

        return Product.builder().id(productId).position(position).sizes(sizes).build();
    }

    private Size createSize(SizeDto sizeDto, Map<Long, List<StockDto>> stocksBySizeId) {
        SizeId sizeId = SizeId.builder().value(sizeDto.getId().intValue()).build();
        SizeBackSoon isBackSoon = SizeBackSoon.builder().value(sizeDto.isBackSoon()).build();
        SizeSpecial isSpecialSize = SizeSpecial.builder().value(sizeDto.isSpecial()).build();

        List<StockDto> stocksForSize = stocksBySizeId.getOrDefault(sizeDto.getId(), List.of());

        int quantity = stocksForSize.stream()
                .mapToInt(StockDto::getQuantity)
                .findFirst()
                .orElse(0);

        return Size.builder().id(sizeId).isBackSoon(isBackSoon).quantity(Optional.ofNullable(SizeQuantity.builder().value(quantity).build())).isSpecialSize(isSpecialSize).build();
    }
}
