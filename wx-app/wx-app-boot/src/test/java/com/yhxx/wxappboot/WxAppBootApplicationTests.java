package com.yhxx.wxappboot;

import com.yhxx.wxapp.web.api.ProgramIndexController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxAppBootApplication.class)
public class WxAppBootApplicationTests {

	@Autowired
	private ProgramIndexController programIndexController;

	@Test
	public void contextLoads() {
	}

}
