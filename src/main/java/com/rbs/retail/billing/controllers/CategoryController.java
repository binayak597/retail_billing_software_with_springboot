package com.rbs.retail.billing.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbs.retail.billing.dto.CategoryDto;
import com.rbs.retail.billing.response.CategoryResponse;
import com.rbs.retail.billing.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryResponse> addCategory(@RequestPart("category") String categoryString, @RequestPart("file") MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        CategoryDto request = null;

        try{
            request = objectMapper.readValue(categoryString, CategoryDto.class);

            CategoryResponse response = categoryService.add(request, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occured while parsing the json: " + ex.getMessage());
        }


    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> fetchCategories(){

        List<CategoryResponse> data = categoryService.read();

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Void> remove(@PathVariable String categoryId){

        try{
            categoryService.delete(categoryId);
            return ResponseEntity.noContent().build(); //HTTP 204
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }
}
