package com.example.infrastructure.repositories;

import com.example.infrastructure.dto.ProductDto;
import com.example.infrastructure.dto.SizeDto;
import com.example.infrastructure.dto.StockDto;
import com.visibility.domain.entities.product.Product;
import com.visibility.domain.entities.product.ProductId;
import com.visibility.domain.entities.product.ProductPriorityPosition;
import com.visibility.domain.entities.size.Size;
import com.visibility.domain.entities.size.SizeBackSoon;
import com.visibility.domain.entities.size.SizeId;
import com.visibility.domain.entities.size.SizeQuantity;
import com.visibility.domain.entities.size.SizeSpecial;
import com.visibility.domain.repositories.ProductRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.infrastructure.util.CsvReaderUtil.readCsvFile;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Flux<Product> retrieveAllProducts() {
        String productFilePath = "src/main/resources/product.csv";
        String sizeFilePath = "src/main/resources/size.csv";
        String stockFilePath = "src/main/resources/stock.csv";

        Flux<ProductDto> productFlux = readCsvFile(productFilePath, ProductDto.class);
        Flux<SizeDto> sizeFlux = readCsvFile(sizeFilePath, SizeDto.class);
        Flux<StockDto> stockFlux = readCsvFile(stockFilePath, StockDto.class);

        return Flux.zip(productFlux.collectList(), sizeFlux.collectList(), stockFlux.collectList())
                .flatMapSequential(tuple -> Flux.fromIterable(generateResult(tuple.getT1(), tuple.getT2(), tuple.getT3())));
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
