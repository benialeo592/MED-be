package med.be.peoplemanagement.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.*;

@NoRepositoryBean
public interface CustomJpaConnector<T> extends JpaRepository<T, Long> {

    List<T> findAll(Specification<T> spec);
}
