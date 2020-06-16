package cl.huerta.codingdojo.subscriptions.services;
import java.util.Date;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.huerta.codingdojo.subscriptions.models.Role;
import cl.huerta.codingdojo.subscriptions.models.User;
import cl.huerta.codingdojo.subscriptions.repositories.PackageRepository;
import cl.huerta.codingdojo.subscriptions.repositories.RoleRepository;
import cl.huerta.codingdojo.subscriptions.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PackageRepository packageRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PackageRepository packageRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.packageRepository = packageRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    // register user and hash their password
    public User registerUser(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	user.addRole(roleRepository.findByName("ROLE_USER"));
    	user.setPackage(packageRepository.findByName("Basic"));
    	user.setHiredAt(new Date());
    	return userRepository.save(user);
    }
    
    // register user and hash their password
    public User registerAdmin(User user) {
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	user.addRole(roleRepository.findByName("ROLE_ADMIN"));
    	return userRepository.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    public long countUsers() {
    	return userRepository.count();
    }
    
    public Role findRoleByName(String name) {
    	return this.roleRepository.findByName(name);
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepository.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
}