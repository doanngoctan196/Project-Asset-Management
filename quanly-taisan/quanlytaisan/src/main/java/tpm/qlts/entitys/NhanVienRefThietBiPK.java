package tpm.qlts.entitys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The primary key class for the NV_TB database table.
 * 
 */
@Embeddable
public class NhanVienRefThietBiPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "MaNhanVien", insertable = false, updatable = false)
	private String maNhanVien;

	@Column(name = "MaThietBi", insertable = false, updatable = false)
	private long maThietBi;

	@Column(name = "MaBanGiao", insertable = false, updatable = false)
	private int MaBanGiao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Timestame", insertable = false, updatable = false)
	private Date timestame;

	public NhanVienRefThietBiPK() {
	}

	public String getMaNhanVien() {
		return this.maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public long getMaThietBi() {
		return this.maThietBi;
	}

	public int getMaBanGiao() {
		return MaBanGiao;
	}

	public void setMaBanGiao(int maBanGiao) {
		MaBanGiao = maBanGiao;
	}

	public void setMaThietBi(long maThietBi) {
		this.maThietBi = maThietBi;
	}

	public Date getTimestame() {
		return timestame;
	}

	public void setTimestame(Date timestame) {
		this.timestame = timestame;
	}

	public NhanVienRefThietBiPK(String maNhanVien, long maThietBi, Date timestame) {
		super();
		this.maNhanVien = maNhanVien;
		this.maThietBi = maThietBi;
		this.timestame = timestame;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVienRefThietBiPK other = (NhanVienRefThietBiPK) obj;
		if (maNhanVien == null) {
			if (other.maNhanVien != null)
				return false;
		} else if (!maNhanVien.equals(other.maNhanVien))
			return false;
		if (maThietBi != other.maThietBi)
			return false;
		if (timestame == null) {
			if (other.timestame != null)
				return false;
		} else if (!timestame.equals(other.timestame))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maNhanVien == null) ? 0 : maNhanVien.hashCode());
		result = prime * result + (int) (maThietBi ^ (maThietBi >>> 32));
		result = prime * result + ((timestame == null) ? 0 : timestame.hashCode());
		return result;
	}
}