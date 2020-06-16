package cl.huerta.codingdojo.subscriptions.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.huerta.codingdojo.subscriptions.models.Package;
import cl.huerta.codingdojo.subscriptions.models.User;
import cl.huerta.codingdojo.subscriptions.repositories.PackageRepository;
import cl.huerta.codingdojo.subscriptions.repositories.UserRepository;

@Service
public class CustomerService {
    private final UserRepository userRepository;
    private final PackageRepository packageRepository;

    private CustomerService(UserRepository userRepository, PackageRepository packageRepository) {
        this.userRepository = userRepository;
        this.packageRepository = packageRepository;
    }

    public List<User> findAll() {
    	return this.userRepository.getCustomers();
    }
    
    public User findCustomerById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            User user = u.get();
            if (user.getRoles().stream().anyMatch(r -> "ROLE_USER".equals(r.getName())))
            	return user;
    	}
        return null;
    }
    
    public void setPackage(User user, Long package_id) {
    	Optional<Package> pkg = this.packageRepository.findById(package_id);
    	if (pkg.isPresent()) {
    		this.userRepository.setPackageForOne(pkg.get(), user.getId());
    	}
    }
    
    public List<Package> findAvailablePackages(User user) {
    	return this.packageRepository.getAvailablePackages(user.getPackage().getId());
    }

}
