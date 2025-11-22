package org.embed.service;

import java.util.List;

import org.embed.dto.NoticeDTO;

public interface NoticeService {

	List<NoticeDTO> getNoticeList() throws Exception;

	NoticeDTO getNoticeDetail(int postId) throws Exception;

	void createNotice(NoticeDTO notice) throws Exception;

	void updateNotice(NoticeDTO notice) throws Exception;

	void deleteNotice(int postId) throws Exception;
}
