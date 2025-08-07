package com.rbs.retail.billing.impls;

import com.rbs.retail.billing.dto.ItemDto;
import com.rbs.retail.billing.entities.CategoryEntity;
import com.rbs.retail.billing.entities.ItemEntity;
import com.rbs.retail.billing.repositories.CategoryRepository;
import com.rbs.retail.billing.repositories.ItemRespository;
import com.rbs.retail.billing.response.ItemResponse;
import com.rbs.retail.billing.services.FileUploadService;
import com.rbs.retail.billing.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpls implements ItemService {

    private final ItemRespository itemRespository;
    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;


    @Override
    public ItemResponse add(ItemDto request, MultipartFile file) {

        String imgUrl = fileUploadService.uploadFile(file);

        ItemEntity newItem = convertToEntity(request);

        CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id " + request.getCategoryId()));

        newItem.setCategory(existingCategory);
        newItem.setImgUrl(imgUrl);

        newItem = itemRespository.save(newItem);

        return convertToResponse(newItem);
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {

        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .price(newItem.getPrice())
                .description(newItem.getDescription())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryId())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemDto request) {

        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRespository.findAll()
                .stream()
                .map(item -> convertToResponse(item))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {

        ItemEntity existingItem = itemRespository.findByItemId(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with id " + itemId));

        boolean isFileDelete = fileUploadService.deleteFile(existingItem.getImgUrl());
        if(isFileDelete){
            itemRespository.delete(existingItem);
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete the file");
        }
    }
}
