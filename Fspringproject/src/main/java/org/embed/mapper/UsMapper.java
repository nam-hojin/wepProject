package org.embed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.embed.dto.UsDTO;

@Mapper
public interface UsMapper {
    int insertUs(UsDTO us) throws Exception;
}
	/*
	 * int updateUs(UsDTO usDTO)throws Exception; int deleteUs(int UsId)throws
	 * Exception;
	 */
	
