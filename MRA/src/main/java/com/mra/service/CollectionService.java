package com.mra.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mra.model.Collections;

@Service
public interface CollectionService {

	List<Collections> getAllCollection();

	Optional<Collections> getCollection(int id);

	boolean addCollection(Collections collection);

	boolean deleteCollection(int id);

	boolean updateCollection(Collections collection);

}
