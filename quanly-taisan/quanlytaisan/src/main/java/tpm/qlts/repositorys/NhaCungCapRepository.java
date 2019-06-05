package tpm.qlts.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tpm.qlts.entitys.NhaCungCap;

@Repository("nhaCungCapRepository")
public interface NhaCungCapRepository extends CrudRepository<NhaCungCap, String>{

}
