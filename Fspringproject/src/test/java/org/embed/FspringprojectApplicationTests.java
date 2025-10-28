package org.embed;

import java.lang.reflect.Parameter;
import java.security.PublicKey;

import org.embed.dto.UsDTO;
import org.embed.mapper.Usmapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FspringprojectApplicationTests {
	@Autowired
	private Usmapper usmapper;
	
	/*
	  @Test
	  
	  @DisplayName("아이디생성") public void testInsert() throws Exception{
	   UsDTO us = new UsDTO();
	  
	  us.setUserName("햇감자"); 
	  us.setPassword("1111"); 
	  us.setPasswordCheck("1111");
	  us.setName("햇감자"); 
	  us.setPhone("010-123-1234"); 
	  us.setEmail("aaa@aaaaa.net");
	  us.setBirth("2025-10-27"); 
	  us.setGender("M"); 
	  int result = usmapper.insertUs(us); 
	  System.out.println("아이디생성 (성공:1, 실패: 0) : "+ result);
	  }
	 
		
	@Test
	@DisplayName("회원정보 수정")
	public void testUpdate()throws Exception{
			UsDTO parame = new UsDTO();
			parame.setUserName("강낭콩");
			parame.setPassword("222");
			parame.setName("강낭콩");
			parame.setPhone("010-123-1234");
			parame.setEmail("aaa@aa.net");
			parame.setBirth("2025-10-27");
			parame.setGender("M");
			parame.setUserId((int)1);
			
	int result = usmapper.updateUs(parame);
	System.out.println("회원정보수정"+result);
	
			
		}
	
	@Test
	@DisplayName("삭제")
	public void testdelete() throws Exception{
		UsDTO us = new UsDTO();
		
	int result = usmapper.deleteUs((int)1);
	System.out.print("삭제 "+ result);
		
		
	}
	*/
	
	
	
	
}
