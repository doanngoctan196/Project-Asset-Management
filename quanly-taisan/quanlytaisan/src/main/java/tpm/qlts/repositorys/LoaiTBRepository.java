package tpm.qlts.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tpm.qlts.entitys.LoaiTB;

@Repository("loaiTBRepository")
public interface LoaiTBRepository extends CrudRepository<LoaiTB, String> {

	@Query("select l from PhongBan p join p.nhanViens n Join n.nvTbs m Join m.thietBi tb Join tb.loaiTb l where p.maPhongBan =:maPhongBan")
	public List<LoaiTB> getAllByIdPhongBan(@Param("maPhongBan") String maPhongBan);

	@Query("select l from PhongBan p JOIN p.nhanViens n Join n.nvTbs m Join m.thietBi tb Join tb.loaiTb l where p.maPhongBan =:maPhongBan and l.maLoai =:maLoai")
	public List<LoaiTB> getAllThietBimaLoai(@Param("maPhongBan") String maPhongBan, @Param("maLoai") String maLoai);

	@Query("select c from LoaiTB c where c.maLoaiCha <> null")
	public List<LoaiTB> getLoaiTBSub();

	@Query("select c from LoaiTB c where c.maLoaiCha = null")
	public List<LoaiTB> getLoaiTBCha();

	@Query(value = "select top 1 ChiMucChu from GeneralLoaiAuto where id = 1", nativeQuery = true)
	public String getChiMucChu();

	@Query(value = "select top 1 ChiMucSo from GeneralLoaiAuto where id = 1", nativeQuery = true)
	public int getChiMucSo();

	@Query(value = "select  * from LoaiTB l1 where l1.MaLoai in (select MaLoai from LoaiTB where MaLoaiCha is null and MaNCC = :MaNCC) ", nativeQuery = true)
	public List<LoaiTB> getAllLoaiTBByNhaCungCap(@Param("MaNCC") String maNCC);
}
