package org.embed.service;

import java.util.List;
import org.embed.dto.UsDTO;
import org.embed.mapper.UsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsServiceImpl implements UsService {

	private final UsMapper usMapper;

	@Autowired
	public UsServiceImpl(UsMapper usMapper) {
		this.usMapper = usMapper;
	}

	@Override
	public int insertUs(UsDTO us) throws Exception {
		if (us.getRole() == null)
			us.setRole("ROLE_USER");
		return usMapper.insertUs(us);
	}

	@Override
	public void registerUser(UsDTO user) throws Exception {
		if (user.getRole() == null)
			user.setRole("ROLE_USER");
		usMapper.insertUs(user);
	}

	@Override
	public UsDTO loginCheck(String userName, String userPassword) {
		return usMapper.loginCheck(userName, userPassword);
	}

	@Override
	public UsDTO getUserById(int userId) throws Exception {
		return usMapper.getUserById(userId);
	}

	@Override
	public int updateUs(UsDTO us) throws Exception {
		return usMapper.updateUs(us);
	}

	@Override
	public int deleteUs(int userId) throws Exception {
		return usMapper.deleteUs(userId);
	}

	@Override
	public UsDTO findByUserName(String userName) throws Exception {
		return usMapper.findByUserName(userName);
	}

	@Override
	public List<UsDTO> getAllUsers() throws Exception {
		return usMapper.getAllUsers();
	}
}