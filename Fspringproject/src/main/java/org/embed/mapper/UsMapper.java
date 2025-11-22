package org.embed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.embed.dto.UsDTO;

@Mapper
public interface UsMapper {
	int insertUs(UsDTO us);

	UsDTO loginCheck(@Param("userName") String userName, @Param("userPassword") String userPassword);

	UsDTO getUserById(@Param("userId") int userId);

	UsDTO findByUserName(@Param("userName") String userName);

	int updateUs(UsDTO us);

	int deleteUs(@Param("userId") int userId);

	List<UsDTO> getAllUsers();
}