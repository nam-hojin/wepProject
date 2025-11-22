package org.embed.service;

import java.util.List;

import org.embed.dto.NoticeDTO;
import org.embed.mapper.NoticeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeServiceImpl implements NoticeService {

	private final NoticeMapper noticeMapper;

	public NoticeServiceImpl(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}

	@Override
	public List<NoticeDTO> getNoticeList() throws Exception {
		return noticeMapper.selectNoticeList();
	}

	@Override
	public NoticeDTO getNoticeDetail(int postId) throws Exception {
		return noticeMapper.selectNoticeDetail(postId);
	}

	@Override
	@Transactional
	public void createNotice(NoticeDTO notice) throws Exception {
		noticeMapper.insertNotice(notice);
	}

	@Override
	@Transactional
	public void updateNotice(NoticeDTO notice) throws Exception {
		noticeMapper.updateNotice(notice);
	}

	@Override
	@Transactional
	public void deleteNotice(int postId) throws Exception {
		noticeMapper.deleteNotice(postId);
	}
}
