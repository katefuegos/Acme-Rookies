
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.draftmode=false")
	Collection<Position> findFinalMode();

	@Query("select p from Position p where p.draftmode=false and p.company.id = ?1")
	Collection<Position> findFinalByCompany(int companyId);

	@Query("select p from Position p where p.draftmode=true and p.company.id=?1")
	Collection<Position> findDraftByCompany(int companyId);

	@Query("select f from Position f where f.draftmode=false and (f.title like %:keyword% or f.description like %:keyword% or f.skills like %:keyword% or f.profile like %:keyword% or f.tecnologies like %:keyword% or f.company.comercialName like %:keyword%)")
	Collection<Position> search(@Param("keyword") String keyword);

	@Query("select p from Position p where p.company.id =?1")
	Collection<Position> findByCompanyIdSingle(int companyId);

	@Query("select p from Position p where p.draftmode=false and p.cancelled <> true")
	Collection<Position> findFinalNotCancelled();

}
