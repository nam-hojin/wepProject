package org.embed.service;

import org.apache.ibatis.annotations.Param;
import org.embed.dto.UsDTO;
import java.util.List;

public interface UsService {
    int insertUs(UsDTO us) throws Exception;
    void registerUser(UsDTO user) throws Exception;
    UsDTO loginCheck(@Param("userName") String userName, @Param("userPassword") String userPassword);
    UsDTO getUserById(int userId) throws Exception;
    int updateUs(UsDTO us) throws Exception;
    int deleteUs(int userId) throws Exception;
    UsDTO findByUserName(String userName) throws Exception;
    List<UsDTO> getAllUsers() throws Exception; 
}