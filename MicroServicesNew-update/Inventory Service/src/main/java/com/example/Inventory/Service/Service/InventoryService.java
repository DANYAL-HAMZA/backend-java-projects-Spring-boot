package com.example.Inventory.Service.Service;

import java.lang.String;
import java.util.List;

import com.example.Inventory.Service.Module.Inventory;
import com.example.Inventory.Service.Module.InventoryResponse;
import com.example.Inventory.Service.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class InventoryService {
    private final InventoryRepository inventoryRepository;
@Autowired
    public InventoryService(InventoryRepository inventoryRepository) {

    this.inventoryRepository = inventoryRepository;
    }
@Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(String code) {

    return inventoryRepository.findByCode( code).stream().map(inventory ->
        InventoryResponse.builder()
                .code(inventory.getCode())
                .isInStock(inventory.getQuantity() > 0)
                .build()
    ).toList();
}

}
