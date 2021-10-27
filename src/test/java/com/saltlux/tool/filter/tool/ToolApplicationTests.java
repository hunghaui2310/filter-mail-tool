package com.saltlux.tool.filter.tool;

import com.saltlux.tool.filter.tool.controller.FilterAllMailController;
import com.saltlux.tool.filter.tool.model.BusinessEmailModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.repo.BusinessEmailRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.service.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ToolApplicationTests {

	@Autowired
	private EmailRepo emailRepo;

	@Autowired
	private BusinessEmailRepo businessEmailRepo;

	@Autowired
	private BusinessEmailService businessEmailService;

	@Autowired
	private FilterAllMailController filterAllMailController;

	@Autowired
	private SaveAllService saveAllService;

	@Test
	void contextLoads() {
	}

	@Test
	void testEmail() {
		System.out.println(AppUtil.startWithIgnoreCase("234reHR2@", "hr@"));
	}

	@Test
	void testHotEmail() {
		Pageable pageable = PageRequest.of(0, 2000);
		Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailStartsWith(pageable, "hr@");
		System.out.println(idEmails.getTotalElements());
	}

	@Test
	void testBusinessEmail() throws Exception {
		Pageable pageable = PageRequest.of(0, 2000);
		Page<BusinessEmailModel> idEmails = businessEmailRepo.findAllByMemberPrimaryEmailStartsWith(pageable, "email@");
		System.out.println(idEmails.getTotalElements());
	}

	@Test
	void testYahooEmail() {
//		businessEmailService.filterBusinessMail();
	}

	@Test
	void filterAllMail() throws InterruptedException {
//		filterAllMailController.filterAllMail(100);
//		Thread.sleep(2000);
//		saveAllService.saveAllMail();
	}
}
