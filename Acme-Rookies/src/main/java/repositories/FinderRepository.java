
package repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select m.finder from Hacker m where m.id=?1")
	Finder findByHackerId(int hackerId);

	@Query("select f from Position f where (f.ticker like %:keyword%  or f.title like %:keyword% or f.description like %:keyword% or f.skills like %:keyword% or f.profile like %:keyword% or f.tecnologies like %:keyword% ) and (f.deadline BETWEEN :deadlineMin and :deadlineMax) and (f.salary > :minSalary) and f.draftmode=false and (f.cancelled = null or f.cancelled = false)")
	Page<domain.Position> searchPositions(@Param("keyword") String keyword, @Param("deadlineMin") Date deadlineMin, @Param("deadlineMax") Date deadlineMax, @Param("minSalary") double minSalary, Pageable pageable);

}
