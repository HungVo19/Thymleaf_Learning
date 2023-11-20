package Program.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Program.entity.User;
import Program.repository.IUserRepository;
import Program.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserRepository userRepository;

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Optional<User> findById(Long k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User save(User e) {
		return userRepository.save(e);
	}

	@Override
	public void deleteById(Long k) {
		// TODO Auto-generated method stub
		
	}
	
}
