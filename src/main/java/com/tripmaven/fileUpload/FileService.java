package com.tripmaven.fileUpload;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class FileService {
	
	public List<Map> upload(List<MultipartFile> files, String saveDirectory) throws IllegalStateException, IOException{
		List<Map> fileInfos = new Vector<>();
		for(MultipartFile multipartFile:files) {
			//파일객체 생성
			String systemFilename = FileUtils.getNewFileName(saveDirectory, multipartFile.getOriginalFilename());
			File f = new File(saveDirectory + File.separator + systemFilename);
			//업로드
			multipartFile.transferTo(f);
			
			Map<String, Object> map = new HashMap<>();
			map.put("filename", f.getName());
			map.put("filesize", (int)Math.ceil(f.length()/1024.0));
			map.put("filetype", multipartFile.getContentType());
			fileInfos.add(map);
		}
		return fileInfos;
	}
}
