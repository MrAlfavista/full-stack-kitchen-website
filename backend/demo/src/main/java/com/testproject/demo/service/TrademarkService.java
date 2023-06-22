package com.testproject.demo.service;

import com.testproject.demo.model.Ingredient;
import com.testproject.demo.model.Trademark;
import com.testproject.demo.repository.IngredientRepository;
import com.testproject.demo.repository.TrademarkRepository;
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
public class TrademarkService {
    private final TrademarkRepository trademarkRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public TrademarkService(TrademarkRepository trademarkRepository, IngredientRepository ingredientRepository){
        this.trademarkRepository = trademarkRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public void save(String name, double price, double calories, String measure, double amount, String imageUrl, int ingredientId){
        trademarkRepository.save(name, price, calories, measure, amount, imageUrl, ingredientId);
    }

    public List<Trademark> findAllByNameContaining(String name){
        return trademarkRepository.findAllByNameContaining(name);
    };

    public List<Trademark> findAllByIngredient(Ingredient ingredient){
        return trademarkRepository.findAllByIngredient(ingredient);
    }

    public List<Trademark> findAllByIngredientId(int ingredientId){
        return trademarkRepository.findAllByIngredient(ingredientRepository.findById(ingredientId));
    }

    public Trademark findCheapestByNameContaining(String name){
        List<Trademark> list = findAllByNameContaining(name);
        if(list.isEmpty()) return null;
        Trademark res = list.get(0);
        for(Trademark i: list){
            if(res.getPrice() > i.getPrice()){
                res = i;
            }
        }
        return res;
    }

    public Trademark findCheapestByIngredient(Ingredient ingredient){
        List<Trademark> list = trademarkRepository.findAllByIngredient(ingredient);
        if(list.isEmpty()) return null;
        Trademark res = list.get(0);
        for(Trademark i: list){
            if(res.getPrice() > i.getPrice()){
                res = i;
            }
        }
        return res;
    }

    public Trademark findByName(String name){
        return trademarkRepository.findByName(name);
    }

    public List<Trademark> findAll(){
        return trademarkRepository.findAll();
    };

    public Trademark findById(int id){
        return trademarkRepository.findById(id);
    }

    public void deleteByName(String name){
        trademarkRepository.deleteByName(name);
    }

    public String storeFile(MultipartFile file) {
        try {
            // Modify this path to suit your needs
            String uploadsDir = "/StajkaPrep/TestWebProject/backend/demo/src/main/resources/trademark_pictures/";
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
            String uploadsDir = "/StajkaPrep/TestWebProject/backend/demo/src/main/resources/trademark_pictures/";
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
