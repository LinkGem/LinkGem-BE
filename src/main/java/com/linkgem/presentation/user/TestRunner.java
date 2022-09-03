package com.linkgem.presentation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.linkgem.domain.gembox.GemBoxStore;

@Component
public class TestRunner implements ApplicationRunner {

	@Autowired
	GemBoxStore gemBoxStore;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		gemBoxStore.deleteAllByUserId(1L);
	}
}
