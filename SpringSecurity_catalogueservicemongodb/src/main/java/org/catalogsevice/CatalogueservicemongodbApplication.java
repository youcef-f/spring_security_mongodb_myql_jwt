package org.catalogsevice;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import org.catalogsevice.dao.ICategoryRepository;
import org.catalogsevice.dao.IProductRepository;
import org.catalogsevice.entities.Category;
import org.catalogsevice.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogueservicemongodbApplication implements CommandLineRunner {

	
	public static void main(String[] args) {
		SpringApplication.run(CatalogueservicemongodbApplication.class, args);
	}

	
	@Autowired
	private IProductRepository productRepository;

	
	@Autowired
	private ICategoryRepository categoryRepository;

	
	@Override
	public void run(String... args) throws Exception {
		
	 //----------------------------------------------------------------

		categoryRepository.deleteAll();
		
		Stream.of("C1 Ordinateur", "C2 Imprimante").forEach(categorie -> {
			categoryRepository.save(new Category(categorie.split(" ")[0],categorie.split(" ")[1],new ArrayList<>()) );
		});

		categoryRepository.findAll().forEach(System.out::println);
		
		 //----------------------------------------------------------------
	
		productRepository.deleteAll();
		
		

		Category categorie1 = categoryRepository.findById("C1").orElse(new Category());
		
		Stream.of("P1", "P2","P3").forEach(prod -> {
			
			Product produitx = productRepository.save(new  Product(null, prod, Math.random()*1000, categorie1)  );
			
			categorie1.getProducts().add(produitx);
			
			categoryRepository.save(categorie1);

		});
		
		
		//----------------------------------------------------------------

			Category categorie2 = categoryRepository.findById("C2").orElse(new Category());
		
		Stream.of("P4", "P5","P6").forEach(prod -> {
			
			Product produity = productRepository.save(new  Product(null, prod, Math.random()*1000, categorie2)  );
			
			categorie2.getProducts().add(produity);
			
			categoryRepository.save(categorie2);
		});	
		
		
		
		productRepository.findAll().forEach(System.out::println);
		
		
	}

}
