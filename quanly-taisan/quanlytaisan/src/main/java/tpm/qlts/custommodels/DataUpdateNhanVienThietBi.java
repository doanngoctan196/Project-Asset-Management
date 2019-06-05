package tpm.qlts.custommodels;

import java.util.List;

public class DataUpdateNhanVienThietBi {
	public String maNhanVien;
	public boolean kieuBangiao;
	List<Long> lstThietBi;
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public boolean isKieuBangiao() {
		return kieuBangiao;
	}
	public void setKieuBangiao(boolean kieuBangiao) {
		this.kieuBangiao = kieuBangiao;
	}
	public List<Long> getLstThietBi() {
		return lstThietBi;
	}
	public void setLstThietBi(List<Long> lstThietBi) {
		this.lstThietBi = lstThietBi;
	}
	public DataUpdateNhanVienThietBi() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DataUpdateNhanVienThietBi(String maNhanVien, boolean kieuBangiao, List<Long> lstThietBi) {
		super();
		this.maNhanVien = maNhanVien;
		this.kieuBangiao = kieuBangiao;
		this.lstThietBi = lstThietBi;
	}
	
	
}
