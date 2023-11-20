package Program.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Program.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	@Query(nativeQuery = true, value ="select * from user" )
	Page<User> findAllUser (Pageable pageable);
}
 
