package com.tripmaven.fileUpload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tripmaven.csboard.CSBoardDto;
import com.tripmaven.productboard.ProductBoardDto;
import com.tripmaven.productboard.ProductBoardEntity;
import com.tripmaven.productboard.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class FileUploadController {
		
	//파일 저장위치 주입받기
    @Value("${spring.servlet.multipart.location}")
    private String saveDirectory;
    
    private final FileService fileService;
    private final ProductService productService;
    
    
    //파일 등록
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Map>> fileUpload(
    		@RequestPart(name = "files", required = false) List<MultipartFile> files,
    		@RequestPart(name = "type", required = false) String type){
    	
    	System.out.println(saveDirectory);
        try {

        	List<Map> filesInfo = new Vector<>();
        	if(type != null && type.equals("guidelicense")) {
        		filesInfo = fileService.upload(files, saveDirectory+"/guidelicense");
        	}
        	else if(type == null) {
        		filesInfo = fileService.upload(files, saveDirectory);
        	}

            return ResponseEntity.ok(filesInfo);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
    //파일 조회(상품 id로 조회)
    @GetMapping("/upload/{id}")
    public ResponseEntity<Resource> listFilesByProductId(@PathVariable("id") Long id) {
    	 
        try {
            // 상품 ID로 상품 정보 조회
            ProductBoardDto dto = productService.usersById(id);

            System.out.println("파일 이름: " + dto.getFiles());
            
            String[] filenames = dto.getFiles() != null ? dto.getFiles().split(",") : new String[0]; 
            System.out.println("파일 배열 반환: " + filenames);
            System.out.println("파일 배열 반환[0]: " + filenames[0]);


            if (filenames.length > 0) {
                String filename = filenames[0];
                Path filePath = Paths.get(saveDirectory).resolve(filename).normalize();
                File file = filePath.toFile();
                
                System.out.println("GET 확인할 파일 경로: " + filePath.toString());
                System.out.println("GET 파일 존재 여부: " + file.exists());
                System.out.println("GET 파일 읽기 가능 여부: " + file.canRead());

                // 파일 존재 여부 확인
                if (!file.exists() || !file.isFile()) {
                    System.out.println("파일이 존재하지 않거나 유효한 파일이 아님: " + filePath.toString());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }

                // 파일을 리소스로 반환
                Resource resource = new UrlResource(filePath.toUri());

                if (resource.exists() && resource.isReadable()) {
                    // 파일 이름을 UTF-8로 인코딩
                    String encodedFilename = URLEncoder.encode(resource.getFilename(), "UTF-8").replace("+", "%20");

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename)
                            .body(resource);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (MalformedURLException e) {
            System.err.println("잘못된 파일 경로: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


    