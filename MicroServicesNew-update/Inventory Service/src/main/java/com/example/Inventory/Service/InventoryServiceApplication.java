package com.example.Inventory.Service;

import com.example.Inventory.Service.Module.Inventory;
import com.example.Inventory.Service.Repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient


public class InventoryServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
	return args -> {
		Inventory inventory = Inventory.builder()
				.code("IPHONE_13")
				.quantity(100)
				.build();
		Inventory inventory1 = Inventory.builder()
				.quantity(100)
				.code("IPHONE_13_RED")
				.build();
		inventoryRepository.save(inventory);
		inventoryRepository.save(inventory1);
	};
}
}
