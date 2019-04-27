
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select p from Problem p where p.position.id = ?1")
	Collection<Problem> findByPositionId(int positionId);
	
	@Query("select p from Problem p where p.position.id = ?1 and p.draftmode=false")
	Collection<Problem> findByPositionIdAndFinal(int positionId);

	@Query("select p from Problem p where p.position.company.id=?1")
	Collection<Problem> findByCompanyId(int companyId);
}
