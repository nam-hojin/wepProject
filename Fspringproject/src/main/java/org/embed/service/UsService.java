package org.embed.service;

import org.apache.ibatis.annotations.Param;
import org.embed.dto.UsDTO;
import org.springframework.stereotype.Service;
@Service
public interface UsService {
    int insertUs(UsDTO us) throws Exception;
   
}
	
	
	
	
	/*
	 * int updateUs(UsDTO usDTO)throws Exception; int deleteUs(int UsId)throws
	 * Exception;
	 */

