package org.embed.service;

import java.util.List;

import org.embed.dto.QuestionDTO;
import org.embed.dto.UsDTO;
import org.embed.mapper.QuestionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;

    public QuestionServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public List<QuestionDTO> getQuestionList() {
        return questionMapper.getQuestionList();
    }

    @Override
    public QuestionDTO getQuestionDetail(int postId) {
        return questionMapper.getQuestionDetail(postId);
    }

    @Override
    @Transactional
    public void createQuestion(QuestionDTO question, UsDTO loginUser) {
        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            throw new RuntimeException("관리자 권한이 없습니다.");
        }
        questionMapper.createQuestion(question);
    }

    @Override
    @Transactional
    public void updateQuestion(QuestionDTO question, UsDTO loginUser) {
        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            throw new RuntimeException("관리자 권한이 없습니다.");
        }
        questionMapper.updateQuestion(question);
    }

    @Override
    @Transactional
    public void deleteQuestion(int postId, UsDTO loginUser) {
        if (loginUser == null || !"ADMIN".equalsIgnoreCase(loginUser.getRole())) {
            throw new RuntimeException("관리자 권한이 없습니다.");
        }
        questionMapper.deleteQuestion(postId);
    }
}
