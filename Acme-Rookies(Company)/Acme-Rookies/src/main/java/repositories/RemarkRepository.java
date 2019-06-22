
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Remark;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Integer> {

	@Query("select r from Remark r where r.audit.position.company=?1")
	Collection<Remark> findByCompany(int id);

}
