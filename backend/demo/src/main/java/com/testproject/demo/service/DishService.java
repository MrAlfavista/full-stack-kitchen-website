package com.testproject.demo.service;

import com.testproject.demo.model.Dish;
import com.testproject.demo.repository.DishRepository;
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
public class DishService {

    private  final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    public void save(String name, String recipe, double calories, double price, String imageUrl){
        dishRepository.save(name, recipe, calories, price, imageUrl);
    }

    public void deleteByName(String name){
        dishRepository.deleteByName(name);
    }

    public List<Dish> findAll(){
        return dishRepository.findAll();
    }

    public Dish findById(int id){
        return dishRepository.findById(id);
    }

    public Dish findByName(String name){
        return dishRepository.findByName(name);
    }

    public List<Dish> findAllByNameContaining(String name){
        return dishRepository.findAllByNameContaining(name);
    }

    public String storeFile(MultipartFile file) {
        try {
            // Modify this path to suit your needs
            String uploadsDir = "/StajkaPrep/TestWebProject/backend/demo/src/main/resources/dish_pictures/";
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
            String uploadsDir = "/StajkaPrep/TestWebProject/backend/demo/src/main/resources/dish_pictures/";
            Path filePath = Paths.get(uploadsDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                System.out.println("FOUNDED: " + fileName);
                return resource;
            } else {
                System.out.println("File not found: " + fileName);
                throw new Exception("File not found: " + fileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
