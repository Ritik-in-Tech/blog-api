package com.ritik.blog.services.impl;

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

import com.ritik.blog.services.FileService;


@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		
		String name=file.getOriginalFilename();
		
		String filePath=path+File.separator+ name;
		
		File f=new File(path);
		
		if(!f.exists())
		{
			f.mkdir();
		}
		
		File destinationFile=new File(filePath);
		
		if(destinationFile.exists())
		{
			System.out.println("File already exists: " + name);
			return name;
		}
		
		Files.copy(file.getInputStream(),Paths.get(filePath));
		
		return name;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String fullPath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullPath);
		
		return is;
	}

}
