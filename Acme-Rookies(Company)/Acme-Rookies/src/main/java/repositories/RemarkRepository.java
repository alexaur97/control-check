
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Remark;

@Repository
public interface RemarkRepository extends JpaRepository<Remark, Integer> {

}
