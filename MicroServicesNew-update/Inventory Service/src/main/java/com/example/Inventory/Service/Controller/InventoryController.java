package com.example.Inventory.Service.Controller;

import com.example.Inventory.Service.Module.InventoryResponse;
import com.example.Inventory.Service.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")

public class InventoryController {
    private final InventoryService inventoryService;
@Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@PathVariable("code") String code) {
return inventoryService.isInStock(code);
    }
}
