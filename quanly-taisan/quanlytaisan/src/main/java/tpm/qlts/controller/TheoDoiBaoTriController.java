/*package tpm.qlts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tpm.qlts.custommodels.ChiTietThietBi;
import tpm.qlts.services.TheoDoiBaoTriService;

@Controller
@RestController
@RequestMapping("flowBaoTri")
public class TheoDoiBaoTriController {
	@Autowired
	private TheoDoiBaoTriService theoDoiBaoTriService;
	
	@GetMapping("findbyBaoTri/{id}")
	public ChiTietThietBi thongtinBaoTri(@PathVariable long id)
	{
		if(theoDoiBaoTriService.checkExisted(id) == true)
		{
			ChiTietThietBi bt = theoDoiBaoTriService.findById(id);
		}
	}
}
*/