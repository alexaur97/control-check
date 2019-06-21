
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Remark;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Integer> {

	@Query("select r from Remark r  where r.audit.id=?1")
	Collection<Remark> findByAudit(int auditId);

	@Query("select r from Remark r  where r.audit.id=?1 and r.mode='FINAL'")
	Collection<Remark> findByAuditFinal(int auditId);

	@Query("select r from Remark r  where r.auditor.id=?1")
	Collection<Remark> findByAuditor(int auditorId);
}
