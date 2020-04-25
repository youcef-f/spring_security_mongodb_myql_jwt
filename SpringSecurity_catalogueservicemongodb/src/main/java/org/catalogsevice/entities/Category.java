package org.catalogsevice.entities;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	@Id // différent de @id de jpa
	private String id;
	private String name;
	
	@DBRef
	private Collection<Product> products = new ArrayList<>(); // il faut instancier
}
