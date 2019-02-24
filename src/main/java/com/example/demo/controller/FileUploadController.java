package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.UploadRepository;
import com.example.demo.model.UploadMetaData;

/* RestController is a special controller used for restful webservices along with response body. It uploads and downloads the single and multiple files.*/
@RestController
public class FileUploadController {
	
	private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	/* Uploaded file will downloads to this folder.*/
	public static String UPLOADED_FOLDER = "C:\\Users\\Nikitha\\Documents\\workspace-spring-tool-suite-4-4.1.0.RELEASE\\springbootfile\\src\\main\\resources\\uploadfile\\";
	
	/*
	 * This object is used to store the metadata of the file into h2 database.
	 */
	@Autowired
	private UploadRepository fileUploadMetaData;

	/*
	 *Single  file will be uploaded
	 *  parameter is uploaded file
	 * request is another file */
	@PostMapping("/fileUpload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile,
			final HttpServletRequest request) {
		
		logger.debug("Single file upload!");
		logger.debug("fileName : " + uploadfile.getOriginalFilename());
		logger.debug("contentType : " + uploadfile.getContentType());
		logger.debug("contentSize : " + uploadfile.getSize());
		
		/* If uploaded file is empty then the response message will be sent by response entity*/
		if (uploadfile.isEmpty()) {
			return new ResponseEntity<String>("please select a file!", HttpStatus.OK);
		}

		try {
			/* Uploaded file will be saved to the system. */
			saveUpload(Arrays.asList(uploadfile));
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Successfully uploaded - " + uploadfile.getOriginalFilename(),
				new HttpHeaders(), HttpStatus.OK);

	}

	/*
	 * It is the Rest endpoint api to get uploaded files 
	 * returns file metadata*/
	@GetMapping("/getFileMetaData")
	public List<UploadMetaData> fileUploadMetaData() {
		List<UploadMetaData> fileMetaData = fileUploadMetaData.findAll();
		return fileMetaData;
	}

	/*
	 * Files will be saved to database
	 * parameter is multiple files
	 */
	private void saveUpload(List<MultipartFile> files) throws IOException {
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue; 
			}
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			saveMetaData(file);

		}

	}

	/* Meta data saves to the database
	 * parameter is uploaded file */
	private void saveMetaData(MultipartFile file) throws IOException {
		UploadMetaData metaData = new UploadMetaData();
		metaData.setName(file.getOriginalFilename());
		metaData.setContentType(file.getContentType());
		metaData.setContentSize(file.getSize());
		fileUploadMetaData.save(metaData);
	}
}