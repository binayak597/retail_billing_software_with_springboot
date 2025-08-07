package com.rbs.retail.billing.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbs.retail.billing.dto.ItemDto;
import com.rbs.retail.billing.response.ItemResponse;
import com.rbs.retail.billing.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/admin/items")
    public ResponseEntity<ItemResponse> addItem(@RequestPart("item") String itemString,

                                                @RequestPart("file") MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        ItemDto itemRequest = null;
        try{
            itemRequest = objectMapper.readValue(itemString, ItemDto.class);

            ItemResponse response = itemService.add(itemRequest, file);

            System.out.println(response.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occured while parsing the json data");
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> readItems(){

        List<ItemResponse> responseList = itemService.fetchItems();

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @DeleteMapping("/admin/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String itemId){

        try{
            itemService.deleteItem(itemId);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
    }
}
