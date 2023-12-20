package com.ecommerce.product.service.impl;

import com.ecommerce.product.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		String filename = file.getOriginalFilename();
		
//		String randomuid = UUID.randomUUID().toString();
	
//		String newfileName = randomuid.concat(filename.substring(filename.lastIndexOf(".")));
		
		String filePath = path+File.separator+filename;
		
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		File existingFile = new File(filePath);
		if (existingFile.exists()) {
			existingFile.delete();
		}
		Files.copy(file.getInputStream(), Paths.get(filePath),StandardCopyOption.REPLACE_EXISTING);
		
		return filename;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
	     String fullpathString =	path+File.separator+filename;
	     FileInputStream inputStream= new FileInputStream(fullpathString);
		return inputStream;
	}

}
