package tpm.qlts.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tpm.qlts.entitys.PhongBan;

@Repository("phongBanRepository")
public interface PhongBanRepository extends CrudRepository<PhongBan, String>{

	@Query("SELECT p FROM PhongBan p ORDER BY p.tenPhongBan DESC")
	List<PhongBan> findAll();
	
	@Query("select p from PhongBan p JOIN p.nhanViens n Join n.nvTbs m Join m.thietBi tb where tb.maThietBi =:maThietBi")
	public PhongBan getByIdThietBi(@Param("maThietBi") long maThietBi);
}
