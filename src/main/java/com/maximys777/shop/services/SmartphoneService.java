package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.SmartphoneRequest;
import com.maximys777.shop.dto.response.SmartphoneResponse;
import com.maximys777.shop.entities.ProductCategoryEnum;
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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;
    private final CloudinaryService cloudinaryService;

    public SmartphoneResponse create(MultipartFile productImage,
                                     String productTitle,
                                     String productBrand,
                                     String productDescription,
                                     BigDecimal productPrice,
                                     String productAvailable,
                                     ProductCategoryEnum productCategoryEnum,
                                     String smartphoneModel,
                                     String smartphoneOs,
                                     Integer smartphoneStorage,
                                     Integer smartphoneRam,
                                     String smartphoneColor,
                                     Integer batteryCapacity,
                                     String batteryUnit,
                                     Integer smartphoneModelYear) {

        boolean exists = smartphoneRepository.findAll()
                .stream()
                .anyMatch(e -> e.getProductTitle().equals(productTitle));
        if (exists) {
            throw new BadRequestException("Такой телефон уже существует.");
        }

        String imageUrl = cloudinaryService.uploadFile(productImage, "product/smartphones", "image").get("secure_url");

        SmartphoneEntity entity = new SmartphoneEntity();
        SmartphoneRequest request = new SmartphoneRequest(imageUrl, productTitle, productBrand, productDescription, productPrice,
                productAvailable, productCategoryEnum, smartphoneModel, smartphoneOs,
                smartphoneStorage, smartphoneRam, smartphoneColor, batteryCapacity,
                batteryUnit, smartphoneModelYear);

        SmartphoneResponse response = saveOrUpdate(entity, request);

        log.info("Телефон {} создан.", entity.getProductTitle());
        return response;
    }

    public SmartphoneResponse update(Long productId,
                                     MultipartFile productImage,
                                     String productTitle,
                                     String productBrand,
                                     String productDescription,
                                     BigDecimal productPrice,
                                     String productAvailable,
                                     ProductCategoryEnum productCategoryEnum,
                                     String smartphoneModel,
                                     String smartphoneOs,
                                     Integer smartphoneStorage,
                                     Integer smartphoneRam,
                                     String smartphoneColor,
                                     Integer batteryCapacity,
                                     String batteryUnit,
                                     Integer smartphoneModelYear) {
        SmartphoneEntity entity = smartphoneRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Телефон не найден."));

        String imageUrl = entity.getProductImage();

        if (productImage != null && !productImage.isEmpty()) {
            String oldImageUrl = entity.getProductImage();
            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                String publicId = cloudinaryService.extractPublicIdFromUrl(oldImageUrl);
                log.info("Удалено старое изображение товара {}", productTitle);
                cloudinaryService.deleteFile(publicId);
            }

            imageUrl = cloudinaryService.uploadFile(productImage, "product/smartphones", "image").get("secure_url");
            entity.setProductImage(imageUrl);
        }

        SmartphoneRequest request = new SmartphoneRequest(
                imageUrl,
                productTitle != null ? productTitle : entity.getProductTitle(),
                productBrand != null ? productBrand : entity.getProductBrand(),
                productDescription != null ? productDescription : entity.getProductDescription(),
                productPrice != null ? productPrice : entity.getProductPrice(),
                productAvailable != null ? productAvailable : entity.getProductAvailable(),
                productCategoryEnum != null ? productCategoryEnum : entity.getProductCategoryEnum(),
                smartphoneModel != null ? smartphoneModel : entity.getSmartphoneModel(),
                smartphoneOs != null ? smartphoneOs : entity.getSmartphoneOs(),
                smartphoneStorage != null ? smartphoneStorage : entity.getSmartphoneStorage(),
                smartphoneRam != null ? smartphoneRam : entity.getSmartphoneRam(),
                smartphoneColor != null ? smartphoneColor : entity.getSmartphoneColor(),
                batteryCapacity != null ? batteryCapacity : entity.getBatteryCapacity(),
                batteryUnit != null ? batteryUnit : entity.getBatteryUnit(),
                smartphoneModelYear != null ? smartphoneModelYear : entity.getSmartphoneModelYear()
        );

        log.info("Информация о телефоне {} обновлена.", request.getProductTitle());
        return saveOrUpdate(entity, request);
    }

    public void deleteById(Long productId) {
        SmartphoneEntity entity = smartphoneRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Смартфон с ID " + productId + " не найден.")
        );
        log.info("Телефон {} удален.", entity.getProductTitle());
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
