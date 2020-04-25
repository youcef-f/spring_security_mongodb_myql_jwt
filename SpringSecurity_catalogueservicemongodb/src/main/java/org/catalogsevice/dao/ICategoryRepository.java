package org.catalogsevice.dao;

import org.catalogsevice.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ICategoryRepository extends MongoRepository<Category, String>  {

}
