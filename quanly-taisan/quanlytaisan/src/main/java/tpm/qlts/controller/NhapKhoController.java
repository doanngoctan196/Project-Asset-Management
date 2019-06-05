package tpm.qlts.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tpm.qlts.custommodels.DataNhapKho;
import tpm.qlts.custommodels.ThietBiNhap;
import tpm.qlts.entitys.BienNhanThietBi;
import tpm.qlts.entitys.ChiTiet;
import tpm.qlts.entitys.DonViTinh;
import tpm.qlts.entitys.GeneralChiMuc;
import tpm.qlts.entitys.LoaiTB;
import tpm.qlts.entitys.NhaCungCap;
import tpm.qlts.entitys.ThietBi;
import tpm.qlts.services.BienNhanThietBiServices;
import tpm.qlts.services.ChiMucLoaiService;
import tpm.qlts.services.ChiTietServices;
import tpm.qlts.services.DonViTinhService;
import tpm.qlts.services.LoaiTBService;
import tpm.qlts.services.NhaCungCapService;
import tpm.qlts.services.ThietBiServices;

@Controller
@RestController
@RequestMapping("nhap-kho")
public class NhapKhoController {
	@Autowired
	NhaCungCapService nhaCungCapService;

	@Autowired
	DonViTinhService donViTinhService;

	@Autowired
	LoaiTBService loaiTBService;

	@Autowired
	ThietBiServices thietBiServices;

	@Autowired
	BienNhanThietBiServices bienNhanThietBiService;

	@Autowired
	ChiTietServices chiTietService;

	@Autowired
	ChiMucLoaiService chiMucLoaiService;

	// get-info
	@GetMapping("get-all-ncc")
	public List<NhaCungCap> getAllNhaCungCap() {
		return nhaCungCapService.findAll();
	}

	@GetMapping("get-all-dvt")
	public List<DonViTinh> getAllDonViTinh() {
		return donViTinhService.findAll();
	}

	@GetMapping("get-loaitb-sub")
	public List<LoaiTB> getLoaiTBSub() {
		return loaiTBService.getLoaiTBSub();
	}

	@GetMapping("get-loaitb-cha")
	public List<LoaiTB> getLoaiTBCha() {
		return loaiTBService.getLoaiTBCha();
	}
	
	@GetMapping("get-loaitb-by-ncc/{maNCC}")
	public List<LoaiTB> getAllLoaiTBByNhaCungCap(@PathVariable String maNCC){
		return loaiTBService.getAllLoaiTBByNhaCungCap(maNCC);
	}

	@GetMapping("get-autoid/{soluong}")
	public long[] getMaAutoByList(@PathVariable int soluong) {
		long maxID;
		try {
			maxID = thietBiServices.getMaxIDThietBi();
		} catch (AopInvocationException e) {
			maxID = 1000;
		}
		long[] lstIDRes = new long[soluong];

		for (int i = 0; i < soluong; i++) {
			lstIDRes[i] = maxID;
			maxID++;
		}
		return lstIDRes;
	}

	@PostMapping("nhap-kho-hang-loat")
	@Transactional
	public BienNhanThietBi nhapKho(@RequestBody DataNhapKho data) {
		List<ThietBi> lstThietBi = new ArrayList<ThietBi>();
		BienNhanThietBi bienNhan = new BienNhanThietBi();
		List<ChiTiet> lstChiTietBienNhan = new ArrayList<ChiTiet>();
		NhaCungCap ncc = nhaCungCapService.findById(data.getMaNhaCungCap());

		// Don bien nhan
		String maBienNhan = getMaBienNhan();
		bienNhan.setMaBienNhan(maBienNhan);
		bienNhan.setNgayBienNhan(new Date());
		bienNhan.setMaNCC(ncc.getTenNCC());
		bienNhan.setInputtype(data.getKieuNhap());

		// Chi tiet bien nhan
		for (ThietBiNhap thietBi : data.getLstThietBi()) {
			ChiTiet chiTiet = new ChiTiet();
			chiTiet.setTenThietBi(thietBi.getTenThietBi());
			chiTiet.setDonViTinh(thietBi.getMaDonViTinh());
			chiTiet.setSoLuong(thietBi.getSoLuong());
			chiTiet.setBienNhanThietBi(bienNhan);
			chiTiet.setGiaTri(thietBi.getDonGia());

			double khauHao = Double.parseDouble(String.valueOf(thietBi.getKhauHao()));
			DonViTinh dvt = donViTinhService.findById(thietBi.getMaDonViTinh());
			System.out.println(dvt.getTenDonViTinh());
			if (thietBi.getKieuNhap().equals("them_moi") == true) {
				// Tao loại thiết bị con mới
				LoaiTB loaiTBCon = loaiTBService.save(new LoaiTB(thietBi.getMaNhomThietBi(), thietBi.getMaLoaiCha(),
						thietBi.getTenThietBi(), thietBi.getTenThietBi(), ncc));

				// Add thiet bi
				for (Long maThietBi : thietBi.getLstMaThietBi()) {
					ThietBi tb = new ThietBi(maThietBi, thietBi.getBaoHanh(), thietBi.getDonGia(), khauHao, new Date(),
							bienNhan, dvt, loaiTBCon);
					lstThietBi.add(tb);
				}
			} else if (thietBi.getKieuNhap().equals("co_san") == true) {
				// Add thiet bi
				LoaiTB loaiCoSan = loaiTBService.findByID(thietBi.getMaLoaiThietBi());
				for (Long maThietBi : thietBi.getLstMaThietBi()) {
					ThietBi tb = new ThietBi(maThietBi, thietBi.getBaoHanh(), thietBi.getDonGia(), khauHao, new Date(),
							bienNhan, dvt, loaiCoSan);
					lstThietBi.add(tb);
				}
			}

			lstChiTietBienNhan.add(chiTiet);
		}
		BienNhanThietBi resBienNhan = bienNhanThietBiService.update(bienNhan);
		chiTietService.updateAll(lstChiTietBienNhan);
		thietBiServices.update(lstThietBi);
		return resBienNhan;
	}

	@GetMapping("get-chi-muc-test")
	private Iterable<GeneralChiMuc> generalMaLoaiCon() {
		return chiMucLoaiService.findAll();
	}

	public String getMaBienNhan() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return "BN" + timestamp.getTime();
	}
}
