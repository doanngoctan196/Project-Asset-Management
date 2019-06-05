package tpm.qlts.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tpm.qlts.entitys.ThietBi;

@Repository("thietBiRepository")
public interface ThietBiRepository extends JpaRepository<ThietBi, Long> {
	@Query(value = "select top 1 MaThietBi from ThietBi order by MaThietBi desc", nativeQuery = true)
	public long getMaxIDThietBi();

	@Query("select tb from PhongBan p JOIN p.nhanViens n Join n.nvTbs m Join m.thietBi tb Join tb.loaiTb l where p.maPhongBan =:maPhongBan and l.maLoai =:maLoai")
	public List<ThietBi> getAllThietBiByID(@Param("maPhongBan") String maPhongBan, @Param("maLoai") String maLoai);

	@Query("select tb from PhongBan p JOIN p.nhanViens n Join n.nvTbs m Join m.thietBi tb where p.maPhongBan =:maPhongBan")
	public List<ThietBi> getAllByIdthietBiPhongBan(@Param("maPhongBan") String maPhongBan);

	@Query(value = "\r\n" + "select ThietBi.* from LoaiTB inner join ThietBi on (LoaiTB.MaLoai = ThietBi.MaLoai)\r\n"
			+ "where MaThietBi in (\r\n" + "	(select MaThietBi from NV_TB where DenNgay is null)\r\n"
			+ "	INTERSECT \r\n"
			+ "	(select NV_TB.MaThietBi from NV_TB inner join NhanVien on (NV_TB.MaNhanVien = NhanVien.MaNhanVien) inner join PhongBan on (NhanVien.MaPhongBan = PhongBan.MaPhongBan)\r\n"
			+ "where PhongBan.MaPhongBan = :maPhongBan))", nativeQuery = true)
	public List<ThietBi> getThietBiIdPhongBan(@Param("maPhongBan") String maPhongBan);

	@Query(value = "select ThietBi.* from LoaiTB inner join ThietBi on (LoaiTB.MaLoai = ThietBi.MaLoai)\r\n"
			+ "where MaThietBi in ((select MaThietBi from NV_TB where DenNgay is null)\r\n" + "INTERSECT \r\n"
			+ "(select NV_TB.MaThietBi from NV_TB inner join NhanVien on (NV_TB.MaNhanVien = NhanVien.MaNhanVien) inner join PhongBan on (NhanVien.MaPhongBan = PhongBan.MaPhongBan) where PhongBan.MaPhongBan =:maPhongBan)\r\n"
			+ "INTERSECT \r\n"
			+ "(select ThietBi.MaThietBi from LoaiTB inner join ThietBi on(LoaiTB.MaLoai = ThietBi.MaLoai) \r\n"
			+ "where LoaiTB.MaLoai =:maLoai))", nativeQuery = true)
	public List<ThietBi> getThietBiIdPhongBanIdLoai(@Param("maPhongBan") String maPhongBan,
			@Param("maLoai") String maLoai);

}
