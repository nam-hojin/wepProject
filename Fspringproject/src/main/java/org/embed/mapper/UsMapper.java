package org.embed.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.embed.dto.UsDTO;

@Mapper
public interface UsMapper {
    int insertUs(UsDTO us) throws Exception;
    UsDTO selectLogin(@Param("userName") String userName);
}
	/*
	 * int updateUs(UsDTO usDTO)throws Exception; int deleteUs(int UsId)throws
	 * Exception;
	 */
	
