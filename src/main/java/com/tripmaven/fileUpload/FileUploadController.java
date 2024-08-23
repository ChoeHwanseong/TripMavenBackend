package com.tripmaven.fileUpload;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tripmaven.productboard.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class FileUploadController {
	
	//파일 저장위치 주입받기
    @Value("${spring.servlet.multipart.location}")
    private String saveDirectory;
    private final FileService fileService;
    
    
    //파일 등록
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Map>> fileUpload(@RequestPart(name ="files", required = false) List<MultipartFile> files){
    	System.out.println(saveDirectory);
        try {
            List<Map> filesInfo = fileService.upload(files, saveDirectory);
            return ResponseEntity.ok(filesInfo);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
    
}




    