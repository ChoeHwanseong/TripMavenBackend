package com.tripmaven.fileUpload;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class fileController {
	/*
	 @Value("${spring.servlet.multipart.location}")
	 private String saveDirectory;
	 
	  // 파일 업로드 처리
	  @PostMapping(consumes = "multipart/form-data")
	  public ResponseEntity<List<Map<String, Object>>> fileUpload(@RequestPart(name = "files", required = false) List<MultipartFile> files) {
	      try {
	          System.out.println("파일 등록 들어옴");
	          List<Map<String, Object>> filesInfo = uploadFiles(files, saveDirectory);
	          System.out.println("저장된 파일 경로: " + saveDirectory);
	          return ResponseEntity.ok(filesInfo);
	      } catch (Exception e) {
	          e.printStackTrace();
	          return ResponseEntity.status(HttpStatusCode.INTERNAL_SERVER_ERROR).body(null);
	      }
	  }

	  // 파일 업로드 로직
	  public List<Map<String, Object>> uploadFiles(List<MultipartFile> files, String saveDirectory) throws IOException {
	      List<Map<String, Object>> fileInfos = new Vector<>();
	        
	      for (MultipartFile multipartFile : files) {
	          // 새로운 파일 이름 생성 (중복 방지)
	          String systemFilename = FileUtils.getNewFileName(saveDirectory, multipartFile.getOriginalFilename());
	            
	          File f = new File(saveDirectory + File.separator + systemFilename);
	          multipartFile.transferTo(f);

	          // 파일 정보 저장
	          Map<String, Object> map = new HashMap<>();
	          map.put("filename", f.getName());
	          map.put("filesize", (int) Math.ceil(f.length() / 1024.0));
	          map.put("filetype", multipartFile.getContentType());
	          fileInfos.add(map);
	      }

	      return fileInfos;
	  }

	  // 파일 URL 목록 반환 (프론트엔드에서 사용)
	  @GetMapping("/files")
	  public ResponseEntity<List<String>> getFileUrls() {
	      File folder = new File(saveDirectory);
	      File[] listOfFiles = folder.listFiles();
	      List<String> fileUrls = new Vector<>();

	      if (listOfFiles != null) {
	          for (File file : listOfFiles) {
	              if (file.isFile()) {
	                  fileUrls.add("/uploads/" + file.getName());
	              }
	          }
	      }

	      return ResponseEntity.ok(fileUrls);
	  }
	 
	 */

}
