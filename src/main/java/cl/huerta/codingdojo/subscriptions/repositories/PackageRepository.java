package cl.huerta.codingdojo.subscriptions.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.huerta.codingdojo.subscriptions.models.Package;

@Repository
public interface PackageRepository extends CrudRepository<Package, Long> {
	
	List<Package> findAll();

    Package findByName(String name);
    
    @Query("SELECT p FROM Package p WHERE id <> ?1 AND isAvailable = TRUE")
    List<Package> getAvailablePackages(Long id);
    
}