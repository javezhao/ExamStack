package com.examstack.portal.service;


import org.apache.ibatis.annotations.Param;

import com.examstack.common.domain.exam.ExamPaper;

public interface ExamPaperService {
	
	/**
	 * 获取一张试卷
	 * @param examPaperId
	 * @return
	 */
	public ExamPaper getExamPaperById(int examPaperId);
	
	
	/**
	 * 更新et_question，将正确答案具体内容增加进去 zwj
	 * @param questionId
	 * @param answerDetail
	 */
	public void updateAnswerDetail(int questionId, String answerDetail);
	
	public String getAnswerDetail(int questionId);
	
}
