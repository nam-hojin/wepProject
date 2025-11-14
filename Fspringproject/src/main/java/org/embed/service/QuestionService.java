package org.embed.service;

import java.util.List;

import org.embed.dto.QuestionDTO;

public interface QuestionService {
    List<QuestionDTO> getQuestionList();
    QuestionDTO getQuestionDetail(int postId);
    void createQuestion(QuestionDTO question);
    void updateQuestion(QuestionDTO question);
    void deleteQuestion(int postId);
}