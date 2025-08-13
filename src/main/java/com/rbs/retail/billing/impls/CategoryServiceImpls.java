package com.rbs.retail.billing.impls;

import com.rbs.retail.billing.dto.CategoryDto;
import com.rbs.retail.billing.entities.CategoryEntity;
import com.rbs.retail.billing.repositories.CategoryRepository;
import com.rbs.retail.billing.repositories.ItemRespository;
import com.rbs.retail.billing.response.CategoryResponse;
import com.rbs.retail.billing.services.CategoryService;
import com.rbs.retail.billing.services.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpls implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    private final ItemRespository itemRespository;

    @Override
    public CategoryResponse add(CategoryDto request, MultipartFile file) throws IOException {

        String imgUrl = fileUploadService.uploadFile(file);

//        String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
//
//        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
//
//        Files.createDirectories(uploadPath);
//
//        Path targetLocation = uploadPath.resolve(fileName);
//
//        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//        String imgUrl = "http://localhost:5454/api/v1.0/uploads/" + fileName;

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

//        String imgUrl = exisitingCategory.getImgUrl();
//
//        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
//
//        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
//
//        Path filePath = uploadPath.resolve(fileName);
//
//        try{
//
//            Files.deleteIfExists(filePath);
//            categoryRepository.delete(exisitingCategory);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        categoryRepository.delete(exisitingCategory);

    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {

        Integer itemsCount = itemRespository.countByCategoryId(newCategory.getId());

        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .items(itemsCount)
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
