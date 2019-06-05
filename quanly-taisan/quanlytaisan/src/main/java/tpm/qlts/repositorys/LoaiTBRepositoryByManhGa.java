package tpm.qlts.repositorys;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tpm.qlts.entitys.LoaiTB;

@Repository("loaiTBRepositoryByManhGa")
public interface LoaiTBRepositoryByManhGa extends LoaiTBRepository {
	@Query(value = "select LoaiTB.* from LoaiTB where MaLoai = (select MaLoaiCha from LoaiTB where MaLoai= :MaLoai)", nativeQuery = true)
	public LoaiTB getLoaiChaFromMaLoaiCon(@Param("MaLoai") String maLoai);
}
