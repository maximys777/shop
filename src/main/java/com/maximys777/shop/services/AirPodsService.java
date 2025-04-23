package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.AirPodsRequest;
import com.maximys777.shop.dto.response.AirPodsResponse;
import com.maximys777.shop.entities.AirPodsEntity;
import com.maximys777.shop.entities.ProductCategoryEnum;
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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AirPodsService {
    private final AirPodsRepository airPodsRepository;
    private final CloudinaryService cloudinaryService;

    public AirPodsResponse create(MultipartFile productImage,
                                  String productTitle,
                                  String productBrand,
                                  String productDescription,
                                  BigDecimal productPrice,
                                  String productAvailable,
                                  ProductCategoryEnum productCategoryEnum,
                                  String AirPodsModel,
                                  String AirPodsColor,
                                  Integer AirPodsModelYear,
                                  Integer AirPodsWorkTime
                                  ) {
        boolean exists = airPodsRepository.findAll()
                .stream()
                .anyMatch(e -> e.getProductTitle().equalsIgnoreCase(productTitle));
        if (exists) {
            throw new BadRequestException("Такие наушники уже существуют.");
        }

        String imageUrl = cloudinaryService.uploadFile(productImage, "product/airpods", "image").get("secure_url");
        AirPodsEntity entity = new AirPodsEntity();
        AirPodsRequest request = new AirPodsRequest(imageUrl, productTitle, productBrand, productDescription, productPrice,
                productAvailable, productCategoryEnum, AirPodsModel, AirPodsColor, AirPodsModelYear, AirPodsWorkTime);

        AirPodsResponse response = saveOrUpdate(entity, request);

        log.info("Наушники {} созданы.", request.getProductTitle());
        return response;
    }

    public AirPodsResponse update(Long productId,
                                  MultipartFile productImage,
                                  String productTitle,
                                  String productBrand,
                                  String productDescription,
                                  BigDecimal productPrice,
                                  String productAvailable,
                                  ProductCategoryEnum productCategoryEnum,
                                  String AirPodsModel,
                                  String AirPodsColor,
                                  Integer AirPodsModelYear,
                                  Integer AirPodsWorkTime) {
        AirPodsEntity entity = airPodsRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Наушники не найдены."));

        String imageUrl = entity.getProductImage();
        if (productImage != null && !productImage.isEmpty()) {
            String oldImageUrl = entity.getProductImage();
            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                String publicId = cloudinaryService.extractPublicIdFromUrl(oldImageUrl);
                log.info("Удалено старое изображение товара {}", productTitle);
                cloudinaryService.deleteFile(publicId);
            }

            imageUrl = cloudinaryService.uploadFile(productImage, "product/airpods", "image").get("secure_url");
            entity.setProductImage(imageUrl);
        }

        AirPodsRequest request = new AirPodsRequest(
          imageUrl,
          productTitle != null ? productTitle : entity.getProductTitle(),
          productBrand != null ? productBrand : entity.getProductBrand(),
          productDescription != null ? productDescription : entity.getProductDescription(),
          productPrice != null ? productPrice : entity.getProductPrice(),
          productAvailable != null ? productAvailable : entity.getProductAvailable(),
          productCategoryEnum != null ? productCategoryEnum : entity.getProductCategoryEnum(),
          AirPodsModel != null ? AirPodsModel : entity.getAirPodsModel(),
          AirPodsColor != null ? AirPodsColor : entity.getAirPodsColor(),
          AirPodsModelYear != null ? AirPodsModelYear : entity.getAirPodsModelYear(),
          AirPodsWorkTime != null ? AirPodsWorkTime : entity.getAirPodsWorkTime()
        );
        log.info("Информация о планшете {} обновлена.", request.getProductTitle());
        return saveOrUpdate(entity, request);
    }

    public void deleteById(Long productId, AirPodsRequest request) {
        AirPodsEntity entity = airPodsRepository.findById(productId).orElseThrow(() ->
                        new NotFoundException("Наушники с ID " + productId + " не найден.")
        );
        log.info("Наушники {} удалены.", request.getProductTitle());
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
