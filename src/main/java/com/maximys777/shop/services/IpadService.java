package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.IpadRequest;
import com.maximys777.shop.dto.response.IpadResponse;
import com.maximys777.shop.entities.IpadEntity;
import com.maximys777.shop.entities.ProductCategoryEnum;
import com.maximys777.shop.globalexceptions.BadRequestException;
import com.maximys777.shop.globalexceptions.NotFoundException;
import com.maximys777.shop.globalmapper.GlobalMapper;
import com.maximys777.shop.repositories.IpadRepository;
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
public class IpadService {
    private final IpadRepository ipadRepository;
    private final CloudinaryService cloudinaryService;

    public IpadResponse create(MultipartFile productImage,
                               String productTitle,
                               String productBrand,
                               String productDescription,
                               BigDecimal productPrice,
                               String productAvailable,
                               ProductCategoryEnum productCategoryEnum,
                               String ipadModel,
                               String ipadColor,
                               String ipadProcessor,
                               Integer ipadCapacity,
                               String ipadOs,
                               Integer ipadDiaonal,
                               Integer ipadModelYear) {
        boolean exists = ipadRepository.findAll()
                .stream()
                .anyMatch(e -> e.getProductTitle().equals(productTitle));
        if (exists) {
            throw new BadRequestException("Такой плашет уже существует");
        }

        String imageUrl = cloudinaryService.uploadFile(productImage, "product/ipads", "image").get("secure_url");
        IpadEntity entity = new IpadEntity();
        IpadRequest request = new IpadRequest(imageUrl, productTitle, productBrand, productDescription, productPrice,
                productAvailable, productCategoryEnum, ipadModel, ipadColor, ipadProcessor, ipadCapacity,
                ipadOs, ipadDiaonal, ipadModelYear);

        IpadResponse response = saveOrUpdate(entity, request);
        log.info("Планшет {} создан.", entity.getProductTitle());
        return response;
    }

    public IpadResponse update(Long productId,
                               MultipartFile productImage,
                               String productTitle,
                               String productBrand,
                               String productDescription,
                               BigDecimal productPrice,
                               String productAvailable,
                               ProductCategoryEnum productCategoryEnum,
                               String ipadModel,
                               String ipadColor,
                               String ipadProcessor,
                               Integer ipadCapacity,
                               String ipadOs,
                               Integer ipadDiagonal,
                               Integer ipadModelYear) {
        IpadEntity entity = ipadRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Планшет не найден."));

        String imageUrl = entity.getProductImage();
        if (productImage != null && !productImage.isEmpty()) {
            String oldImageUrl = entity.getProductImage();
            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                String publicId = cloudinaryService.extractPublicIdFromUrl(oldImageUrl);
                log.info("Удалено старое изображение товара {}", productTitle);
                cloudinaryService.deleteFile(publicId);
            }

            imageUrl = cloudinaryService.uploadFile(productImage, "product/ipads", "image").get("secure_url");
            entity.setProductImage(imageUrl);
        }

        IpadRequest request = new IpadRequest(
                imageUrl,
                productTitle != null ? productTitle : entity.getProductTitle(),
                productBrand != null ? productBrand : entity.getProductBrand(),
                productDescription != null ? productDescription : entity.getProductDescription(),
                productPrice != null ? productPrice : entity.getProductPrice(),
                productAvailable != null ? productAvailable : entity.getProductAvailable(),
                productCategoryEnum != null ? productCategoryEnum : entity.getProductCategoryEnum(),
                ipadModel != null ? ipadModel : entity.getIpadModel(),
                ipadColor != null ? ipadColor : entity.getIpadColor(),
                ipadProcessor != null ? ipadProcessor : entity.getIpadProcessor(),
                ipadCapacity != null ? ipadCapacity : entity.getIpadCapacity(),
                ipadOs != null ? ipadOs : entity.getIpadOs(),
                ipadDiagonal != null ? ipadDiagonal : entity.getIpadDiagonal(),
                ipadModelYear != null ? ipadModelYear : entity.getIpadModelYear()
        );

        log.info("Информация о планшете {} обновлена.", request.getProductTitle());
            return saveOrUpdate(entity, request);
    }

    public void deleteById(Long productId, IpadRequest request) {
        IpadEntity entity = ipadRepository.findById(productId).orElseThrow(() ->
                        new NotFoundException("Планшет с ID " + productId + " не найден.")
        );
        log.info("Планшет {} удален.",  request.getProductTitle());
        ipadRepository.deleteById(productId);
    }

    public IpadResponse getById(Long productId) {
        return ipadRepository.findById(productId)
                .map(GlobalMapper::mapToIpadResponse)
                .orElseThrow(() -> new NotFoundException("Ноутбук не найден."));
    }

    public List<IpadResponse> getAllIpad(int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<IpadEntity> ipadPage =  ipadRepository.findAll(pageRequest);
        return ipadPage.stream()
                .map(GlobalMapper::mapToIpadResponse)
                .collect(Collectors.toList());
    }

    public List<IpadResponse> searchByTitle(String productTitle, int page, int size, String sortBy, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortBy
        );
        Page<IpadEntity> ipadPage = ipadRepository.findByProductTitleContainingIgnoreCase(productTitle, pageRequest);
        return ipadPage.stream()
                .map(GlobalMapper::mapToIpadResponse)
                .collect(Collectors.toList());
    }


    private IpadResponse saveOrUpdate(IpadEntity entity, IpadRequest request) {
        entity.setProductImage(request.getProductImage());
        entity.setProductTitle(request.getProductTitle());
        entity.setProductBrand(request.getProductBrand());
        entity.setProductDescription(request.getProductDescription());
        entity.setProductPrice(request.getProductPrice());
        entity.setProductAvailable(request.getProductAvailable());
        entity.setProductCategoryEnum(request.getProductCategoryEnum());

        entity.setIpadModel(request.getIpadModel());
        entity.setIpadColor(request.getIpadColor());
        entity.setIpadProcessor(request.getIpadProcessor());
        entity.setIpadCapacity(request.getIpadCapacity());
        entity.setIpadOs(request.getIpadOs());
        entity.setIpadDiagonal(request.getIpadDiagonal());
        entity.setIpadModelYear(request.getIpadModelYear());

        return GlobalMapper.mapToIpadResponse(ipadRepository.save(entity));
    }
}
