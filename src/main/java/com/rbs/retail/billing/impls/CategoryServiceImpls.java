package com.rbs.retail.billing.impls;

import com.rbs.retail.billing.dto.CategoryDto;
import com.rbs.retail.billing.entities.CategoryEntity;
import com.rbs.retail.billing.repositories.CategoryRepository;
import com.rbs.retail.billing.response.CategoryResponse;
import com.rbs.retail.billing.services.CategoryService;
import com.rbs.retail.billing.services.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpls implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    @Override
    public CategoryResponse add(CategoryDto request, MultipartFile file) {

        String imgUrl = fileUploadService.uploadFile(file);

        CategoryEntity newCategory = covertToEntity(request);

        newCategory.setImgUrl(imgUrl);
        newCategory = categoryRepository.save(newCategory);

        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {

        CategoryEntity exisitingCategory = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));

        fileUploadService.deleteFile(exisitingCategory.getImgUrl());
        categoryRepository.delete(exisitingCategory);

    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {

        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .build();
    }

    private CategoryEntity covertToEntity(CategoryDto request) {

        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
}
