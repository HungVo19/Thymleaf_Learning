package Program.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICoreService<E,K> {
	Page<E> findAll(Pageable pageable);
	Optional<E> findById(K k);
	E save(E e);
	void deleteById(K k);
}
