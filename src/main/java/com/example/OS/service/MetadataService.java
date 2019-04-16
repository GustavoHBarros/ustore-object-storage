package com.example.OS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.OS.model.Payload;
import com.example.OS.repository.MetadataRepository;

@Service
public class MetadataService {

	@Autowired
	MetadataRepository metadataRepository;

	public List<Payload> getAllFilesMetadata() {
		return metadataRepository.findAll();
	}

	public Payload getFileMetadataById(Long fileId) {
		Optional<Payload> opt = metadataRepository.findById(fileId);
		return opt.isPresent() ? opt.get() : null;
	}

	public Payload save(Payload payload) {
		return metadataRepository.save(payload);
	}

}
