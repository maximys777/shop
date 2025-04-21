package com.maximys777.shop.services;

import com.maximys777.shop.dto.request.IpadRequest;
import com.maximys777.shop.dto.response.IpadResponse;
import com.maximys777.shop.entities.IpadEntity;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class IpadService {
    private final IpadRepository ipadRepository;

    public IpadResponse create(IpadRequest request) {
        boolean exists = ipadRepository.findAll()
                .stream()
                .anyMatch(e -> e.getProductTitle().equals(request.getProductTitle())
                && e.getIpadModel().equals(request.getIpadModel()));
        if (exists) {
            throw new BadRequestException("Такой плашет уже существует");
        }

        IpadEntity entity = new IpadEntity();
        log.info("Планшет {} + {} создан.", request.getProductTitle(), request.getIpadModel());
        return saveOrUpdate(entity, request);
    }

    public IpadResponse update(Long productId, IpadRequest request) {
        IpadEntity entity = ipadRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Планшет не найден."));
        log.info("Информация о планшете {} + {} обновлена.", request.getProductTitle(), request.getIpadModel());
            return saveOrUpdate(entity, request);
    }

    public void deleteById(Long productId, IpadRequest request) {
        IpadEntity entity = ipadRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Планшет не найден."));
        log.info("Планшет {} + {} удалены.",  request.getProductTitle(), request.getIpadModel());
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
