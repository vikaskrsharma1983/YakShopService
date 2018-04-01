package com.yakshop.net.controller;

import java.io.File;
import java.security.Provider.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yakshop.net.model.FileInfo;
import com.yakshop.net.service.YakShopService;

@Controller
public class YakhshopController {
	private static Logger LOGGER = LoggerFactory.getLogger("YakhshopController.class");
	@Autowired
	YakShopService yakShopService;

	@Value("${server.path}")
	private String FILE_PATH;

	@PostMapping(value = "/uploadYakXml/{timeElapsed}", headers = ("content-type=multipart/*"), consumes = "application/xml")
	public ResponseEntity<FileInfo> uploadYakXml(@RequestParam("file") MultipartFile inputFile,@PathVariable int timeElapsed ) {
		FileInfo fileInfo = new FileInfo();
		HttpHeaders headers = new HttpHeaders();
		if (!inputFile.isEmpty()) {
			try {
				String originalFilename = inputFile.getOriginalFilename();
				File destinationFile = new File(FILE_PATH + File.separator + originalFilename);
				inputFile.transferTo(destinationFile);
				fileInfo.setFileName(destinationFile.getPath());
				fileInfo.setFileSize(inputFile.getSize());
				headers.add("File Uploaded Successfully - ", originalFilename);
				yakShopService.read(destinationFile,timeElapsed);
				fileInfo.setMessage("Data created Successfully");
				return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
			} catch (Exception e) {
				fileInfo.setMessage("Column format,sheet format or column order not supported");
				return new ResponseEntity<FileInfo>(fileInfo, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<FileInfo>(HttpStatus.OK);
		}
	}

}
