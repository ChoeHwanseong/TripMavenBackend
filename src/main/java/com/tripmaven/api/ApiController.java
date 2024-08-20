package com.tripmaven.api;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost")
public class ApiController {

    private final ApiService apiService;

    @PostMapping("/ocr")
    public ResponseEntity<String> processOCR(@RequestParam Map<String, String> map) {
    	System.out.println("ocr 컨트롤러 진입");
        String ocrResult = apiService.callFastAPIOCRService(map);
        return ResponseEntity.ok(ocrResult);
    }

    @PostMapping("/stt")
    public ResponseEntity<String> processSTT(@RequestParam String filePath) {
        String sttResult = apiService.callFastAPISTTService(filePath);
        return ResponseEntity.ok(sttResult);
    }
}
