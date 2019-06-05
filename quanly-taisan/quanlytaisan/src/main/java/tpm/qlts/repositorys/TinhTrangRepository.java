package tpm.qlts.repositorys;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tpm.qlts.entitys.TinhTrang;

@Repository("tinhTrangRepository")
public interface TinhTrangRepository extends CrudRepository<TinhTrang, String> {

	@Query(value = "select dbo.checkTinhTrang(:idThietBi) as TinhTrang", nativeQuery = true)
	public String getTenTinhTrangByIDThietBi(@Param("idThietBi") long idThietBi);
}