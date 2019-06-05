package tpm.qlts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tpm.qlts.custommodels.BanGiaoData;
import tpm.qlts.custommodels.TheoDoiBaoTri;
import tpm.qlts.custommodels.TheoDoiLuanChuyen;
import tpm.qlts.custommodels.ThietBiByLoaiTB;
import tpm.qlts.custommodels.ThietBiChiTiet;
import tpm.qlts.entitys.LoaiTB;
import tpm.qlts.entitys.NhanVienRefThietBi;
import tpm.qlts.entitys.NhanVienRefThietBiPK;
import tpm.qlts.entitys.ThietBi;
import tpm.qlts.entitys.TinhTrangRefThietBi;
import tpm.qlts.services.LoaiThietBiServiceByManhGa;
import tpm.qlts.services.NhanVienRefThietBiService;
import tpm.qlts.services.ThietBiServiceByManhGa;
import tpm.qlts.services.TinhTrangRefThietBiService;
import tpm.qlts.services.TinhTrangService;

@Controller
@RestController
@RequestMapping("thietbi")
public class ThietBiController {

	@Autowired
	private ThietBiServiceByManhGa thietBiServices;

	@Autowired
	private LoaiThietBiServiceByManhGa loaiThietBiService;

	@Autowired
	private TinhTrangService tinhTrangService;

	@Autowired
	private NhanVienRefThietBiService nhanVienRefThietBiService;
	
	@Autowired
	private TinhTrangRefThietBiService tinhTrangRefThietBiService;

	@GetMapping("listtb")
	public List<ThietBi> getalltb() {
		return (List<ThietBi>) thietBiServices.findAll();
	}

	// get thiet bi theo nha cung cap
	@GetMapping("get-tb-by-ncc/{maNCC}/{maLoai}")
	public List<ThietBiByLoaiTB> layThietBiTheoNhaCungCap(@PathVariable String maNCC, @PathVariable String maLoai) {
		List<ThietBiByLoaiTB> lstThietBiGroupByLoai = new ArrayList<ThietBiByLoaiTB>();
		List<ThietBi> lstThietBi = null;
		if (maLoai.equals("none_value")) {
			lstThietBi = thietBiServices.getThietBiByNhaCungCap(maNCC);
		} else {
			lstThietBi = thietBiServices.getThietBiByNCCAndMaLoai(maNCC, maLoai);
		}

		for (ThietBi tb : lstThietBi) {
			LoaiTB loaiTB = loaiThietBiService.getLoaiThietBiFromThietBiCon(tb.getMaLoai());
			LoaiTB loaiTBCon = loaiThietBiService.findByID(tb.getMaLoai());
			ThietBiChiTiet tbs = new ThietBiChiTiet("" + maNCC + loaiTB.getMaLoai() + tb.getMaThietBi(),
					loaiTBCon.getTenLoai(), tb.getBaoHanh(), tb.getGiaTri(), tb.getKhauHao(), tb.getMaLoai(),
					tb.getNgayNhap(), tinhTrangService.getTenTinhTrangByIDThietBi(tb.getMaThietBi()));
			int check = checkInList(lstThietBiGroupByLoai, loaiTB.getMaLoai());
			if (check != -1) {
				lstThietBiGroupByLoai.get(check).getLstThietBi().add(tbs);
			} else {
				ThietBiByLoaiTB newItem = new ThietBiByLoaiTB(loaiTB.getMaLoai(), loaiTB.getTenLoai(),
						new ArrayList<ThietBiChiTiet>());

				newItem.getLstThietBi().add(tbs);
				lstThietBiGroupByLoai.add(newItem);
			}
		}
		return lstThietBiGroupByLoai;
	}

	public int checkInList(List<ThietBiByLoaiTB> lst, String maLoai) {
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).getMaLoaiTB().equals(maLoai)) {
				return i;
			}
		}
		return -1;
	}

	@PostMapping("ban-giao-thiet-bi")
	@Transactional
	public List<NhanVienRefThietBi> banGiaoThietBi(@RequestBody BanGiaoData data) {
		try {
			List<NhanVienRefThietBi> lstNhanVienTB = new ArrayList<NhanVienRefThietBi>();
			for (ThietBiChiTiet tb : data.getLstThietBi()) {
				long maThietBi = Long.parseLong(tb.getMaTB().substring(4));
				NhanVienRefThietBi nvTB = new NhanVienRefThietBi(
						new NhanVienRefThietBiPK(data.getMaNhanVien(), maThietBi, new Date()), new Date(),
						data.getKieuBanGiao().equals("ca_nhan") == true ? true : false);
				lstNhanVienTB.add(nvTB);
			}
			return nhanVienRefThietBiService.updateAll(lstNhanVienTB);
		} catch (Exception e) {
			return null;
		}
	}
	/////theo doi~ luan chuyen thiet bi
	@GetMapping("get-theo-doi-LC-thietbi/{id}")
	public List<TheoDoiLuanChuyen>getbyIDMathietbi(@PathVariable long id)
	{
		List<NhanVienRefThietBi> lstnvftb = nhanVienRefThietBiService.getbyIDMathietbi(id);
		List<TheoDoiLuanChuyen> lstTDLC = new ArrayList<TheoDoiLuanChuyen>();
		for(NhanVienRefThietBi NVrefTB : lstnvftb)
		{
			TheoDoiLuanChuyen tdlc = new TheoDoiLuanChuyen();
				tdlc.setMaThietBi(NVrefTB.getMaThietBi());
				tdlc.setDenNgay(NVrefTB.getDenNgay());
				tdlc.setTuNgay(NVrefTB.getTuNgay());
				tdlc.setTenThietBi(NVrefTB.getThietBi().getLoaiTb().getTenLoai());
				tdlc.setTenPhongBan(NVrefTB.getNhanVien().getPhongBan().getTenPhongBan());
				
				lstTDLC.add(tdlc);
		}
		return lstTDLC;
	}
	///////theo doi~ bao tri` thiet bi
	@GetMapping("get-theo-doi-BT-thietbi/{id}")
	public List<TheoDoiBaoTri>getByMaTBBaoTri(@PathVariable long id)
	{
		List<TinhTrangRefThietBi> lstTTrfTB = tinhTrangRefThietBiService.getByMaTBBaoTri(id);
		List<TheoDoiBaoTri> lstTDBT = new ArrayList<TheoDoiBaoTri>();
		for(TinhTrangRefThietBi TTrfTB : lstTTrfTB)
		{
			TheoDoiBaoTri tdbt = new TheoDoiBaoTri();
			tdbt.setBaoHanh(TTrfTB.getThietBi().getBaoHanh());
			tdbt.setMaPhieuBaoTri(TTrfTB.getPhieuBaoTri().getMaPhieuBaoTri());
			tdbt.setMaThietBi(TTrfTB.getThietBi().getMaThietBi());
			tdbt.setDenNgay(TTrfTB.getDenNgay());
			tdbt.setTuNgay(TTrfTB.getTuNgay());
			
			lstTDBT.add(tdbt);
		}
		return lstTDBT;
	}
}
