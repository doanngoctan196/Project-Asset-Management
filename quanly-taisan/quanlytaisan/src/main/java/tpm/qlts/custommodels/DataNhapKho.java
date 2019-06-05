package tpm.qlts.custommodels;

import java.util.List;

public class DataNhapKho {
	private String maNhaCungCap;
	private String kieuNhap;

	private List<ThietBiNhap> lstThietBi;

	public DataNhapKho() {
	}

	public DataNhapKho(String maNhaCungCap, String kieuNhap, List<ThietBiNhap> lstThietBi) {
		super();
		this.maNhaCungCap = maNhaCungCap;
		this.kieuNhap = kieuNhap;
		this.lstThietBi = lstThietBi;
	}

	public String getMaNhaCungCap() {
		return maNhaCungCap;
	}

	public void setMaNhaCungCap(String maNhaCungCap) {
		this.maNhaCungCap = maNhaCungCap;
	}

	public String getKieuNhap() {
		return kieuNhap;
	}

	public void setKieuNhap(String kieuNhap) {
		this.kieuNhap = kieuNhap;
	}

	public List<ThietBiNhap> getLstThietBi() {
		return lstThietBi;
	}

	public void setLstThietBi(List<ThietBiNhap> lstThietBi) {
		this.lstThietBi = lstThietBi;
	}

}
