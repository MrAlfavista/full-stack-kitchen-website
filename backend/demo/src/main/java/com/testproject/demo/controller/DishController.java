package com.testproject.demo.controller;

import com.testproject.demo.model.Dish;
import com.testproject.demo.service.DishService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class DishController {
    DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping("/createDish")
    public int createDish(@RequestBody Dish dish){
        dishService.save(dish.getName(), dish.getRecipe(), dish.getCalories(), dish.getPrice(), dish.getImageUrl());
        return dishService.findByName(dish.getName()).getId();
    }

    @GetMapping("/findAllDishesByNameContaining")
    public List<Dish> findAllDishesByNameContaining(@RequestParam String name){
        return dishService.findAllByNameContaining(name);
    }

    @GetMapping("/getDish")
    public Dish findDish(@RequestParam int id){
        return dishService.findById(id);
    }

    @PostMapping("/uploadDishImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = dishService.storeFile(file); // Implement this method to store the file and return its name

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadDishImage/")
                .path(fileName)
                .toUriString();

        return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
    }

    @GetMapping("/downloadDishImage/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = dishService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // handle exception
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
