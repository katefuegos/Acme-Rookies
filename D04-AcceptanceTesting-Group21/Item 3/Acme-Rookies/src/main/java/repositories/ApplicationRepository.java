
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.position.company.id=?1")
	Collection<Application> findByCompanyId(int companyId);

	@Query("select a from Application a where a.rookie.id=?1")
	Collection<Application> findByRookieId(int rookieId);

	@Query("select a from Application a where a.rookie.id=?1 and a.status = 'PENDING'")
	Collection<Application> findPendingByRookieId(int rookieId);

	@Query("select a from Application a where a.rookie.id=?1 and a.status = 'REJECTED'")
	Collection<Application> findRejectedByRookieId(int rookieId);

	@Query("select a from Application a where a.rookie.id=?1 and a.status = 'ACCEPTED'")
	Collection<Application> findAcceptedByRookieId(int rookieId);

	@Query("select a from Application a where a.rookie.id=?1 and a.status = 'SUBMITTED'")
	Collection<Application> findSubmittedByRookieId(int rookieId);

}
