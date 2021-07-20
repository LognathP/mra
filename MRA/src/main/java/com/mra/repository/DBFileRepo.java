package com.mra.repository;

import org.springframework.data.repository.CrudRepository;

import com.mra.model.Category;
import com.mra.model.DBFile;

public interface DBFileRepo extends CrudRepository<DBFile, Long>{

}
