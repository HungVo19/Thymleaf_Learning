package Program.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Program.entity.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {
	List<Category> findAll();
}
