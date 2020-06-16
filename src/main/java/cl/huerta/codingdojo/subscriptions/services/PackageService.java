package cl.huerta.codingdojo.subscriptions.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.huerta.codingdojo.subscriptions.models.Package;
import cl.huerta.codingdojo.subscriptions.repositories.PackageRepository;
import cl.huerta.codingdojo.subscriptions.repositories.UserRepository;

@Service
public class PackageService {
    private final PackageRepository packageRepository;

    private PackageService(UserRepository userRepository, PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }
    
    public List<Package> findAll() {
    	return this.packageRepository.findAll();
    }

	public void registerPackage(Package pkg) {
		this.packageRepository.save(pkg);
	}

	public Package findById(Long packageid) {
		Optional<Package> pkg = this.packageRepository.findById(packageid);
		if (pkg.isPresent())
			return pkg.get();
		return null;
	}

	public void remove(Package pkg) {
		this.packageRepository.delete(pkg);
	}

}
