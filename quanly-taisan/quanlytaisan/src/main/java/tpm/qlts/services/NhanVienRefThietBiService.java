package tpm.qlts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tpm.qlts.entitys.NhanVienRefThietBi;
import tpm.qlts.entitys.NhanVienRefThietBiPK;
import tpm.qlts.repositorys.NhanVienRefThietBiRepository;

@Service("NhanVienRefThietBiService")
public class NhanVienRefThietBiService {
	@Autowired
	private NhanVienRefThietBiRepository nhanVienRefThietBiRepository;
	
	public void deleteById(NhanVienRefThietBiPK id)
	{
		nhanVienRefThietBiRepository.deleteById(id);
	}
	
	public  NhanVienRefThietBi update(NhanVienRefThietBi nhanVienRefThietBi) {
		return nhanVienRefThietBiRepository.save(nhanVienRefThietBi);
	}
	
	public  List<NhanVienRefThietBi> updateAll(List<NhanVienRefThietBi> nhanVienRefThietBis) {
		return (List<NhanVienRefThietBi>) nhanVienRefThietBiRepository.saveAll(nhanVienRefThietBis);
	}
	
	public Optional<NhanVienRefThietBi> findById(NhanVienRefThietBiPK id)
	{
		return nhanVienRefThietBiRepository.findById(id);
	}
	
	public List<NhanVienRefThietBi> findAll()
	{
		return (List<NhanVienRefThietBi>) nhanVienRefThietBiRepository.findAll();
	}
	
	public NhanVienRefThietBi getAllMathietbi(long id){
		return nhanVienRefThietBiRepository.getAllMathietbi(id);
	}
	
	public List<NhanVienRefThietBi> getAllNVTBByIdPhongBan(String id){
		return nhanVienRefThietBiRepository.getAllNVTBByIdPhongBan(id);
	}
	public List<NhanVienRefThietBi> getbyIDMathietbi(long id)
	{
		return nhanVienRefThietBiRepository.getbyIDMathietbi(id);
	}
}
