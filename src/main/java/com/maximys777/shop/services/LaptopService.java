package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.LaptopRequest;
import com.maximys777.shop.dto.response.LaptopResponse;
import com.maximys777.shop.entities.LaptopEntity;
import com.maximys777.shop.entities.ProductCategoryEnum;
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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class LaptopService {
    private final LaptopRepository laptopRepository;
    private final CloudinaryService cloudinaryService;

    public LaptopResponse create(MultipartFile productImage,
                                 String productTitle,
                                 String productBrand,
                                 String productDescription,
                                 BigDecimal productPrice,
                                 String productAvailable,
                                 ProductCategoryEnum productCategoryEnum,
                                 String laptopModel,
                                 Double laptopDiagonal,
                                 String laptopProcessor,
                                 String laptopOs,
                                 Integer laptopRam,
                                 String laptopStorage,
                                 String laptopGraphCard,
                                 String laptopColor,
                                 String laptopBattery,
                                 Integer laptopModelYear) {
        boolean exists = laptopRepository.findAll()
                .stream()
                .anyMatch(e -> e.getProductTitle().equals(productTitle));
        if (exists) {
            throw new BadRequestException("Такой ноутбук уже существует.");
        }

        String imageUrl = cloudinaryService.uploadFile(productImage, "product/laptops", "image").get("secure_url");
        LaptopEntity entity = new LaptopEntity();
        LaptopRequest request = new LaptopRequest(imageUrl, productTitle, productBrand, productDescription, productPrice,
                productAvailable, productCategoryEnum, laptopModel, laptopDiagonal, laptopProcessor, laptopOs,
                laptopRam, laptopStorage, laptopGraphCard, laptopColor, laptopBattery, laptopModelYear);

        LaptopResponse response = saveOrUpdate(entity, request);
        log.info("Ноутбук {} создан.", entity.getProductTitle());
        return response;
    }

    public LaptopResponse update(Long productId,
                                 MultipartFile productImage,
                                 String productTitle,
                                 String productBrand,
                                 String productDescription,
                                 BigDecimal productPrice,
                                 String productAvailable,
                                 ProductCategoryEnum productCategoryEnum,
                                 String laptopModel,
                                 Double laptopDiagonal,
                                 String laptopProcessor,
                                 String laptopOs,
                                 Integer laptopRam,
                                 String laptopStorage,
                                 String laptopGraphCard,
                                 String laptopColor,
                                 String laptopBattery,
                                 Integer laptopModelYear) {
        LaptopEntity entity = laptopRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Ноутбук не найден."));

        String imageUrl = entity.getProductImage();
        if (productImage != null && !productImage.isEmpty()) {
            String oldImageUrl = entity.getProductImage();
            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                String publicId = cloudinaryService.extractPublicIdFromUrl(oldImageUrl);
                log.info("Удалено старое изображение товара {}", productTitle);
                cloudinaryService.deleteFile(publicId);
            }

            imageUrl = cloudinaryService.uploadFile(productImage, "product/laptops", "image").get("secure_url");
            entity.setProductImage(imageUrl);
        }

        LaptopRequest request = new LaptopRequest(
                imageUrl,
                productTitle != null ? productTitle : entity.getProductTitle(),
                productBrand != null ? productBrand : entity.getProductBrand(),
                productDescription != null ? productDescription : entity.getProductDescription(),
                productPrice != null ? productPrice : entity.getProductPrice(),
                productAvailable != null ? productAvailable : entity.getProductAvailable(),
                productCategoryEnum != null ? productCategoryEnum : entity.getProductCategoryEnum(),
                laptopModel != null ? laptopModel : entity.getLaptopModel(),
                laptopDiagonal != null ? laptopDiagonal : entity.getLaptopDiagonal(),
                laptopProcessor != null ? laptopProcessor : entity.getLaptopProcessor(),
                laptopOs != null ? laptopOs : entity.getLaptopOs(),
                laptopRam != null ? laptopRam : entity.getLaptopRam(),
                laptopStorage != null ? laptopStorage : entity.getLaptopStorage(),
                laptopGraphCard != null ? laptopGraphCard : entity.getLaptopGraphCard(),
                laptopColor != null ? laptopColor : entity.getLaptopColor(),
                laptopBattery != null ? laptopBattery : entity.getLaptopBattery(),
                laptopModelYear != null ? laptopModelYear : entity.getLaptopModelYear()
        );
        log.info("Информация о ноутбуке {} обновлена.", request.getProductTitle());
        return saveOrUpdate(entity, request);
}

    public LaptopResponse getById(Long productId) {
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

    public void deleteById(Long productId) {
        LaptopEntity entity = laptopRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Ноутбук с ID " + productId + " не найден.")
        );
        log.info("Ноутбук {} удален.", entity.getProductTitle());
        laptopRepository.deleteById(productId);
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
