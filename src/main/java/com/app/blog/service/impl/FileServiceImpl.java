package com.app.blog.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.blog.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//Getting the file name
		String name=file.getOriginalFilename();
		
		//Random name generation
		String randomId=UUID.randomUUID().toString();
		String fileName=randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//Full Path
		String filePath=path +File.separator +fileName;
		
		
		//Creating a folder if not created
		 File f= new File(path);
		 if(!f.exists()) {
			 f.mkdir();
		 }
		 
		 //file copy
		 Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName;
		InputStream is = new FileInputStream(fullPath);
		//we can write  our DB logic here to return InputStream
		
		return is;
	}

}
