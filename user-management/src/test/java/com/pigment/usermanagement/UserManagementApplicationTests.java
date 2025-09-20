package com.pigment.usermanagement;

import com.pigment.usermanagement.service.TokenHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class UserManagementApplicationTests {
	@MockitoBean
	private TokenHandler tokenHandler;
	@Test
	void contextLoads() {
	}

}
