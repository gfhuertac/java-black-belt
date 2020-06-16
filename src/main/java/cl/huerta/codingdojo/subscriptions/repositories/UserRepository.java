package cl.huerta.codingdojo.subscriptions.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.huerta.codingdojo.subscriptions.models.Package;
import cl.huerta.codingdojo.subscriptions.models.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	List<User> findAll();

	@Query(
		value="SELECT u.* FROM users u, roles r, users_roles ur WHERE u.id = ur.user_id AND r.id = ur.role_id AND r.name = 'ROLE_USER'",
		nativeQuery = true
	)
	List<User> getCustomers();

    User findByName(String name);
    
    User findByEmail(String email);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u set u.pkg = ?1 WHERE u.id = ?2")
    int setPackageForOne(Package package1, Long id);
    
}