package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.SmartphoneRequest;
import com.maximys777.shop.dto.response.SmartphoneResponse;
import com.maximys777.shop.entities.SmartphoneEntity;
import com.maximys777.shop.globalexceptions.BadRequestException;
import com.maximys777.shop.globalexceptions.NotFoundException;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.repositories.SmartphoneRepository;
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
public class SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;

    public SmartphoneResponse create(SmartphoneRequest request) {
        boolean exists = smartphoneRepository.findAll()
                .stream()
                .anyMatch(e -> e.getProductTitle().equals(request.getProductTitle())
                && e.getSmartphoneModel().equals(request.getSmartphoneModel()));
        if (exists) {
            throw new BadRequestException("Такой телефон уже существует.");
        }

        SmartphoneEntity entity = new SmartphoneEntity();
        log.info("Телефон {} + {} создан.", request.getProductTitle(), request.getSmartphoneModel());
        return saveOrUpdate(entity, request);
    }

    public SmartphoneResponse update(Long productId, SmartphoneRequest request) {
        SmartphoneEntity entity = smartphoneRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Телефон не найден."));
        log.info("Информация о телефоне {} + {} обновлена.", request.getProductTitle(), request.getSmartphoneModel());
        return saveOrUpdate(entity, request);
    }

    public void deleteById(Long productId, SmartphoneRequest request) {
        SmartphoneEntity entity = smartphoneRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Смартфон не найден."));
        log.info("Телефон {} + {} удалены.",  request.getProductTitle(), request.getSmartphoneModel());
        smartphoneRepository.deleteById(productId);
    }

    public SmartphoneResponse getById(Long productId) {
        return smartphoneRepository.findById(productId)
                .map(GlobalMapper::mapToSmartphoneResponse)
                .orElseThrow(() -> new NotFoundException("Смартфон не найден"));
    }

    public List<SmartphoneResponse> getAllSmartphone(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<SmartphoneEntity> smartphonePage = smartphoneRepository.findAll(pageRequest);
        return smartphonePage.stream()
                .map(GlobalMapper::mapToSmartphoneResponse)
                .collect(Collectors.toList());
    }

    public List<SmartphoneResponse> searchByTitle(String productTitle, int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<SmartphoneEntity> smartphonePage =  smartphoneRepository.findByProductTitleContainingIgnoreCase(productTitle, pageRequest);
        return smartphonePage.stream()
                .map(GlobalMapper::mapToSmartphoneResponse)
                .collect(Collectors.toList());
    }


    private SmartphoneResponse saveOrUpdate(SmartphoneEntity entity, SmartphoneRequest request) {
        entity.setProductImage(request.getProductImage());
        entity.setProductTitle(request.getProductTitle());
        entity.setProductBrand(request.getProductBrand());
        entity.setProductDescription(request.getProductDescription());
        entity.setProductPrice(request.getProductPrice());
        entity.setProductAvailable(request.getProductAvailable());
        entity.setProductCategoryEnum(request.getProductCategoryEnum());

        entity.setSmartphoneModel(request.getSmartphoneModel());
        entity.setSmartphoneOs(request.getSmartphoneOs());
        entity.setSmartphoneStorage(request.getSmartphoneStorage());
        entity.setSmartphoneRam(request.getSmartphoneRam());
        entity.setSmartphoneColor(request.getSmartphoneColor());
        entity.setBatteryCapacity(request.getBatteryCapacity());
        entity.setBatteryUnit(request.getBatteryUnit());
        entity.setSmartphoneModelYear(request.getSmartphoneModelYear());

        return GlobalMapper.mapToSmartphoneResponse(smartphoneRepository.save(entity));
    }
}
