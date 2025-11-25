package org.embed.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.embed.dto.QuestionDTO;

@Mapper
public interface QuestionMapper {
	List<QuestionDTO> getQuestionList();

	QuestionDTO getQuestionDetail(int postId);

	void createQuestion(QuestionDTO question);

	void updateQuestion(QuestionDTO question);

	void deleteQuestion(int postId);
}