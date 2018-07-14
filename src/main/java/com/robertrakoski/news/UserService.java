package com.robertrakoski.news;

import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserService {

	@Autowired
	UserRepository userRepository;
	
	User getById(Long id) {
		checkForExisting(id);
		Optional<User> user = userRepository.findById(id);
		return user.get();
	}
	
	User create(User user) {
		if(user.getId() != null) 
			throw new EntityExistsException("The id attribute must be null to persist a new entity.");
		
		if(user.getPassword().length() < 6)
			throw new IllegalArgumentException("The username should be at least 6 characters long");
		if(user.getUsername().length() > 32)
			throw new IllegalArgumentException("The username should be no longer than 32");
		
		if(user.getPassword().length() < 6)
			throw new IllegalArgumentException("The password should be at least 6 characters long");
		if(user.getPassword().length() > 32)
			throw new IllegalArgumentException("The password should be no longer than 32");
		
		return userRepository.save(user);
	}
	
//	// for password change - needs to be implemented later
//	User update(User user) {
//		checkForExisting(user.getId());
//		return userRepository.save(user);
//	}
//	
//	// for removing account - needs to be implemented later
//	void delete(Long id) {
//		checkForExisting(id);
//		userRepository.deleteById(id);
//	}
	
	private void checkForExisting(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			throw new EntityNotFoundException("There is no user with specified id.");
	}
}
