package tpm.qlts.custommodels;

import java.util.Date;

public class TheoDoiLuanChuyen {
	private long maThietBi;
	private Date tuNgay;
	private Date denNgay;
	private String tenPhongBan;
	private String tenThietBi;
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
	public String getTenPhongBan() {
		return tenPhongBan;
	}
	public void setTenPhongBan(String tenPhongBan) {
		this.tenPhongBan = tenPhongBan;
	}
	public String getTenThietBi() {
		return tenThietBi;
	}
	public void setTenThietBi(String tenThietBi) {
		this.tenThietBi = tenThietBi;
	}
	public TheoDoiLuanChuyen(long maThietBi, Date tuNgay, Date denNgay, String tenPhongBan, String tenThietBi) {
		super();
		this.maThietBi = maThietBi;
		this.tuNgay = tuNgay;
		this.denNgay = denNgay;
		this.tenPhongBan = tenPhongBan;
		this.tenThietBi = tenThietBi;
	}
	public TheoDoiLuanChuyen() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
