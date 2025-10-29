package org.embed.service;


import org.embed.dto.UsDTO;
import org.embed.fileutils.FileUtils;
import org.embed.mapper.UsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsServicempl implements UsService{
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	public UsMapper usMapper;
	
	@Override
	public int insertUs(UsDTO us) throws Exception {
		// TODO Auto-generated method stub
		return usMapper.insertUs(us);
	}
	
	@Override
	public int updateUs(UsDTO usDTO) throws Exception {
		// TODO Auto-generated method stub
		return usMapper.updateUs(usDTO);
	}
	
	@Override
	public int deleteUs(int UsId) throws Exception {
		// TODO Auto-generated method stub
		return usMapper.deleteUs(UsId);
	}
}
