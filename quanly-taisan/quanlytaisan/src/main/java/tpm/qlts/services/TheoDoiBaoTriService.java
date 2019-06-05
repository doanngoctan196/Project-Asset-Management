/*package tpm.qlts.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tpm.qlts.custommodels.ChiTietPhieuBaoTri;
import tpm.qlts.repositorys.TheoDoiBaoBiRepository;
@Service("theoDoiBaoTriSevice")
public class TheoDoiBaoTriService {
	
	@Autowired
	private TheoDoiBaoBiRepository theoDoiBaoTriRepository;
	
	public ChiTietPhieuBaoTri findById(Long id) {
		Optional<ChiTietPhieuBaoTri> s = theoDoiBaoTriRepository.findById(id);
		if (s.isPresent()) {
			return s.get();
		}
		return null;
	}
	public boolean checkExisted(long id) {
		return theoDoiBaoTriRepository.existsById(id);
	}
}
*/