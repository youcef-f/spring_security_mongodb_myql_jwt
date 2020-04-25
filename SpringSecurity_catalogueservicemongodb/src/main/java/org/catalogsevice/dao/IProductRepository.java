package org.catalogsevice.dao;

import org.catalogsevice.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface IProductRepository  extends MongoRepository<Product, String>{

}
