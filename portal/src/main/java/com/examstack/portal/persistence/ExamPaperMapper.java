package com.examstack.portal.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.util.Page;

public interface ExamPaperMapper {

	public List<ExamPaper> getExamPaperList(@Param("searchStr") String searchStr, @Param("page") Page<ExamPaper> page);
	
	public void insertExamPaper(ExamPaper examPaper);
	
	public ExamPaper getExamPaperById(int examPaperId);
	
	public void updateExamPaper(ExamPaper examPaper);
	
	public void deleteExamPaper(int id);
	
	public List<ExamPaper> getEnabledExamPaperList(@Param("userName") String userName,@Param("page") Page<ExamPaper> page);

	/**
	 * 更新et_question，将正确答案具体内容增加进去 zwj
	 * @param questionId
	 * @param answerDetail
	 */
	// zwj add
	public void updateAnswerDetail(@Param("questionId") int questionId,  @Param("answerDetail")String answerDetail);

//	public String getAnswerDetail(@Param("questionId") int questionId);
	public String getAnswerDetail(int questionId);

}
