package com.example.OS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.example.OS.model.Payload;
import com.example.OS.service.MetadataService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@RestController
@RequestMapping("/file")
public class ObjectStorageController {
	private GridFsTemplate gridFsTemplate;

	@Autowired
	private MetadataService metadataService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Payload>> findAllMetadata() {
		List<Payload> fileMetadata = metadataService.getAllFilesMetadata();
		return ResponseEntity.ok().body(fileMetadata);
	}

	@RequestMapping(value = "/metadata/{id}", method = RequestMethod.GET)
	public ResponseEntity<Payload> findMetadata(@PathVariable Long id) {
		
		Payload fileMetadata = metadataService.getFileMetadataById(id);
		return ResponseEntity.ok().body(fileMetadata);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Payload> find(@PathVariable Long id) {
		Payload fileMetadata = metadataService.getFileMetadataById(id);
		try {
			GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("metadata.id").is(id.toString())));
			byte[] bytes = this.gridFsTemplate.getResource(gridFSFile).getInputStream().readAllBytes();
			fileMetadata.setContent(bytes);
			return new ResponseEntity<>(fileMetadata, HttpStatus.OK);
		} catch (IOException e) {
				e.printStackTrace();
		}
		return null;
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody Payload payload) {
		InputStream inputStream = new ByteArrayInputStream(payload.getContent());
		DBObject metaData = new BasicDBObject();
		metaData.put("id", payload.getId());
		metaData.put("userId", payload.getUserId());
		metadataService.save(payload);
		gridFsTemplate.store(inputStream, metaData).toString();

		return new ResponseEntity<>("File saved successfully", HttpStatus.OK);
	}

}
