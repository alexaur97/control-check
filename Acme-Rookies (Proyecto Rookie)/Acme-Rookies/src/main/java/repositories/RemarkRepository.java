package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Remark;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Integer>{

	@Query("select r from Remark r where r.mode='FINAL' and r.application.id=?1")
	Collection<Remark> findAllFinal(int id);

	@Query("select r from Remark r where r.application.id=?1")
	Collection<Remark> findAllByApplication(int applicationId);

	@Query("select r from Remark r where r.company.id=?1")
	Collection<Remark> findAllByPrincipal(int id);

	@Query("select r from Remark r where r.application.rookie.id=?1 and r.mode='FINAL'")
	Collection<Remark> findAllByRookie(int id);
	
	
}
