package com.rbs.retail.billing.services;

import com.rbs.retail.billing.dto.CategoryDto;
import com.rbs.retail.billing.response.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryResponse add(CategoryDto request, MultipartFile file);
    List<CategoryResponse> read();
    void delete(String categoryId);
}
