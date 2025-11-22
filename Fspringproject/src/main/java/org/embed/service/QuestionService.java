package org.embed.service;

import java.util.List;

import org.embed.dto.QuestionDTO;
import org.embed.dto.UsDTO;

public interface QuestionService {
    List<QuestionDTO> getQuestionList();

    QuestionDTO getQuestionDetail(int postId);

    void createQuestion(QuestionDTO question, UsDTO loginUser);

    void updateQuestion(QuestionDTO question, UsDTO loginUser);

    void deleteQuestion(int postId, UsDTO loginUser);
}
