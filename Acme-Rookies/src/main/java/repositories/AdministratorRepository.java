
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select h from Administrator h where h.userAccount.id = ?1")
	Administrator findAdministratorByUserAccount(int userAccountId);

	@Query("select a from Actor a where a.userAccount.username=?1")
	Administrator findAdminByUsername(String username);

	//DASHBOARD C
	//C1 - avg,min,max,stddev of the number of position per company.
	@Query("select avg(1.0 * (select count(e) from Position e where e.company.id = b.id)),min(1.0 * (select count(e) from Position e where e.company.id = b.id)),max(1.0 * (select count(e) from Position e where e.company.id = b.id)),stddev(1.0 * (select count(e) from Position e where e.company.id = b.id)) from Company b")
	Object[] queryC1();

	//C2 - avg,min,max,stddev of the number of applications per rookie.
	@Query("select avg(1.0 * (select count(e) from Application e where e.rookie.id = b.id)),min(1.0 * (select count(e) from Application e where e.rookie.id = b.id)),max(1.0 * (select count(e) from Application e where e.rookie.id = b.id)),stddev(1.0 * (select count(e) from Application e where e.rookie.id = b.id)) from Rookie b")
	Object[] queryC2();

	//C3 - The companies that have offered more positions.
	@Query("select e.company.id,e.company.comercialName, count(e) from Position e group by e.company order by 3 desc")
	Page<Object[]> queryC3(Pageable pageable);

	//C4 - The rookies who have made more applications.
	@Query("select e.rookie.id,e.rookie.userAccount.username, count(e) from Application e group by e.rookie order by 3 desc")
	Page<Object[]> queryC4(Pageable pageable);

	//C5 - The average, the minimum, the maximum, and the standard deviation of the salaries offered.
	@Query("select avg(p.salary),min(p.salary),max(p.salary),stddev(p.salary) from Position p")
	Object[] queryC5();

	// C6 - The best and the worst position in terms of salary. 
	@Query("select p from Position p where p.salary = (select max(p.salary) from Position p) or p.salary = (select min(p.salary) from Position p) order by p.salary desc")
	Collection<domain.Position> queryC6();

	//	//DASHBOARD B
	//B1 - avg,min,max,stddev of the number of curricula per rookie.
	@Query("select avg(1.0 * (select count(e) from Curricula e where e.rookie.id = b.id and e.copy = false)),min(1.0 * (select count(e) from Curricula e where e.rookie.id = b.id and e.copy = false)),max(1.0 * (select count(e) from Curricula e where e.rookie.id = b.id and e.copy = false)),stddev(1.0 * (select count(e) from Curricula e where e.rookie.id = b.id and e.copy = false)) from Rookie b")
	Object[] queryB1();

	// B2 -
	@Query("select avg(f.positions.size),min(f.positions.size)*1.0,max(f.positions.size)*1.0,stddev(f.positions.size) from Finder f")
	Object[] queryB2();

	// B3
	@Query("select (select count(f1) from Finder f1 where f1.positions.size > 0)*1.0/count(f), (select count(f2) from Finder f2 where f2.positions.size = 0)*1.0/count(f) from Finder f")
	Object[] queryB3();

	// ACME - ROOKIE 
	//DASHBOARD C
	//C1 - 
	@Query("select avg(a.score)*1.0, min(a.score)*1.0, max(a.score)*1.0, stddev(a.score)*1.0 from Audit a")
	Object[] queryNewC1();

	//C2 - 
	@Query("select avg(a.auditScore)*1.0, min(a.auditScore)*1.0, max(a.auditScore)*1.0, stddev(a.auditScore)*1.0 from Company a")
	Object[] queryNewC2();

	//C3 -
	@Query("select c from Company c where c.auditScore = (select max(a.auditScore) from Company a)")
	domain.Company queryNewC3();

	//C4 - 
	@Query("select avg(a.position.salary)*1.0 from  Audit a where a.score > (select avg(t.score) from Audit t)")
	Double queryNewC4();

	//	//DASHBOARD B
	//B1 - 
	@Query("select avg(1.0 * (select count(e) from Item e where e.provider.id = b.id )),min(1.0 * (select count(e) from Item e where e.provider.id = b.id)),max(1.0 * (select count(e) from Item e where e.provider.id = b.id )),stddev(1.0 * (select count(e) from Item e where e.provider.id = b.id)) from Provider b")
	Object[] queryNewB1();

	//B2 - 
	@Query("select e.provider.id,e.provider.userAccount.username, count(e) from Item e group by e.provider order by 3 desc")
	Page<Object[]> queryNewB2(Pageable pageable);
}
