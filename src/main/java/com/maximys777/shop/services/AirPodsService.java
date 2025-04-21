package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.AirPodsRequest;
import com.maximys777.shop.dto.response.AirPodsResponse;
import com.maximys777.shop.entities.AirPodsEntity;
import com.maximys777.shop.globalexceptions.BadRequestException;
import com.maximys777.shop.globalexceptions.NotFoundException;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.repositories.AirPodsRepository;
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
public class AirPodsService {
    private final AirPodsRepository airPodsRepository;

    public AirPodsResponse create(AirPodsRequest request) {
        boolean exists = airPodsRepository.findAll()
                .stream()
                .anyMatch(e -> e.getProductTitle().equalsIgnoreCase(request.getProductTitle())
                && e.getAirPodsModel().equalsIgnoreCase(request.getAirPodsModel()));
        if (exists) {
            throw new BadRequestException("Такие наушники уже существуют.");
        }

        AirPodsEntity entity = new AirPodsEntity();
        log.info("Наушники {} + {} созданы.", request.getProductTitle(), request.getAirPodsModel());
        return saveOrUpdate(entity, request);
    }

    public AirPodsResponse update(Long productId, AirPodsRequest request) {
        AirPodsEntity entity = airPodsRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Наушники не найдены."));
        log.info("Информация о наушниках {} + {} обновлена.", request.getProductTitle(), request.getAirPodsModel());
        return saveOrUpdate(entity, request);
    }

    public void deleteById(Long productId, AirPodsRequest request) {
        AirPodsEntity entity = airPodsRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Наушники не найдены"));
        log.info("Наушники {} + {} удалены.", request.getProductTitle(), request.getAirPodsModel());
        airPodsRepository.deleteById(productId);
    }

    public AirPodsResponse findById(Long productId) {
        return airPodsRepository.findById(productId)
                .map(GlobalMapper::mapToAirPodsResponse)
                .orElseThrow(() -> new NotFoundException("Наушники не найдены"));
    }

    public List<AirPodsResponse> findAllAirPods(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<AirPodsEntity> airPodsPage =  airPodsRepository.findAll(pageRequest);
        return airPodsPage.stream()
                .map(GlobalMapper::mapToAirPodsResponse)
                .collect(Collectors.toList());
    }

    public List<AirPodsResponse> searchByTitle(String productTitle, int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<AirPodsEntity> airPodsPage = airPodsRepository.findByProductTitleContainingIgnoreCase(productTitle, pageRequest);
        return airPodsPage.stream()
                .map(GlobalMapper::mapToAirPodsResponse)
                    .collect(Collectors.toList());
    }

    private AirPodsResponse saveOrUpdate(AirPodsEntity entity, AirPodsRequest request) {
        entity.setProductImage(request.getProductImage());
        entity.setProductTitle(request.getProductTitle());
        entity.setProductBrand(request.getProductBrand());
        entity.setProductDescription(request.getProductDescription());
        entity.setProductPrice(request.getProductPrice());
        entity.setProductAvailable(request.getProductAvailable());
        entity.setProductCategoryEnum(request.getProductCategoryEnum());

        entity.setAirPodsModel(request.getAirPodsModel());
        entity.setAirPodsColor(request.getAirPodsColor());
        entity.setAirPodsModelYear(request.getAirPodsModelYear());
        entity.setAirPodsWorkTime(request.getAirPodsWorkTime());

        return GlobalMapper.mapToAirPodsResponse(airPodsRepository.save(entity));
    }
}
