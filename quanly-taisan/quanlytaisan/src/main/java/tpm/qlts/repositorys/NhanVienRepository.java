package tpm.qlts.repositorys;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tpm.qlts.entitys.NhanVien;

@Repository("nhanVienRepository")
public interface NhanVienRepository extends CrudRepository<NhanVien, String>{
	
	@Query("SELECT c FROM NhanVien c where c.tenNhanVien = :tenNhanVien")
	List<NhanVien> findNhanVienByName(@Param("tenNhanVien") String tenNhanVien);
	
	@Query("select n from PhongBan p inner join p.nhanViens n where p.maPhongBan= :maPhongBan")
	public List<NhanVien> getNhanVienRefPhongBan(@Param("maPhongBan") String maPhongBan);
}
