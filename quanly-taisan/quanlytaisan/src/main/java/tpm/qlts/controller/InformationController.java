package tpm.qlts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tpm.qlts.custommodels.ChiTietYeuCauUpdate;
import tpm.qlts.custommodels.DanhSachTB;
import tpm.qlts.custommodels.DataUpdateNhanVienThietBi;
import tpm.qlts.custommodels.LuanChuyenOptions;
import tpm.qlts.custommodels.NhanVienUpdate;
import tpm.qlts.custommodels.PhieuYeuCauThietBiUpdate;
import tpm.qlts.custommodels.options;
import tpm.qlts.entitys.ChiTietYeuCau;
import tpm.qlts.entitys.ChucVu;
import tpm.qlts.entitys.DonViTinh;
import tpm.qlts.entitys.LoaiTB;
import tpm.qlts.entitys.NhanVien;
import tpm.qlts.entitys.NhanVienRefThietBi;
import tpm.qlts.entitys.NhanVienRefThietBiPK;
import tpm.qlts.entitys.PhieuYeuCauThietBi;
import tpm.qlts.entitys.PhongBan;
import tpm.qlts.entitys.ThietBi;
import tpm.qlts.entitys.TinhTrang;
import tpm.qlts.entitys.TinhTrangRefThietBi;
import tpm.qlts.services.ChiTietYeuCauServices;
import tpm.qlts.services.ChucVuService;
import tpm.qlts.services.DonViTinhService;
import tpm.qlts.services.LoaiTBService;
import tpm.qlts.services.NhanVienRefThietBiService;
import tpm.qlts.services.NhanVienService;
import tpm.qlts.services.PhieuYeuCauThietBiServices;
import tpm.qlts.services.PhongBanService;
import tpm.qlts.services.ThietBiServices;
import tpm.qlts.services.TinhTrangService;

@Controller
@RestController
@RequestMapping("infor")
public class InformationController {
	@Autowired
	private PhongBanService phongBanService;

	@Autowired
	private NhanVienService nhanVienService;

	@Autowired
	private ChucVuService chucVuService;

	@Autowired
	private TinhTrangService tinhTrangService;

	@Autowired
	private DonViTinhService donViTinhService;

	@Autowired
	private ChiTietYeuCauServices chiTietYeuCauServices;

	@Autowired
	private PhieuYeuCauThietBiServices phieuYeuCauThietBiServices;

	@Autowired
	private LoaiTBService loaiTBService;

	@Autowired
	private ThietBiServices thietBiServices;

	@Autowired
	private NhanVienRefThietBiService NhanVienRefThietBiService;

	@SuppressWarnings("unused")
	@GetMapping(value = "listAllPhongBan")
	public List<PhongBan> getPhongBan() {
		return phongBanService.findAll();
	}

	@PostMapping(value = "add-phongban")
	public PhongBan addPhongBan(@RequestBody PhongBan phongBan) {
		PhongBan resPhongBan = phongBanService.update(phongBan);
		return resPhongBan;
	}

	@PutMapping("update-phongban")
	public PhongBan updatePhongBan(@RequestBody PhongBan phongBan) {
		if (phongBanService.existsById(phongBan.getMaPhongBan())) {
			return phongBanService.update(phongBan);
		}
		return null;
	}

	@DeleteMapping(value = "delete-phongban/{id}")
	public void deletePhongBan(@PathVariable String id) {
		phongBanService.deleteById(id);
	}

	@DeleteMapping("delete-listPhongBan")
	public int deleteBylistPhongBan(@RequestBody List<String> lstID) {
		int count = 0;
		for (String id : lstID) {
			if (phongBanService.existsById(id)) {
				phongBanService.deleteById(id);
			}
			count++;
		}
		return count;
	}

	@GetMapping(value = "listAllChucVu")
	public List<ChucVu> getChucVu() {
		List<ChucVu> listChucVu = chucVuService.findAll();
		return listChucVu;
	}

	@PostMapping("add-ChucVu")
	public ChucVu addChucVu(@RequestBody ChucVu chucVu) {
		ChucVu resChucVu = chucVuService.update(chucVu);
		return resChucVu;
	}

	@GetMapping(value = "listAllNhanVien")
	public List<NhanVien> getNhanVien() {
		List<NhanVien> listNhanVien = nhanVienService.findAll();
		return listNhanVien;
	}

	@PostMapping(value = "add-employee")
	public NhanVienUpdate addEmployee(@RequestBody NhanVien nhanVien) { // vì sao lại có 2 cái trường đầu tiên
//		 vì trong entity nhân viên xét thuộc tính insertable và updatetable xét cho nó là false nên khi vào phải get cái trường đó vào
		nhanVien.setChucVu(new ChucVu(nhanVien.getMaChucVu()));
		nhanVien.setPhongBan(new PhongBan(nhanVien.getMaPhongBan()));
		NhanVien resNhanVien = nhanVienService.save(nhanVien);
		String tenChucVu = resNhanVien.getChucVu().getTenChucVu();
		String tenPhongBan = resNhanVien.getPhongBan().getTenPhongBan();

		return new NhanVienUpdate(resNhanVien.getMaNhanVien(), resNhanVien.getTenNhanVien(), resNhanVien.getNgaySinh(),
				resNhanVien.getQueQuan(), tenChucVu, tenPhongBan);
	}

	@RequestMapping(value = "update-employee", method = RequestMethod.PUT)
	public NhanVienUpdate updateNhanVien(@RequestBody NhanVien nhanVien) {
		nhanVien.setChucVu(new ChucVu(nhanVien.getMaChucVu()));
		nhanVien.setPhongBan(new PhongBan(nhanVien.getMaPhongBan()));
		if (nhanVienService.existsById(nhanVien.getMaNhanVien())) {
			NhanVien resNhanVien = nhanVienService.save(nhanVien);
			String tenChucVu = resNhanVien.getChucVu().getTenChucVu();
			String tenPhongBan = resNhanVien.getPhongBan().getTenPhongBan();
			return new NhanVienUpdate(resNhanVien.getMaNhanVien(), resNhanVien.getTenNhanVien(),
					resNhanVien.getNgaySinh(), resNhanVien.getQueQuan(), tenChucVu, tenPhongBan);
		}
		return null;
	}

	@DeleteMapping(value = "delete-employee/{id}")
	public void deleteNhanVien(@PathVariable String id) {
		nhanVienService.deleteById(id);
	}

	@DeleteMapping("delete-by-listNhanVien")
	public int deleteByListNhanVien(@RequestBody List<String> lstID) {
		int cout = 0;
		for (String id : lstID) {
			if (nhanVienService.existsById(id)) {
				nhanVienService.deleteById(id);
				cout++;
			}
		}
		return cout;
	}

	@GetMapping(value = "list-employeeId/{id}")
	public NhanVien getNhanVienById(@PathVariable String id) {
		Optional<NhanVien> nhanVien = nhanVienService.findById(id);
		return nhanVien.get();
	}

	@GetMapping(value = "list-employeeByName/{ten}")
	public List<NhanVien> listNhanVienByName(@PathVariable("ten") String ten) {
		return nhanVienService.findNhanVienByName(ten);
	}

	@GetMapping("list-tinhtrang")
	public List<TinhTrang> getTinhTrang() {
		List<TinhTrang> resTinhTrang = tinhTrangService.findAll();
		return resTinhTrang;
	}

	@PostMapping(value = "add-tinhtrang")
	public TinhTrang addTinhTrang(@RequestBody TinhTrang tinhTrang) {
		TinhTrang resTinhTrang = tinhTrangService.update(tinhTrang);
		return resTinhTrang;
	}

	@PutMapping(value = "update-tinhtrang")
	public TinhTrang updateTinhTrang(@RequestBody TinhTrang tinhTrang) {
		if (tinhTrangService.exitstsById(tinhTrang.getMaTinhTrang())) {
			TinhTrang resTinhTrang = tinhTrangService.update(tinhTrang);
			return resTinhTrang;
		}
		return null;
	}

	@DeleteMapping(value = "delete-tinhtrang/{id}")
	public void deleteTinhTrang(@PathVariable String id) {
		tinhTrangService.deleteById(id);
	}

	@DeleteMapping("delete-by-list")
	public int deleteByList(@RequestBody List<String> lstID) {
		int cout = 0;
		for (String id : lstID) {
			if (tinhTrangService.exitstsById(id)) {
				tinhTrangService.deleteById(id);
				cout++;
			}
		}
		return cout;
	}

//	@GetMapping(value="list-searchNhanVien/{key}")
//	public List<NhanVienUpdate> search(@RequestBody String key){
//		List<NhanVien> nvList = nhanVienService.findByKey(key);
//
//		List<NhanVienUpdate> resList = new ArrayList<>();
//		for(NhanVien nvItem : nvList){
//
//			resList.add(new NhanVienUpdate(nvItem.getTenNhanVien(), nvItem.getChucVu().getTenChucVu(), nvItem.getNgaySinh(), nvItem.getQueQuan(), nvItem.getPhongBan().getTenPhongBan(), key));
//		}
//		return resList;
//
//	}

	@RequestMapping(value = "list-employeeUpdate")
	public List<NhanVienUpdate> getNhanVienUpdate() {
		List<NhanVien> nvList = nhanVienService.findAll();

		List<NhanVienUpdate> reslist = new ArrayList<>();
		for (NhanVien nvItem : nvList) {

			reslist.add(new NhanVienUpdate(nvItem.getMaNhanVien(), nvItem.getTenNhanVien(), nvItem.getNgaySinh(),
					nvItem.getQueQuan(), nvItem.getChucVu().getTenChucVu(), nvItem.getPhongBan().getTenPhongBan()));
		}
		return reslist;
	}

	//////////////////////// ----------Don vi
	//////////////////////// tinh-----------------///////////////////////
	@GetMapping(value = "listAllDonViTinh")
	public List<DonViTinh> listDonViTinh() {
		return donViTinhService.findAll();
	}

	@PostMapping(value = "add-newdonvitinh")
	public DonViTinh addDonViTinh(@RequestBody DonViTinh donViTinh) {
		return donViTinhService.save(donViTinh);
	}

	@PutMapping(value = "update-donviinh")
	public DonViTinh updateDonViTinh(@RequestBody DonViTinh donViTinh) {
		if (donViTinhService.existsById(donViTinh.getMaDonViTinh())) {
			return donViTinhService.save(donViTinh);
		}
		return null;
	}

	@DeleteMapping(value = "delete-donvitinh/{id}")
	public void deleteDonviTinh(@PathVariable Integer id) {
		donViTinhService.deleteById(id);
	}

	@DeleteMapping(value = "delete-list-donvitinh")
	public int deleteByLstDonViTinh(@RequestBody List<Integer> lstID) {
		int count = 0;
		for (Integer id : lstID) {
			if (donViTinhService.existsById(id)) {
				donViTinhService.deleteById(id);
			}
			count++;
		}
		return count;
	}

	@GetMapping(value = "listAllctyc")
	public List<ChiTietYeuCau> getAllctyc() {
		return chiTietYeuCauServices.findAll();
	}

	@PostMapping(value = "add-ctyc")
	public ChiTietYeuCau addChiTietyeuCau(@RequestBody ChiTietYeuCau chiTietYeuCau) {
		return chiTietYeuCauServices.update(chiTietYeuCau);
	}

	@PutMapping(value = "update-ctyc")
	public ChiTietYeuCau updateChiTietYeuCau(@RequestBody ChiTietYeuCau chiTietYeuCau) {
		if (chiTietYeuCauServices.existsById(chiTietYeuCau.getMaCT())) {
			return chiTietYeuCauServices.update(chiTietYeuCau);
		}
		return null;
	}

	@DeleteMapping(value = "delete-ctyc")
	public void deleteChiTietYeuCau(@PathVariable Integer id) {
		chiTietYeuCauServices.delete(id);
	}

	@DeleteMapping(value = "delete-list-ctyc")
	public int deleteByLstctyc(@RequestBody List<Integer> lstID) {
		int count = 0;
		for (Integer id : lstID) {
			if (chiTietYeuCauServices.existsById(id)) {
				chiTietYeuCauServices.delete(id);
			}
			count++;
		}
		return count;
	}

	@GetMapping(value = "listAllphieuyeucauthietbi")
	public List<PhieuYeuCauThietBi> getPhieuyeucauthietbi() {
		return phieuYeuCauThietBiServices.findAll();
	}

//	@PostMapping(value = "addPhieuyeucauthietbi")
//	public PhieuYeuCauThietBiUpdate addPhieuyeucauthietbi(@RequestBody PhieuYeuCauThietBi phieuYeuCauThietBi)
//	{
//		 );
//	}

//	@GetMapping(value = "list-allphieu")
//	public PhieuYeuCauThietBiUpdate getAllPhieu(@RequestBody PhieuYeuCauThietBiUpdate phieuYeuCauThietBiUpdate)
//	{
//		List<ChiTietYeuCau> listChiTiet = chiTietYeuCauServices.findAll();
//		List<ChiTietYeuCauUpdate> listChiTietupdate = new ArrayList<ChiTietYeuCauUpdate>();
//		for (ChiTietYeuCau list : listChiTiet) {
//			
//		}
//		return null;
//	}

	@PostMapping(value = "add-phieuyeucau")
	public PhieuYeuCauThietBi addPhieuYeuCau(@RequestBody PhieuYeuCauThietBiUpdate data) {
		PhieuYeuCauThietBi phieu = new PhieuYeuCauThietBi(); // entity -> save to PhieuYeuCau
		// set data to Phieu
		phieu.setNgayLapPhieu(new Date());
		phieu.setNhanVien(new NhanVien(data.getNhanVien()));
		phieu.setMucDich(data.getMucDich());
		PhieuYeuCauThietBi phieuMoi = phieuYeuCauThietBiServices.update(phieu);
		List<ChiTietYeuCau> lstCTYeuCau = new ArrayList<ChiTietYeuCau>(); // entitys -> save to ChiTietYeuCau
		// set data lst chi tiet
		for (ChiTietYeuCauUpdate item : data.getChiTietYeuCaus()) {
			ChiTietYeuCau chiTietYeuCau = new ChiTietYeuCau();
			chiTietYeuCau.setPhieuYeuCauThietBi(phieuMoi);

			chiTietYeuCau.setTenThietBi(item.getTenThietBi());
			chiTietYeuCau.setDonViTInh(item.getDonViTInh());
			chiTietYeuCau.setQuyCach_DatTinh(item.getQuyCach_DatTinh());
			chiTietYeuCau.setSoLuong(item.getSoLuong());

			lstCTYeuCau.add(chiTietYeuCau);
		}
		chiTietYeuCauServices.updateByList(lstCTYeuCau);

		return phieuMoi;
	}

	@GetMapping(value = "getByIdPhieuYeucau/{id}")
	public Optional<PhieuYeuCauThietBi> getByIdPhieuYeuCau(@PathVariable int id) {
		return phieuYeuCauThietBiServices.finById(id);
	}

	@GetMapping(value = "getAllPhieuYeuCau")
	public List<PhieuYeuCauThietBi> getAllPhieuYeuCau() {
		return phieuYeuCauThietBiServices.findAll();
	}

	@GetMapping(value = "getAllByIdPhieu/{id}")
	public List<ChiTietYeuCau> getAllByIdPhieu(@PathVariable int id) {
		return chiTietYeuCauServices.getAllByIdPhieu(id);
	}

	@GetMapping(value = "listAllloaiTb")
	public List<LoaiTB> getAllloaiTb() {
		return loaiTBService.findAll();
	}

	@GetMapping(value = "listLoaiTBByIdphongBan/{id}")
	public List<LoaiTB> getAlllaoiTbByIdphongBan(@PathVariable String id) {
		return loaiTBService.getAllByIdPhongBan(id);
	}
	
	@GetMapping(value = "getAllThietBiPHongBan/{id}")
	public List<ThietBi> getAllThietBiPHongBan(@PathVariable String id){
		return thietBiServices.getAllByIdthietBiPhongBan(id);
	}
	
	@GetMapping(value = "getAllNVTBByIdPhongBan/{id}")
	public List<NhanVienRefThietBi> getAllNVTBByIdPhongBan(@PathVariable String id){
		return NhanVienRefThietBiService.getAllNVTBByIdPhongBan(id);
	}
	
//	@GetMapping(value = "listAllWithPhongBan/{id}")
//	public List<LuanChuyenOptions> getLuanChuyenOption(@PathVariable String id){
//		List<LuanChuyenOptions> luanchuyen = new ArrayList<LuanChuyenOptions>();
//		List<LoaiTB> listLoai = loaiTBService.getAllByIdPhongBan(id);
//		for (LoaiTB Loai : listLoai) {
//			LuanChuyenOptions luanChuyenOptions = new LuanChuyenOptions();
//			luanChuyenOptions.setLabel(Loai.getMaLoai());
//			List<options> option = new ArrayList<options>();
//			luanChuyenOptions.setOptions(option);
//			List<ThietBi> listTb = Loai.getThietBis();
//			for (ThietBi listthietbi : listTb) {
//				List<NhanVienRefThietBi> tresthietbi = listthietbi.getNvTbs();
//				for (NhanVienRefThietBi thietbis : tresthietbi) {
//					if (thietbis.getDenNgay() == null) {
//						options list = new options();
//						list.setValue(Long.toString(thietbis.getMaThietBi()));
//						list.setLabel(Long.toString(thietbis.getMaThietBi()));
//						option.add(list);
//						break;
//					}
//				}
//			}
//			luanchuyen.add(luanChuyenOptions);	
//		}
//		return luanchuyen;
//			
//		}
	
	@GetMapping(value = "listThietBiTheoLoai/{maPhongBan}/{maLoai}")
	public List<LuanChuyenOptions> getThietBiTheoLoai(@PathVariable("maPhongBan") String maPhongBan,
			@PathVariable("maLoai") String maLoai) {
		List<LuanChuyenOptions> lcOptions = new ArrayList<LuanChuyenOptions>();
		List<LoaiTB> loai = loaiTBService.getAllThietBimaLoai(maPhongBan, maLoai);
		for (LoaiTB loaiTB : loai) {
			LuanChuyenOptions luanchuyen = new LuanChuyenOptions();
			luanchuyen.setLabel(loaiTB.getTenLoai());
			List<options> option = new ArrayList<options>();
			luanchuyen.setOptions(option);
			List<ThietBi> thietbi = loaiTB.getThietBis();
			for (ThietBi item : thietbi) {
				options list = new options();
				list.setValue(Long.toString(item.getMaThietBi()));
				list.setLabel(loaiTB.getTenLoai() + " " + Long.toString(item.getMaThietBi()));
				option.add(list);
			}
			lcOptions.add(luanchuyen);
		}
		return lcOptions;

	}


	@GetMapping(value = "listAllWithPhongBan/{id}")
	public List<LuanChuyenOptions> getLuanChuyenOption(@PathVariable String id) {
		List<LuanChuyenOptions> luanchuyen = new ArrayList<LuanChuyenOptions>();
		List<LoaiTB> listLoai = loaiTBService.getAllByIdPhongBan(id);
		for (LoaiTB loaiTB : listLoai) {
			LuanChuyenOptions luanChuyenOptions = new LuanChuyenOptions();
			luanChuyenOptions.setLabel(loaiTB.getTenLoai());
			List<options> option = new ArrayList<options>();
			luanChuyenOptions.setOptions(option);
			List<ThietBi> thietbi = loaiTB.getThietBis();
			for (ThietBi thietbis : thietbi) {
				options list = new options();
				list.setValue(Long.toString(thietbis.getMaThietBi()));
				list.setLabel(loaiTB.getTenLoai() + " " + Long.toString(thietbis.getMaThietBi()));
				option.add(list);
			}
			luanchuyen.add(luanChuyenOptions);
		}
		return luanchuyen;

	}

	@GetMapping(value = "listallDanhSachTB")
	public List<DanhSachTB> getDanhSachTB() {
		List<DanhSachTB> danhsach = new ArrayList<DanhSachTB>();
		List<ThietBi> thietbi = thietBiServices.findAll();
		for (ThietBi listThietBi : thietbi) {
			DanhSachTB danh = new DanhSachTB();
			danh.setMaThietBi(listThietBi.getMaThietBi());
			danh.setTenLoai(listThietBi.getLoaiTb().getTenLoai() + " " + listThietBi.getMaThietBi());
			danhsach.add(danh);
		}
		return danhsach;
	}

	@GetMapping(value = "listAllNhanVienThietBi")
	public List<NhanVienRefThietBi> getNhanVienRefThietBi() {
		return NhanVienRefThietBiService.findAll();
	}

	@PutMapping(value = "update-nhanvien-ref-thietbi")
	public List<NhanVienRefThietBi> updateNhanVienRefThietBi(@RequestBody DataUpdateNhanVienThietBi data) {
		List<NhanVienRefThietBi> lstUpdate = new ArrayList<NhanVienRefThietBi>();
		for (long maTB : data.getLstThietBi()) {
			NhanVienRefThietBiPK key = new NhanVienRefThietBiPK(data.getMaNhanVien(), maTB, new Date());
			lstUpdate.add(new NhanVienRefThietBi(key, new Date(), data.isKieuBangiao()));

			NhanVienRefThietBi recoreOld = NhanVienRefThietBiService.getAllMathietbi(maTB);
			recoreOld.setDenNgay(new Date());
			lstUpdate.add(recoreOld);
		}
		return NhanVienRefThietBiService.updateAll(lstUpdate);
	}

	@GetMapping(value = "listMathietbi/{id}")
	public NhanVienRefThietBi getAllMathietbi(@PathVariable long id) {
		return NhanVienRefThietBiService.getAllMathietbi(id);
	}

	@GetMapping(value = "listNhanVienRefPhongBan/{maPhongBan}")
	public List<NhanVien> getNhanVienRefPhongBan(@PathVariable String maPhongBan) {
		return nhanVienService.getNhanVienRefPhongBan(maPhongBan);
	}

	@GetMapping(value = "getByIdThietBi/{id}")
	public PhongBan getByIdThietBi(@PathVariable long id) {
		return phongBanService.getByIdThietBi(id);
	}
	
	@GetMapping(value = "getThietBiIdPhongBan/{id}")
	public List<LuanChuyenOptions> getThietBiIdPhongBan(@PathVariable String id){
		List<LuanChuyenOptions> lstLuanChuyen = new ArrayList<LuanChuyenOptions>();
		List<ThietBi> lstThietBi = thietBiServices.getThietBiIdPhongBan(id);
		for(ThietBi tb : lstThietBi) {
			String maLoaiCheck = tb.getMaLoai();
			
			options opt = new options();
			opt.setLabel(tb.getLoaiTb().getTenLoai()+ " " + String.valueOf(tb.getMaThietBi()));
			opt.setValue(String.valueOf(tb.getMaThietBi()));
			List<TinhTrangRefThietBi> ttTb = tb.getTtTbs();
			for (TinhTrangRefThietBi ttrefthietBi : ttTb) {
				if (tb.getMaThietBi() == ttrefthietBi.getThietBi().getMaThietBi()) {
					opt.setTenTinhTrang(ttrefthietBi.getTinhTrang().getTenTinhTrang());
					opt.setMoTa(ttrefthietBi.getTinhTrang().getMoTa());
				}
			}
			boolean check = false;
			for(LuanChuyenOptions lc : lstLuanChuyen) {
				if(maLoaiCheck.equals(lc.getValue())) {
					lc.getOptions().add(opt);
					check = true;
				}
			}
			if(check == false) {
				LoaiTB loaiTB = loaiTBService.findByID(tb.getMaLoai());
				LuanChuyenOptions lcOT = new LuanChuyenOptions();
				lcOT.setLabel(loaiTB.getTenLoai());
				lcOT.setValue(loaiTB.getMaLoai());
				List<options> lstOpt = new ArrayList<options>();
				lstOpt.add(opt);
				lcOT.setOptions(lstOpt);
				lstLuanChuyen.add(lcOT);
			}
		}
		return lstLuanChuyen;
	}
	
	public boolean checkExistsMaLoai (String maLoai, List<LuanChuyenOptions> lstLuanChuyen) {
		for(LuanChuyenOptions lc : lstLuanChuyen) {
			if(maLoai.equals(lc.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	@GetMapping(value = "listThietBiIdPhongBanIdLoai/{maPhongBan}/{maLoai}")
	public List<LuanChuyenOptions> getThietBiIdPhongBanIdLoai(@PathVariable("maPhongBan") String maPhongBan, @PathVariable("maLoai") String maLoai){
		List<LuanChuyenOptions> lstLuanChuyen = new ArrayList<LuanChuyenOptions>();
		List<ThietBi> lstThietBi = thietBiServices.getThietBiIdPhongBanIdLoai(maPhongBan, maLoai);
		for (ThietBi thietBi : lstThietBi) {
			String chectMaloai = thietBi.getMaLoai();
			options opt = new options();
			opt.setLabel(thietBi.getLoaiTb().getTenLoai() + " " + String.valueOf(thietBi.getMaThietBi()));
			opt.setValue(String.valueOf(thietBi.getMaThietBi()));
			List<TinhTrangRefThietBi> ttTb = thietBi.getTtTbs();
			for (TinhTrangRefThietBi ttrefthietBi : ttTb) {
				if (thietBi.getMaThietBi() == ttrefthietBi.getThietBi().getMaThietBi()) {
					opt.setTenTinhTrang(ttrefthietBi.getTinhTrang().getTenTinhTrang());
					opt.setMoTa(ttrefthietBi.getTinhTrang().getMoTa());
				}
			}
			boolean check = false;
			for(LuanChuyenOptions lc : lstLuanChuyen) {
				if (chectMaloai.equals(lc.getValue())) {
					lc.getOptions().add(opt);
					check = true;
				}
			}
			if(check == false) {
				LoaiTB loaiTB = loaiTBService.findByID(thietBi.getMaLoai());
				LuanChuyenOptions lcOT = new LuanChuyenOptions();
				lcOT.setLabel(loaiTB.getTenLoai());
				lcOT.setValue(loaiTB.getMaLoai());
				List<options> lstOpt = new ArrayList<options>();
				lstOpt.add(opt);
				lcOT.setOptions(lstOpt);
				lstLuanChuyen.add(lcOT);
			}
		}
		return lstLuanChuyen;
	}
	
	
}
