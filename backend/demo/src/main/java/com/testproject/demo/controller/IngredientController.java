package com.testproject.demo.controller;

import com.testproject.demo.model.Ingredient;
import com.testproject.demo.service.IngredientService;
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


@CrossOrigin
@RestController
public class IngredientController {

    IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/addIngredient")
    public void addIngredient(@RequestBody Ingredient ingredient){
        ingredientService.save(ingredient.getName(), ingredient.getPrice(), ingredient.getCalories(),
                ingredient.getMeasure(), ingredient.getAmount(), ingredient.getImageUrl());
    }

    @GetMapping("/getIngredient")
    public Ingredient getIngredient(@RequestParam String name){
        return ingredientService.findByName(name);
    }

    @GetMapping("/findAllIngredientsByNameContaining")
    public List<Ingredient> findAllIngredientsByNameContaining(@RequestParam String name){
        return ingredientService.findAllByNameContaining(name);
    }

    @GetMapping("/findById")
    public Ingredient findById(@RequestParam int id){
        return ingredientService.findById(id);
    }

    @GetMapping("/findAllIngredients")
    public List<Ingredient> findAllIngredients(){
        return ingredientService.findAll();
    }

    @PostMapping("/uploadIngredientImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = ingredientService.storeFile(file); // Implement this method to store the file and return its name

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadIngredientImage/")
                .path(fileName)
                .toUriString();

        return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
    }

    @GetMapping("/downloadIngredientImage/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = ingredientService.loadFileAsResource(fileName);

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
