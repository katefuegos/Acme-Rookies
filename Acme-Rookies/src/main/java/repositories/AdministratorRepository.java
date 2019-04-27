
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

	//C2 - avg,min,max,stddev of the number of applications per hacker.
	@Query("select avg(1.0 * (select count(e) from Application e where e.hacker.id = b.id)),min(1.0 * (select count(e) from Application e where e.hacker.id = b.id)),max(1.0 * (select count(e) from Application e where e.hacker.id = b.id)),stddev(1.0 * (select count(e) from Application e where e.hacker.id = b.id)) from Hacker b")
	Object[] queryC2();

	//C3 - The companies that have offered more positions.
	@Query("select e.company.id,e.company.comercialName, count(e) from Position e group by e.company order by 3 desc")
	Page<Object[]> queryC3(Pageable pageable);

	//C4 - The hackers who have made more applications.
	@Query("select e.hacker.id,e.hacker.userAccount.username, count(e) from Application e group by e.hacker order by 3 desc")
	Page<Object[]> queryC4(Pageable pageable);

	//C5 - The average, the minimum, the maximum, and the standard deviation of the salaries offered.
	@Query("select avg(p.salary),min(p.salary),max(p.salary),stddev(p.salary) from Position p")
	Object[] queryC5();

	// C6 - The best and the worst position in terms of salary. 
	@Query("select p from Position p where p.salary = (select max(p.salary) from Position p) or p.salary = (select min(p.salary) from Position p) order by p.salary desc")
	Collection<domain.Position> queryC6();

	//	//DASHBOARD B
	//B1 - avg,min,max,stddev of the number of curricula per hacker.
	@Query("select avg(1.0 * (select count(e) from Curricula e where e.hacker.id = b.id and e.copy = false)),min(1.0 * (select count(e) from Curricula e where e.hacker.id = b.id and e.copy = false)),max(1.0 * (select count(e) from Curricula e where e.hacker.id = b.id and e.copy = false)),stddev(1.0 * (select count(e) from Curricula e where e.hacker.id = b.id and e.copy = false)) from Hacker b")
	Object[] queryB1();

	// B2 -
	@Query("select avg(f.positions.size),min(f.positions.size)*1.0,max(f.positions.size)*1.0,stddev(f.positions.size) from Finder f")
	Object[] queryB2();

	// B3
	@Query("select (select count(f1) from Finder f1 where f1.positions.size > 0)*1.0/count(f), (select count(f2) from Finder f2 where f2.positions.size = 0)*1.0/count(f) from Finder f")
	Object[] queryB3();

	//	//C2 - avg,min,max,stddev of the number of result per finder.
	//	@Query("select avg(1.0 * (select count(e) from Application e where e.hacker.id = b.id)),min(1.0 * (select count(e) from Application e where e.hacker.id = b.id)),max(1.0 * (select count(e) from Application e where e.hacker.id = b.id)),stddev(1.0 * (select count(e) from Application e where e.hacker.id = b.id)) from Hacker b")
	//	Object[] queryB2();
	//	//NEW DASHBOARD B
	//
	//	// New B1
	//	@Query("select (select count(b) from Area b where b.chapter.id=null)/(1.0*count(a)) from Area a")
	//	Double queryNewB1();
	//	// New B2
	//	@Query("select avg(1.0 * (select count(e) from Parade e where e.brotherhood.area.chapter.id = b.id)), min(1.0 * (select count(e) from Parade e where e.brotherhood.area.chapter.id = b.id)), max(1.0 * (select count(e) from Parade e where e.brotherhood.area.chapter.id = b.id)), stddev(1.0 * (select count(e) from Parade e where e.brotherhood.area.chapter.id = b.id)) from Chapter b")
	//	Object[] queryNewB2();
	//
	//	// New B3
	//	@Query("select c from Parade p join p.brotherhood.area.chapter c group by c having count(p)> (select avg(1.0 * (select count(e) from Parade e where e.brotherhood.area.chapter.id = b.id)) from Chapter b)")
	//	Collection<domain.Chapter> queryNewB3();
	//
	//	// New B4 - 2 valores [0,1]
	//	@Query("select (select count(b) from Parade b where b.draftMode=true)/(1.0*count(a)),(select count(c) from Parade c where c.draftMode=false)/(1.0*count(a)) from Parade a")
	//	Object[] queryNewB4();
	//
	//	// New B5
	//	@Query("select p.status, (count(p)*1.0)/(1.0*(select count(r) from Parade r)) from Parade p group by p.status")
	//	Collection<Object[]> queryNewB5();
}
