package tpm.qlts.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import tpm.qlts.comporator.PhieuBaoTriComporator;
import tpm.qlts.custommodels.BaoTriData;
import tpm.qlts.custommodels.ChiTietPhieuBaoTri;
import tpm.qlts.custommodels.ChiTietThietBi;
import tpm.qlts.entitys.NhanVien;
import tpm.qlts.entitys.PhieuBaoTri;
import tpm.qlts.entitys.ThietBi;
import tpm.qlts.entitys.TinhTrang;
import tpm.qlts.entitys.TinhTrangRefThietBi;
import tpm.qlts.entitys.TinhTrangRefThietBiPK;
import tpm.qlts.services.NhanVienService;
import tpm.qlts.services.PhieuBaoTriService;
import tpm.qlts.services.ThietBiServices;
import tpm.qlts.services.TinhTrangRefThietBiService;
import tpm.qlts.services.TinhTrangServiceByNam;

@Controller
@RestController
@RequestMapping("baotri")
public class BaoTriThietBiController {

	@Autowired
	private TinhTrangServiceByNam tinhTrangService;

	@Autowired
	private ThietBiServices thietBiService;

	@Autowired
	private PhieuBaoTriService phieuBaoTriService;

	@Autowired
	private TinhTrangRefThietBiService tinhTrangRefThietBiService;

	@Autowired
	private NhanVienService nhanVienService;

	@GetMapping("get-all-tinhtrang")
	public List<TinhTrang> getALlTinhTrang() {
		return tinhTrangService.findAll();
	}

	@GetMapping("lay-tt-thiet-bi/{key}")
	public ChiTietThietBi LayThongTinThietBi(@PathVariable long key) {
		if (thietBiService.checkExisted(key) == true) {
			ThietBi tb = thietBiService.findById(key);

			ChiTietThietBi ctTB = new ChiTietThietBi(tb.getLoaiTb().getTenLoai(), tb.getMaThietBi(), tb.getBaoHanh(),
					tb.getGiaTri(), tb.getKhauHao(), tb.getNgayNhap(),
					tinhTrangService.getMaTinhTrangByIDThietBi(tb.getMaThietBi()),
					tinhTrangService.getTenTinhTrangByIDThietBi(tb.getMaThietBi()));
			return ctTB;
		} else {
			return null;
		}
	}

	@PostMapping("lap-danh-sach-baotri")
	@Transactional
	public List<TinhTrangRefThietBi> lapDanhSachThietBiBaoTri(@RequestBody BaoTriData data) {
		PhieuBaoTri phieuBaoTri = phieuBaoTriService.save(new PhieuBaoTri("cho_sua_chua"));
		List<TinhTrangRefThietBi> newLstTTTBs = new ArrayList<TinhTrangRefThietBi>();

		if (data.getLstThietBi().size() > 0) {
			for (ChiTietThietBi tb : data.getLstThietBi()) {

				if (tinhTrangRefThietBiService.checkTinhTrangTB(tb.getMaThietBi(), "TT02")) {

					newLstTTTBs.add(new TinhTrangRefThietBi(
							new TinhTrangRefThietBiPK("TT02", tb.getMaThietBi(), phieuBaoTri.getMaPhieuBaoTri()), null,
							new Date()));
				} else {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NOT_CHANGE_TT");
				}
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NON_ITEM_IN_LIST");
		}
		return tinhTrangRefThietBiService.saveAll(newLstTTTBs);
	}

	// on Lst Bao Tri Thiet Bi
	@GetMapping("get-all-phieu-baotri")
	public Iterable<PhieuBaoTri> getAllPhieuBaoTriThietBi() {
		List<PhieuBaoTri> lstPhieu = (List<PhieuBaoTri>)phieuBaoTriService.findAll();
		Collections.sort(lstPhieu, new PhieuBaoTriComporator());
		return lstPhieu;
	}

	@GetMapping("get-ct-phieu-baotri/{maPhieu}")
	public List<ChiTietPhieuBaoTri> getChiTietPhieuBaoTri(@PathVariable int maPhieu) {
		List<TinhTrangRefThietBi> lstTTTB = tinhTrangRefThietBiService.getByMaPhieuBaoTri(maPhieu);

		List<ChiTietPhieuBaoTri> lstChiTietPhieu = new ArrayList<ChiTietPhieuBaoTri>();

		for (TinhTrangRefThietBi ttrefTB : lstTTTB) {
			lstChiTietPhieu.add(
					new ChiTietPhieuBaoTri(ttrefTB.getDenNgay(), ttrefTB.getTuNgay(), ttrefTB.getId().getMaTinhTrang(),
							tinhTrangService.findById(ttrefTB.getId().getMaTinhTrang()).getTenTinhTrang(),
							ttrefTB.getId().getMaThietBi(),
							thietBiService.findById(ttrefTB.getId().getMaThietBi()).getLoaiTb().getTenLoai(),
							ttrefTB.getId().getMaPhieuBaoTri()));
		}

		return lstChiTietPhieu;
	}

	@PutMapping("batdau-baotri/{maPhieu}")
	public List<ChiTietPhieuBaoTri> updateDangBaoTri(@PathVariable int maPhieu) {
		List<TinhTrangRefThietBi> updateLstTTTBs = tinhTrangRefThietBiService.getByMaPhieuBaoTri(maPhieu);
		List<TinhTrangRefThietBi> deleteLstTTTBs = updateLstTTTBs;

		PhieuBaoTri phieu = phieuBaoTriService.findByID(maPhieu);
		phieu.setTinhTrangPhieu("sua_chua");
		phieu.setNgayBaoTri(new Date());

		for (int i = 0; i < updateLstTTTBs.size(); i++) {
			updateLstTTTBs.get(i).setId(new TinhTrangRefThietBiPK("TT03", updateLstTTTBs.get(i).getId().getMaThietBi(),
					updateLstTTTBs.get(i).getId().getMaPhieuBaoTri()));
		}
		tinhTrangRefThietBiService.deleteAll(deleteLstTTTBs);
		phieuBaoTriService.save(phieu);
		List<TinhTrangRefThietBi> lstTTTBs = tinhTrangRefThietBiService.saveAll(updateLstTTTBs);

		List<ChiTietPhieuBaoTri> lstChiTietPhieu = new ArrayList<ChiTietPhieuBaoTri>();

		for (TinhTrangRefThietBi ttrefTB : lstTTTBs) {
			lstChiTietPhieu.add(
					new ChiTietPhieuBaoTri(ttrefTB.getDenNgay(), ttrefTB.getTuNgay(), ttrefTB.getId().getMaTinhTrang(),
							tinhTrangService.findById(ttrefTB.getId().getMaTinhTrang()).getTenTinhTrang(),
							ttrefTB.getId().getMaThietBi(),
							thietBiService.findById(ttrefTB.getId().getMaThietBi()).getLoaiTb().getTenLoai(),
							ttrefTB.getId().getMaPhieuBaoTri()));
		}

		return lstChiTietPhieu;
	}

	@PutMapping("hoanthanh-baotri/{maPhieu}")
	public List<ChiTietPhieuBaoTri> updateHoanThanhBaoTri(@PathVariable int maPhieu, @RequestBody BaoTriData data) {
		List<TinhTrangRefThietBi> updateLstTTTBs = tinhTrangRefThietBiService.getByMaPhieuBaoTri(maPhieu);
		List<TinhTrangRefThietBi> deleteLstTTTBs = updateLstTTTBs;

		// Set thông tin phiếu
		PhieuBaoTri phieu = phieuBaoTriService.findByID(maPhieu);
		phieu.setTinhTrangPhieu("hoang_thanh");
		phieu.setDiaChiBaoTri(data.getDiaChiBaoTri());
		phieu.setMaNhanVienChiuTrachNhiem(data.getMaNhanVienChiuTrachNhiem());
		phieu.setNoiBaoTri(data.getNoiBaoTri());
		phieu.setPhiBaoTri(data.getPhiBaoTri());

		// Tạo danh sach cập nhật TinhTrangRefThietBi
		for (int i = 0; i < updateLstTTTBs.size(); i++) {
			updateLstTTTBs.get(i).setId(new TinhTrangRefThietBiPK("TT01", updateLstTTTBs.get(i).getId().getMaThietBi(),
					updateLstTTTBs.get(i).getId().getMaPhieuBaoTri()));
			updateLstTTTBs.get(i).setDenNgay(new Date());
		}
		tinhTrangRefThietBiService.deleteAll(deleteLstTTTBs);
		phieuBaoTriService.save(phieu);
		List<TinhTrangRefThietBi> lstTTTBs = tinhTrangRefThietBiService.saveAll(updateLstTTTBs);

		// Custom dữ liệu trả về
		List<ChiTietPhieuBaoTri> lstChiTietPhieu = new ArrayList<ChiTietPhieuBaoTri>();

		for (TinhTrangRefThietBi ttrefTB : lstTTTBs) {
			lstChiTietPhieu.add(
					new ChiTietPhieuBaoTri(ttrefTB.getDenNgay(), ttrefTB.getTuNgay(), ttrefTB.getId().getMaTinhTrang(),
							tinhTrangService.findById(ttrefTB.getId().getMaTinhTrang()).getTenTinhTrang(),
							ttrefTB.getId().getMaThietBi(),
							thietBiService.findById(ttrefTB.getId().getMaThietBi()).getLoaiTb().getTenLoai(),
							ttrefTB.getId().getMaPhieuBaoTri()));
		}

		return lstChiTietPhieu;
	}

	@GetMapping("get-all-nhanvien")
	public List<NhanVien> getAllNhanVien() {
		return nhanVienService.findAll();
	}

}
