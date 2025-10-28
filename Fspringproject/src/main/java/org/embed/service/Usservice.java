package org.embed.service;

import org.embed.dto.UsDTO;
import org.embed.mapper.Usmapper;

public interface Usservice  {

	
	int insertUs(UsDTO us)throws Exception;
	int updateUs(UsDTO usDTO)throws Exception;
	int deleteUs(int UsId)throws Exception;
	
}
