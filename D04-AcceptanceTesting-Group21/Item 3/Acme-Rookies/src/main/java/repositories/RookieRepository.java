package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface RookieRepository extends JpaRepository<Rookie, Integer> {

	@Query("select r from Rookie r where r.userAccount.id = ?1")
	Rookie findRookieByUserAccount(int userAccountId);

	@Query("select r from Rookie r where r.userAccount.username=?1")
	Rookie findRookieByUsername(String username);

}
