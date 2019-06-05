package tpm.qlts.entitys;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the NV_TB database table.
 * 
 */
@Entity
@Table(name="NV_TB")
@NamedQuery(name="NhanVienRefThietBi.findAll", query="SELECT n FROM NhanVienRefThietBi n")
public class NhanVienRefThietBi implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NhanVienRefThietBiPK id;
	
	@Column(name="MaThietBi", insertable=false, updatable=false)
	private long maThietBi;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DenNgay")
	private Date denNgay;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TuNgay")
	private Date tuNgay;
	
	@Column(name = "KieuBanGiao")
	private boolean kieuBanGiao;

	//bi-directional many-to-one association to NhanVien
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MaNhanVien", updatable = false, insertable = false)
	private NhanVien nhanVien;

	//bi-directional many-to-one association to ThietBi
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MaThietBi", updatable = false, insertable = false)
	private ThietBi thietBi;

	public NhanVienRefThietBi() {
	}

	public NhanVienRefThietBiPK getId() {
		return this.id;
	}

	public void setId(NhanVienRefThietBiPK id) {
		this.id = id;
	}

	public Date getDenNgay() {
		return this.denNgay;
	}

	public void setDenNgay(Date denNgay) {
		this.denNgay = denNgay;
	}

	public Date getTuNgay() {
		return this.tuNgay;
	}

	public void setTuNgay(Date tuNgay) {
		this.tuNgay = tuNgay;
	}

	public NhanVien getNhanVien() {
		return this.nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public ThietBi getThietBi() {
		return this.thietBi;
	}

	public void setThietBi(ThietBi thietBi) {
		this.thietBi = thietBi;
	}

	public boolean isKieuBanGiao() {
		return kieuBanGiao;
	}

	public void setKieuBanGiao(boolean kieuBanGiao) {
		this.kieuBanGiao = kieuBanGiao;
	}

	public NhanVienRefThietBi(boolean kieuBanGiao) {
		super();
		this.kieuBanGiao = kieuBanGiao;
	}

	public NhanVienRefThietBi(NhanVienRefThietBiPK id, Date tuNgay, boolean kieuBanGiao) {
		super();
		this.id = id;
		this.tuNgay = tuNgay;
		this.kieuBanGiao = kieuBanGiao;
	}

	public long getMaThietBi() {
		return maThietBi;
	}

	public void setMaThietBi(long maThietBi) {
		this.maThietBi = maThietBi;
	}

	public NhanVienRefThietBi(long maThietBi) {
		super();
		this.maThietBi = maThietBi;
	}
	
	
}