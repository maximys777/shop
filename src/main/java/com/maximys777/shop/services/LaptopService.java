package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.LaptopRequest;
import com.maximys777.shop.dto.response.LaptopResponse;
import com.maximys777.shop.entities.LaptopEntity;
import com.maximys777.shop.globalexceptions.BadRequestException;
import com.maximys777.shop.globalexceptions.NotFoundException;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.repositories.LaptopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LaptopService {
    private final LaptopRepository laptopRepository;

    public LaptopResponse create(LaptopRequest request) {
        boolean exists = laptopRepository.findAll()
                .stream()
                .anyMatch(e -> e.getProductTitle().equals(request.getProductTitle())
                && e.getLaptopModel().equals(request.getLaptopModel()));
        if (exists) {
            throw new BadRequestException("Такой ноутбук уже существует.");
        }

        LaptopEntity entity = new LaptopEntity();
        log.info("Ноутбук {} + {} создан.", request.getProductTitle(), request.getLaptopModel());
        return saveOrUpdate(entity, request);
    }

    public LaptopResponse update(Long productId, LaptopRequest request) {
        LaptopEntity entity = laptopRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Ноутбук не найден."));
        log.info("Информация о ноутбуке {} + {} обновлена.", request.getProductTitle(), request.getLaptopModel());
        return saveOrUpdate(entity, request);
    }

    public void deleteById(Long productId, LaptopRequest request) {
        LaptopEntity entity = laptopRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Ноутбук не найден."));
        log.info("Наушники {} + {} удалены.",  request.getProductTitle(), request.getLaptopModel());
        laptopRepository.deleteById(productId);
    }

    public LaptopResponse findById(Long productId) {
        return laptopRepository.findById(productId)
                .map(GlobalMapper::mapToLaptopResponse)
                .orElseThrow(() -> new NotFoundException("Ноутбук не найден."));
    }

    public List<LaptopResponse> findAllLaptop(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<LaptopEntity> laptopPage = laptopRepository.findAll(pageRequest);
        return laptopPage.stream()
                .map(GlobalMapper::mapToLaptopResponse)
                .collect(Collectors.toList());
    }

    public List<LaptopResponse> searchByTitle(String productTitle, int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<LaptopEntity> laptopPage = laptopRepository.findByProductTitleContainingIgnoreCase(productTitle, pageRequest);
        return laptopPage.stream()
                .map(GlobalMapper::mapToLaptopResponse)
                .collect(Collectors.toList());
    }

    private LaptopResponse saveOrUpdate(LaptopEntity entity, LaptopRequest request) {
        entity.setProductImage(request.getProductImage());
        entity.setProductTitle(request.getProductTitle());
        entity.setProductBrand(request.getProductBrand());
        entity.setProductDescription(request.getProductDescription());
        entity.setProductPrice(request.getProductPrice());
        entity.setProductAvailable(request.getProductAvailable());
        entity.setProductCategoryEnum(request.getProductCategoryEnum());

        entity.setLaptopModel(request.getLaptopModel());
        entity.setLaptopDiagonal(request.getLaptopDiagonal());
        entity.setLaptopProcessor(request.getLaptopProcessor());
        entity.setLaptopOs(request.getLaptopOs());
        entity.setLaptopRam(request.getLaptopRam());
        entity.setLaptopStorage(request.getLaptopStorage());
        entity.setLaptopGraphCard(request.getLaptopGraphCard());
        entity.setLaptopColor(request.getLaptopColor());
        entity.setLaptopBattery(request.getLaptopBattery());
        entity.setLaptopModelYear(request.getLaptopModelYear());

        return GlobalMapper.mapToLaptopResponse(laptopRepository.save(entity));
    }
}
