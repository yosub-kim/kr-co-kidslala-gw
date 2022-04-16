package kr.co.kmac.pms.expertpool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.kmac.pms.common.util.SessionUtils;
import kr.co.kmac.pms.expertpool.domain.ExpertpoolSearchEntity;
import kr.co.kmac.pms.expertpool.domain.ExpertpoolSearchParam;
import kr.co.kmac.pms.expertpool.service.ExpertpoolSearchService;

@Controller
@RequestMapping("/expertpool/*")
public class ExpertpoolSearchController {

	@Autowired
	private ExpertpoolSearchService expertpoolSearchService;

	@GetMapping("/searchList")
	public String getExpertpoolSearch(ExpertpoolSearchParam searchParam, Model model) {

		// 상임, RA 체크
		if(SessionUtils.getJobclass().equals("J") || SessionUtils.getJobclass().equals("N"))
			searchParam.setConsultantChk(SessionUtils.getJobclass());
		
		model.addAttribute("searchParam", expertpoolSearchService.setSearchMetaInfo(searchParam));
		model.addAttribute("mainList", expertpoolSearchService.getExpertpoolSearchMainList(searchParam));
		
		List<ExpertpoolSearchEntity> jobClassAgg = expertpoolSearchService.getExpertpoolSearchJobClassAgg(searchParam);
		if (searchParam.getJobClass() != null && searchParam.getJobClass().length > 0) {
			for (ExpertpoolSearchEntity entity : jobClassAgg) {
				for (int i = 0; i < searchParam.getJobClass().length; i++) {
					if (entity.getJobClass().equals(searchParam.getJobClass()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ExpertpoolSearchEntity> deptAgg = expertpoolSearchService.getExpertpoolSearchDeptAgg(searchParam);
		if (searchParam.getDept() != null && searchParam.getDept().length > 0) {
			for (ExpertpoolSearchEntity entity : deptAgg) {
				for (int i = 0; i < searchParam.getDept().length; i++) {
					if (entity.getDept().equals(searchParam.getDept()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ExpertpoolSearchEntity> restrictAgg = expertpoolSearchService.getExpertpoolSearchRestrictAgg(searchParam);
		if (searchParam.getRestrictUser() != null && searchParam.getRestrictUser().length > 0) {
			for (ExpertpoolSearchEntity entity : restrictAgg) {
				for (int i = 0; i < searchParam.getRestrictUser().length; i++) {
					if (entity.getRestrictUser().equals(searchParam.getRestrictUser()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}

		model.addAttribute("jobClassAgg", jobClassAgg);
		model.addAttribute("deptAgg", deptAgg);
		model.addAttribute("restrictAgg", restrictAgg);

		return "/expertpool/ExpertpoolSearchList";
	}
	
	@GetMapping("/restrict/searchList")
	public String getRestrictExpertpoolSearch(ExpertpoolSearchParam searchParam, Model model) {

		model.addAttribute("searchParam", expertpoolSearchService.setSearchMetaInfo(searchParam));

		model.addAttribute("mainList", expertpoolSearchService.getExpertpoolSearchMainList(searchParam));

		List<ExpertpoolSearchEntity> jobClassAgg = expertpoolSearchService.getExpertpoolSearchJobClassAgg(searchParam);
		if (searchParam.getJobClass() != null && searchParam.getJobClass().length > 0) {
			for (ExpertpoolSearchEntity entity : jobClassAgg) {
				for (int i = 0; i < searchParam.getJobClass().length; i++) {
					if (entity.getJobClass().equals(searchParam.getJobClass()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		
		List<ExpertpoolSearchEntity> restrictAgg = expertpoolSearchService.getExpertpoolSearchRestrictAgg(searchParam);
		if (searchParam.getRestrictUser() != null && searchParam.getRestrictUser().length > 0) {
			for (ExpertpoolSearchEntity entity : restrictAgg) {
				for (int i = 0; i < searchParam.getRestrictUser().length; i++) {
					if (entity.getRestrictUser().equals(searchParam.getRestrictUser()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}

		model.addAttribute("jobClassAgg", jobClassAgg);
		model.addAttribute("restrictAgg", restrictAgg);

		return "/expertpool/RestrictExpertpoolSearchList";
	}
	
	@GetMapping("/hr/searchList")
	public String getHRExpertPoolSearch(ExpertpoolSearchParam searchParam, Model model) {
		
		// 상근/상임/ra count
		searchParam.setConsultantChk("A");
		
		model.addAttribute("searchParam", expertpoolSearchService.setSearchMetaInfo(searchParam));
		model.addAttribute("mainList", expertpoolSearchService.getHRExpertpoolSearchMainList(searchParam));
		
		List<ExpertpoolSearchEntity> jobClassAgg = expertpoolSearchService.getHRExpertpoolSearchJobClassAgg(searchParam);
		if (searchParam.getJobClass() != null && searchParam.getJobClass().length > 0) {
			for (ExpertpoolSearchEntity entity : jobClassAgg) {
				for (int i = 0; i < searchParam.getJobClass().length; i++) {
					if (entity.getJobClass().equals(searchParam.getJobClass()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		List<ExpertpoolSearchEntity> deptAgg = expertpoolSearchService.getHRExpertpoolSearchDeptAgg(searchParam);
		if (searchParam.getDept() != null && searchParam.getDept().length > 0) {
			for (ExpertpoolSearchEntity entity : deptAgg) {
				for (int i = 0; i < searchParam.getDept().length; i++) {
					if (entity.getDept().equals(searchParam.getDept()[i])) {
						entity.setChecked(true);
					}
				}
			}
		}
		
		model.addAttribute("jobClassAgg", jobClassAgg);
		model.addAttribute("deptAgg", deptAgg);

		return "/expertpool/HRExpertpoolSearchList";
	}

}
