package tpm.qlts.custommodels;

import java.util.Date;

public class TheoDoiBaoTri {
	
	private long maThietBi;
	private Date tuNgay;
	private Date denNgay;
	private String tenTinhTrang;
	private int maPhieuBaoTri;
	private String tenThietBi;
	private int baoHanh;
	public long getMaThietBi() {
		return maThietBi;
	}
	public void setMaThietBi(long maThietBi) {
		this.maThietBi = maThietBi;
	}
	public Date getTuNgay() {
		return tuNgay;
	}
	public void setTuNgay(Date tuNgay) {
		this.tuNgay = tuNgay;
	}
	public Date getDenNgay() {
		return denNgay;
	}
	public void setDenNgay(Date denNgay) {
		this.denNgay = denNgay;
	}
	public String getTenTinhTrang() {
		return tenTinhTrang;
	}
	public void setTenTinhTrang(String tenTinhTrang) {
		this.tenTinhTrang = tenTinhTrang;
	}
	public int getMaPhieuBaoTri() {
		return maPhieuBaoTri;
	}
	public void setMaPhieuBaoTri(int maPhieuBaoTri) {
		this.maPhieuBaoTri = maPhieuBaoTri;
	}
	public String getTenThietBi() {
		return tenThietBi;
	}
	public void setTenThietBi(String tenThietBi) {
		this.tenThietBi = tenThietBi;
	}
	public int getBaoHanh() {
		return baoHanh;
	}
	public void setBaoHanh(int baoHanh) {
		this.baoHanh = baoHanh;
	}
	public TheoDoiBaoTri(long maThietBi, Date tuNgay, Date denNgay, String tenTinhTrang, int maPhieuBaoTri,
			String tenThietBi, int baoHanh) {
		super();
		this.maThietBi = maThietBi;
		this.tuNgay = tuNgay;
		this.denNgay = denNgay;
		this.tenTinhTrang = tenTinhTrang;
		this.maPhieuBaoTri = maPhieuBaoTri;
		this.tenThietBi = tenThietBi;
		this.baoHanh = baoHanh;
	}
	public TheoDoiBaoTri() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

}
