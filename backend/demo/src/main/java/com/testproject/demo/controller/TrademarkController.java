package com.testproject.demo.controller;

import com.testproject.demo.model.Trademark;
import com.testproject.demo.service.TrademarkService;
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
public class TrademarkController {

    TrademarkService trademarkService;

    @Autowired
    public TrademarkController(TrademarkService trademarkService) {
        this.trademarkService = trademarkService;
    }

    @PostMapping("/addTrademark")
    public void addTrademark(@RequestBody Trademark trademark){
        trademarkService.save(trademark.getName(), trademark.getPrice(), trademark.getCalories(),
                trademark.getMeasure(), trademark.getAmount(), trademark.getImageUrl(), trademark.getIngredient().getId());
    }

    @GetMapping("/getTrademark")
    public Trademark getTrademark(@RequestParam String name){
        return trademarkService.findByName(name);
    }

    @GetMapping("/getIngredientTrademarks")
    public List<Trademark> getIngredientTrademarks(@RequestParam int ingredientId){
        return trademarkService.findAllByIngredientId(ingredientId);
    }

    @GetMapping("/findAllTrademarksByNameContaining")
    public List<Trademark> findAllTrademarksByNameContaining(@RequestParam String name){
        return trademarkService.findAllByNameContaining(name);
    }

    @GetMapping("/findTrademarkById")
    public Trademark findById(@RequestParam int id){
        return trademarkService.findById(id);
    }

    @GetMapping("/findAllTrademarks")
    public List<Trademark> findAllTrademarks(){
        return trademarkService.findAll();
    }

    @PostMapping("/uploadTrademarkImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = trademarkService.storeFile(file); // Implement this method to store the file and return its name

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadTrademarkImage/")
                .path(fileName)
                .toUriString();

        return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
    }

    @GetMapping("/downloadTrademarkImage/{fileName:.+}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = trademarkService.loadFileAsResource(fileName);

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
