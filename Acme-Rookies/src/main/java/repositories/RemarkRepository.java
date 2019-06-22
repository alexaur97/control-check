
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Remark;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Integer> {

	@Query("select r from Remark r where r.audit.position.company.id=?1")
	Collection<Remark> findByCompany(int id);

	@Query("select r from Remark r where r.mode='FINAL' and r.audit.auditor.id=?1")
	Collection<Remark> findAllFinalMode(int i);

}
