package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.SmartphoneRequest;
import com.maximys777.shop.dto.response.SmartphoneResponse;
import com.maximys777.shop.entities.SmartphoneEntity;
import com.maximys777.shop.globalexceptions.NotFoundException;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.repositories.SmartphoneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;

    public SmartphoneResponse create(SmartphoneRequest request) {
        SmartphoneEntity entity = new SmartphoneEntity();
        return saveOrUpdate(entity, request);
    }

    public SmartphoneResponse update(Long id, SmartphoneRequest request) {
        SmartphoneEntity entity = smartphoneRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Смартфон не найден."));
        return saveOrUpdate(entity, request);
    }

    public void deleteById(Long id) {
        smartphoneRepository.deleteById(id);
    }

    public SmartphoneResponse getById(Long id) {
        return smartphoneRepository.findById(id)
                .map(GlobalMapper::mapToSmartphoneResponse)
                .orElseThrow(() -> new NotFoundException("Смартфон не найден"));
    }

    public List<SmartphoneResponse> getAllSmartphones(int page, int size, String sortBy, String sortDirection) {
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
