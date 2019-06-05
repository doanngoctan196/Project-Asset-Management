package tpm.qlts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tpm.qlts.entitys.NhanVien;
import tpm.qlts.repositorys.NhanVienRepository;

@Service("nhanVienService")
public class NhanVienService{
	
	@Autowired private NhanVienRepository nhanVienRepository;

	public void deleteById(String id)
	{
		nhanVienRepository.deleteById(id);
	}
	
	public  NhanVien save(NhanVien nhanVien) {
		return nhanVienRepository.save(nhanVien);
	}
	
	public Optional<NhanVien> findById(String id)
	{
		return nhanVienRepository.findById(id);
	}
	
	public List<NhanVien> findAll()
	{
		return (List<NhanVien>) nhanVienRepository.findAll();
	}
	
	public boolean existsById(String id)
	{
		return nhanVienRepository.existsById(id);
	}
	
	public List<NhanVien> findNhanVienByName(String ten)
	{
		return nhanVienRepository.findNhanVienByName(ten);
	}
	
	public List<NhanVien> getNhanVienRefPhongBan(String maPhongBan){
		return nhanVienRepository.getNhanVienRefPhongBan(maPhongBan);
	}
}
