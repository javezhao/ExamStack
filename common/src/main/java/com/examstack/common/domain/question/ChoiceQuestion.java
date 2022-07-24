package com.examstack.common.domain.question;

import java.util.List;
import java.util.Map;

/**
 * zwj add 0717
 *
 * 单选题
 */
public class ChoiceQuestion {

	private String name; // 题目
	private Map<String, String> option; // 选项；A、B、C、D
	private String key; // 答案；ABC
	private int knowledgePointId;
	private String answerDetail; // 正确答案对应的具体内容
	private List<String> answerDetails; // 多选
	private int questionId;

	private String analysis;
	private int questionTypeId;
	private String referenceName;
	private String pointName;
	private String fieldName;
	private float questionPoint;
	private String examingPoint;


	public ChoiceQuestion(String name, Map<String, String> option, String key) {
		this.name = name;
		this.option = option;
		this.key = key;
	}

	
	public List<String> getAnswerDetails() {
		return answerDetails;
	}

	public void setAnswerDetails(List<String> answerDetails) {
		this.answerDetails = answerDetails;
	}

	public String getAnswerDetail() {
		return answerDetail;
	}

	public void setAnswerDetail(String answerDetail) {
		this.answerDetail = answerDetail;
	}

	public int getKnowledgePointId() {
		return knowledgePointId;
	}

	public void setKnowledgePointId(int knowledgePointId) {
		this.knowledgePointId = knowledgePointId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	public int getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public float getQuestionPoint() {
		return questionPoint;
	}

	public void setQuestionPoint(float questionPoint) {
		this.questionPoint = questionPoint;
	}

	public String getExamingPoint() {
		return examingPoint;
	}

	public void setExamingPoint(String examingPoint) {
		this.examingPoint = examingPoint;
	}

	public ChoiceQuestion() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getOption() {
		return option;
	}

	public void setOption(Map<String, String> option) {
		this.option = option;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
