package org.embed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.embed.dto.NoticeDTO;

@Mapper
public interface NoticeMapper {
	
	List<NoticeDTO> selectNoticeList() throws Exception;
    void insertNotice(NoticeDTO notice) throws Exception;
    void updateNotice(NoticeDTO notice) throws Exception;
    void deleteNotice(int postId) throws Exception;
    NoticeDTO selectNoticeDetail(int postId) throws Exception;
}