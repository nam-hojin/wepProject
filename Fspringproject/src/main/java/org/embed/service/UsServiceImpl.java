package org.embed.service;

import org.embed.dto.UsDTO;
import org.embed.mapper.UsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsServiceImpl implements UsService {
	@Autowired
    private UsMapper usMapper;
	
	 @Autowired
	    public UsServiceImpl(UsMapper usMapper) {
	        this.usMapper = usMapper;
	    }

    @Override
    public int insertUs(UsDTO us) throws Exception {
        return usMapper.insertUs(us);
    }
    
    
}

	/*
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
	*/

