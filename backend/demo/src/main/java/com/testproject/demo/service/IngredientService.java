package com.testproject.demo.service;

import com.testproject.demo.model.Ingredient;
import com.testproject.demo.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    public void save(String name, double price, double calories, String measure, double amount, String imageUrl){
        ingredientRepository.save(name, price, calories, measure, amount, imageUrl);
    }

    public List<Ingredient> findAllByNameContaining(String name){
        return ingredientRepository.findAllByNameContaining(name);
    };

    public Ingredient findByName(String name){
        return ingredientRepository.findByName(name);
    }

    public List<Ingredient> findAll(){
        return ingredientRepository.findAll();
    };

    public Ingredient findById(int id){
        return ingredientRepository.findById(id);
    }

    public void deleteByName(String name){
        ingredientRepository.deleteByName(name);
    }

    public String storeFile(MultipartFile file) {
        try {
            // Modify this path to suit your needs
            String uploadsDir = "/StajkaPrep/TestWebProject/backend/demo/src/main/resources/ingredient_pictures/";
            Path uploadPath = Paths.get(uploadsDir);

            // Make sure the directory exists
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Get the file's original filename
            String filename = StringUtils.cleanPath(file.getOriginalFilename());

            // Copy the file to the target location (Replacing existing file with the same name)
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String absoluteFilePath = filePath.toAbsolutePath().toString();
            System.out.println("Uploaded file path: " + absoluteFilePath);
            // Return the filename
            return filename;
        } catch (Exception e) {
            // In a real-world application, you would need to do more than just print a stack trace
            e.printStackTrace();
            return null;
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            // Modify this path to suit your needs
            String uploadsDir = "/StajkaPrep/TestWebProject/backend/demo/src/main/resources/ingredient_pictures/";
            Path filePath = Paths.get(uploadsDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found: " + fileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
