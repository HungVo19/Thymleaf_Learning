package Program.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Program.entity.Category;
import Program.repository.ICategoryRepository;
import Program.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private ICategoryRepository categoryRepository;

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Optional<Category> findById(Long k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category save(Category e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long k) {
		// TODO Auto-generated method stub
		
	}

}
