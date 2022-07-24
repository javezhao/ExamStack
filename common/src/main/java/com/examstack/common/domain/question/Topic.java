package com.examstack.common.domain.question;

import java.util.Map;

/*
 *  zwj add 0717
 */

//处理后选项答案临时对象 Topic

public class Topic {

	private Map<String, String> option; // 选项；A、B、C、D
	private String key; // 答案；B
	
	 
		private int questionId;

		private String analysis;
		private int questionTypeId;
		private String referenceName;
		private String pointName;
		private String fieldName;
		private float questionPoint;
		private String examingPoint;
		

		 public int getKnowledgePointId() {
			return knowledgePointId;
		}

		public void setKnowledgePointId(int knowledgePointId) {
			this.knowledgePointId = knowledgePointId;
		}

		private int knowledgePointId;
		 
			
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

	public Topic() {
	}

	public Topic(Map<String, String> option, String key) {
		this.option = option;
		this.key = key;
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
