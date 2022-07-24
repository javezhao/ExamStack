package com.examstack.common.domain.exam;

public class AnswerSheetItem {
	private float point;
	private int questionTypeId;
	private String answer; // 填写的答案 选项A
	private int questionId;
	private String comment;
	private boolean right;

	public String getAnswerRight() {
		return answerRight;
	}

	public void setAnswerRight(String answerRight) {
		this.answerRight = answerRight;
	}

	private String answerRight; // 正确的答案 选项A  zwj add
	private String answerDetail; // 答案 选项对应的具体内容 zwj add
	
	public String getAnswerDetail() {
		return answerDetail;
	}

	public void setAnswerDetail(String answerDetail) {
		this.answerDetail = answerDetail;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public float getPoint() {
		return point;
	}

	public void setPoint(float point) {
		this.point = point;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



}
