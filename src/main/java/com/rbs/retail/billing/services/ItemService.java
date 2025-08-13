package com.rbs.retail.billing.services;

import com.rbs.retail.billing.dto.ItemDto;
import com.rbs.retail.billing.response.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {

   ItemResponse add(ItemDto request, MultipartFile file) throws IOException;

   List<ItemResponse> fetchItems();

   void deleteItem(String itemId);
}
