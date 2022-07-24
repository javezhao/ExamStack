package com.examstack.common.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.taskdefs.Exit;

import com.examstack.common.domain.exam.AnswerSheetItem;
import com.examstack.common.domain.question.ChoiceQuestion;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionContent;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.Topic;
import com.examstack.common.util.xml.Object2Xml;
import com.google.gson.Gson;



public class QuestionAdapter {

	private Question question;
	private QuestionContent questionContent;
	private AnswerSheetItem answerSheetItem;
	private QuestionQueryResult questionQueryResult;
	private String baseUrl;

	public String pointStrFormat(float point){
		
		if(point > (int)point){
			return point + "";
		}
		return (int)point + "";
	}
	/**
	 * 
	 * @param question
	 *            试题
	 * @param answerSheetItem
	 *            答题卡
	 * @param questionQueryResult
	 *            试题描述
	 */
	public QuestionAdapter(Question question, AnswerSheetItem answerSheetItem,
			QuestionQueryResult questionQueryResult, String baseUrl) {
		
		this.question = question;
		this.answerSheetItem = answerSheetItem;
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		this.questionContent = gson.fromJson(question.getContent(), QuestionContent.class);
		
		this.baseUrl = baseUrl;
	}
	
	public QuestionAdapter(AnswerSheetItem answerSheetItem,
			QuestionQueryResult questionQueryResult, String baseUrl) {
		this.answerSheetItem = answerSheetItem;
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		this.questionContent = gson.fromJson(question.getContent(), QuestionContent.class);
		this.baseUrl = baseUrl;
	}

	public QuestionAdapter(QuestionQueryResult questionQueryResult,
			String baseUrl) {
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		this.questionContent = gson.fromJson(questionQueryResult.getContent(), QuestionContent.class);
		this.baseUrl = baseUrl;
	}
	
	public QuestionAdapter()
	{
		
	}

	/**
	 * 组卷专用
	 * 
	 * @return
	 */
	public String getStringFromXML() {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p>").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		sb.append("<span>正确答案：</span>");
		if (questionQueryResult.getQuestionTypeId() == 3) {
			if (questionQueryResult.getAnswer().equals("T"))
				sb.append("<span class=\"answer_value\">").append("对").append("</span><br>");
			else if (questionQueryResult.getAnswer().equals("F"))
				sb.append("<span class=\"answer_value\">").append("错").append("</span><br>");
			else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		} else
			sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
					.append("</span><br>");
		sb.append("</div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-info\">");
		sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getReferenceName());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-warning\">");
		sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getAnalysis());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-success\">");
		sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getPointName());
		sb.append("</p></div></div>");
		sb.append("</div>");

		sb.append("</li>");
		return sb.toString();
	}

	public String getReportStringFromXML(){
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		sb.append("<span>正确答案：</span>");
		if (questionQueryResult.getQuestionTypeId() == 3) {
			if (questionQueryResult.getAnswer().equals("T"))
				sb.append("<span class=\"answer_value\">").append("对").append("</span><br>");
			else if (questionQueryResult.getAnswer().equals("F"))
				sb.append("<span class=\"answer_value\">").append("错").append("</span><br>");
			else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		} else
			sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
					.append("</span><br>");

		sb.append("<span>  你的解答：</span>");
		if (answerSheetItem.getQuestionTypeId() == 3) {
			if (answerSheetItem.getAnswer().trim().equals("T"))
				sb.append("<span>").append("对").append("</span>");
			else if (answerSheetItem.getAnswer().trim().equals("F"))
				sb.append("<span>").append("错").append("</span>");
			else
				sb.append("<span>").append(answerSheetItem.getAnswer())
						.append("</span>");
		} else
			sb.append("<span>").append(answerSheetItem.getAnswer())
					.append("</span>");
		sb.append("</div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-info\">");
		sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getReferenceName());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-warning\">");
		sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getAnalysis());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-success\">");
		sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getPointName());
		sb.append("</p></div></div>");
		sb.append("</div>");

		sb.append("</li>");
		return sb.toString();
	}
	/**
	 * 
	 * @param showAnswer
	 *            是否显示正确的答案。如果有答题卡，会显示用户的答案
	 * @param showPoint
	 *            是否显示分数
	 * @param showAnalysis
	 *            是否显示分析和来源
	 * @return
	 */
	public String getStringFromXML(boolean showAnswer, boolean showPoint,
			boolean showAnalysis) {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		if (showAnswer) {

			sb.append("<span>正确答案：</span>");
			if (questionQueryResult.getQuestionTypeId() == 3) {
				if (questionQueryResult.getAnswer().equals("T"))
					sb.append("<span class=\"answer_value\">").append("对").append("</span><br>");
				else if (questionQueryResult.getAnswer().equals("F"))
					sb.append("<span class=\"answer_value\">").append("错").append("</span><br>");
				else
					sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
							.append("</span><br>");
			} else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		}

		if (answerSheetItem != null) {

			sb.append("<span>  你的解答：</span>");
			if (answerSheetItem.getQuestionTypeId() == 3) {
				if (answerSheetItem.getAnswer().trim().equals("T"))
					sb.append("<span>").append("对").append("</span>");
				else if (answerSheetItem.getAnswer().trim().equals("F"))
					sb.append("<span>").append("错").append("</span>");
				else
					sb.append("<span>").append(answerSheetItem.getAnswer())
							.append("</span>");
			} else
				sb.append("<span>").append(answerSheetItem.getAnswer())
						.append("</span>");

		}
		sb.append("</div>");
		if (showAnalysis) {
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-info\">");
			sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getReferenceName());
			sb.append("</p></div></div>");
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-warning\">");
			sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getAnalysis());
			sb.append("</p></div></div>");
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-success\">");
			sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getPointName());
			sb.append("</p></div></div>");
			sb.append("</div>");
		}

		sb.append("</li>");
		return sb.toString();
	}
	
	
	/*
	 *  zwj add 
	 */
	public ChoiceQuestion getQuestion() {	
		StringBuilder sb = new StringBuilder();
		String answerDetail = "";     // 正确答案对应的具体内容 

		System.out.println(
				" QuestionAdapter  questionQueryResult.getQuestionId()---" 
				+ questionQueryResult.getQuestionId()); 
																								 
		System.out.println(" QuestionAdapter  questionContent.getTitle()---"																													
				+ questionContent.getTitle()); 
																												 
		System.out.println(" QuestionAdapter  questionContent.getTitleImg()---"																						
				+ questionContent.getTitleImg()); 
																													
		System.out.println(
				" QuestionAdapter  questionQueryResult.getAnswer()---" 
		+ questionQueryResult.getAnswer());
		System.out.println(
				" QuestionAdapter  questionQueryResult.getQuestionPoint()---" 
				+ questionQueryResult.getQuestionPoint()); 																		 

		Iterator<String> it1 = questionContent.getChoiceList().keySet().iterator();
		Map<String, String> mapChoice = new HashMap<String, String>();
		while (it1.hasNext()) {
			String key = it1.next(); // 选项 A
			String value = questionContent.getChoiceList().get(key); // 选项内容 =10.。

			System.out.println(
					" QuestionAdapter getQuestion- questionContent.getChoiceList().get(key()---"
			+ key + " == " + value); 
			
			//遍历 选项 --具体内容， 将正确答案的选项，对应的具体内容，保存到数据库，后面乱序后，要比较
		//	if(key.equals(questionQueryResult.getAnswer())) // 单选
			if(questionQueryResult.getAnswer().contains(key) ) // 多选，单选
			{
				// 保存正确答案对应的具体内容，后面要插入到数据库
				value.trim();
				answerDetail += value;
				
				if(questionQueryResult.getQuestionTypeId() == 2) {
					answerDetail += "|";   // 多选题，每个选项后面加 | 进行分割
				}
				
			}
			// 将数据库中的选项提取，存放在 map里			
			mapChoice.put(key, value);
		}
		
		System.out.println(" 这是正确答案。要插入数据库: "+ answerDetail);

		ChoiceQuestion choiceQuestions = new ChoiceQuestion(questionContent.getTitle(), mapChoice, questionQueryResult.getAnswer());
		choiceQuestions.setQuestionId(questionQueryResult.getQuestionId());
		choiceQuestions.setQuestionPoint(questionQueryResult.getQuestionPoint());
		choiceQuestions.setKnowledgePointId(questionQueryResult.getKnowledgePointId());
		choiceQuestions.setAnswerDetail(answerDetail);
		
		return choiceQuestions;
		
	}

	public String DisorderQuestions(ArrayList<ChoiceQuestion> choiceQuestionList, int choiceType)
	{
	//	System.out.println(" choiceQuestionList.len= "+ choiceQuestionList.size());
			
		//题目顺序打乱
		Collections.shuffle(choiceQuestionList);
		float questionPoint = 0;
		
		// 选项-答案乱序
		for (ChoiceQuestion question : choiceQuestionList) {			
			Topic random = randomQuestions(question.getOption(), question.getKey());
			question.setOption(random.getOption());   // 选项列表
			question.setKey(random.getKey());   // 正确答案
			questionPoint = question.getQuestionPoint();
		}

//		for (int idx = 0; idx < choiceQuestionList.size(); idx++) {
//			Map<String, String> option = choiceQuestionList.get(idx).getOption();
//			for (String key : option.keySet()) {
//				String value = option.get(key);
//				System.out.println(" DisorderQuestions  .key get(key()---"
//				    +key+ " == " +value); //zwj add 获取选项的内容
//			}
//		}
//		
		
		// 输出打印	
		StringBuilder sb = new StringBuilder(); 
		if(choiceType == 1) // 单选
		{
			sb.append(getUserSingleChoiceExamPaper(choiceQuestionList, questionPoint));
		}
		else if(choiceType == 2) // 多选
		{
			sb.append(getUserMultipleChoiceExamPaper(choiceQuestionList, questionPoint));
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 打乱选项和答案
	 * 
	 * @param option
	 * @param key
	 * @return
	 */
	public static Topic randomQuestions(Map<String, String> option, String key) {   
		Set<String> keySet = option.keySet();
		ArrayList<String> keyList = new ArrayList<String>(keySet);
		Collections.shuffle(keyList);
		HashMap<String, String> optionNew = new HashMap<String, String>();
		int idx = 0;
		String keyNew = "";
		for (String next : keySet) {
			String randomKey = keyList.get(idx++);
			if (key.contains(next)) {
				keyNew += randomKey;	
			}
			optionNew.put(randomKey, option.get(next));
		}
		 char[] arrayCh = keyNew.toCharArray(); //1，把sortStr转换为字符数组
	     Arrays.sort(arrayCh);//2，利用数组帮助类自动排序
	    String sortedKey=new String(arrayCh);  //加上这句
	    System.out.println("sortedKey 答案为---"+ sortedKey );
	    
		return new Topic(optionNew, sortedKey);
	}
	


	// 编写一道单选题的内容
	public String getUserSingleChoiceExamPaper(ArrayList<ChoiceQuestion> choiceQuestionList, float questionPoint){
		StringBuilder sb = new StringBuilder();
		System.out.println("getUserSingleChoiceExamPaper enter choiceQuestionList len= " +choiceQuestionList.size());
		for (int idx = 0; idx < choiceQuestionList.size(); idx++) {

			System.out.println("getKnowledgePointId== " +choiceQuestionList.get(idx).getKnowledgePointId());
			System.out.println("getQuestionTypeId== " +choiceQuestionList.get(idx).getQuestionTypeId());
			System.out.println("getQuestionPoint==  " +choiceQuestionList.get(idx).getQuestionPoint());
			
		sb.append("<li class=\"question qt-singlechoice\">");
		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");
		
		sb.append("singlechoice").append("</span>");
		sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(choiceQuestionList.get(idx).getKnowledgePointId()).append("</span>");

		sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(2).append("</span>");
	//	sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
		sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(1).append("</span>");
		
		sb.append("<span>[单选题]</span>");
		sb.append("<span class=\"question-point-content\">");
		sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionPoint)).append("</span><span>分)</span>");
		sb.append("</span>");
		sb.append("<span class=\"question-id\" style=\"display:none;\">")
			//	.append(questionQueryResult.getQuestionId())
				.append(choiceQuestionList.get(idx).getQuestionId())
				.append("</span>");
			
 
			
		sb.append("</div>");
		sb.append("<form class=\"question-body\">");
		sb.append("<p class=\"question-body-text\">").append(choiceQuestionList.get(idx).getName()); // 题目
		/*
		if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			*/
			sb.append("<ul class=\"question-opt-list\">");
			
			Map<String, String> option = choiceQuestionList.get(idx).getOption();
			for (String key : option.keySet()) {
				String value = option.get(key);
				System.out.println(" QuestionAdapter zwjad ().key get(key()---"
					    +key+ " == " +value); //zwj add 获取选项的内容

				sb.append("<li class=\"question-list-item\">");
				
				sb.append("<input type=\"radio\" value=\"")
						.append(key)	
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				
				sb.append("<span name=\"data-ref\" id=\"answer\"  value=\"") .append(value)
						.append( "\" style=\"display:none;\">");
				
				// 	.append("\" name=\"data-ref=\""    )
			//	.append(value)
				
//				if (questionContent.getChoiceImgList() != null)
//					if (questionContent.getChoiceImgList().containsKey(key))
//						sb.append(
//								"<img class=\"question-opt-img question-img\" src=\"")
//								.append(baseUrl)
//								.append(questionContent.getChoiceImgList().get(
//										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
	 
 			
		
		sb.append("</li>");
		}
		return sb.toString();
	}

	
	// 编写一道多选题的内容
	public String getUserMultipleChoiceExamPaper(ArrayList<ChoiceQuestion> choiceQuestionList, float questionPoint){
		StringBuilder sb = new StringBuilder();

		for (int idx = 0; idx < choiceQuestionList.size(); idx++) {

			System.out.println("getKnowledgePointId== " +choiceQuestionList.get(idx).getKnowledgePointId());
			System.out.println("getQuestionTypeId== " +choiceQuestionList.get(idx).getQuestionTypeId());
			System.out.println("getQuestionPoint==  " +choiceQuestionList.get(idx).getQuestionPoint());
			
		sb.append("<li class=\"question qt-multiplechoice\">");
		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");
		
		sb.append("multiplechoice").append("</span>");
		sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(choiceQuestionList.get(idx).getKnowledgePointId()).append("</span>");

		sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(2).append("</span>");
	//	sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
		sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(1).append("</span>");
		
		sb.append("<span>[多选题]</span>");
		sb.append("<span class=\"question-point-content\">");
		sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionPoint)).append("</span><span>分)</span>");
		sb.append("</span>");
		sb.append("<span class=\"question-id\" style=\"display:none;\">")
			//	.append(questionQueryResult.getQuestionId())
				.append(choiceQuestionList.get(idx).getQuestionId())
				.append("</span>");
			
		sb.append("</div>");
		sb.append("<form class=\"question-body\">");
		sb.append("<p class=\"question-body-text\">").append(choiceQuestionList.get(idx).getName()); // 题目
		/*
		if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			*/
			sb.append("<ul class=\"question-opt-list\">");
			
			Map<String, String> option = choiceQuestionList.get(idx).getOption();
			for (String key : option.keySet()) {
			
				String value = option.get(key);
				System.out.println(" QuestionAdapter zwjad ().key get(key()---"
					    +key+ " == " +value); //zwj add 获取选项的内容

				sb.append("<li class=\"question-list-item\">");
				
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)	
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				
				sb.append("<span name=\"data-ref\" id=\"answer\"  value=\"") .append(value)
						.append( "\" style=\"display:none;\">");
				
				// 	.append("\" name=\"data-ref=\""    )
			//	.append(value)
				
//				if (questionContent.getChoiceImgList() != null)
//					if (questionContent.getChoiceImgList().containsKey(key))
//						sb.append(
//								"<img class=\"question-opt-img question-img\" src=\"")
//								.append(baseUrl)
//								.append(questionContent.getChoiceImgList().get(
//										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");	 				
			sb.append("</li>");
		}
		return sb.toString();
	}

	

	public String getUserExamPaper() {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			
			System.out.println(" QuestionAdapter zwjadd questionQueryResult.getKnowledgePointId()---"+questionQueryResult.getKnowledgePointId()); //zwj add
			System.out.println(" QuestionAdapter zwjadd questionQueryResult.getQuestionTypeId()---"+questionQueryResult.getQuestionTypeId()); //zwj add
			System.out.println(" QuestionAdapter zwjadd questionQueryResult.getQuestionPoint()---"+questionQueryResult.getQuestionPoint()); //zwj add

			System.out.println(" QuestionAdapter zwjadd questionQueryResult.getQuestionId()---"+questionQueryResult.getQuestionId()); //zwj add
			System.out.println(" QuestionAdapter zwjadd questionContent.getTitle()---"+questionContent.getTitle()); //zwj add
			System.out.println(" QuestionAdapter zwjadd questionContent.getTitleImg()---"+questionContent.getTitleImg()); //zwj add
			System.out.println(" QuestionAdapter zwjadd questionQueryResult.getAnswer()---"+questionQueryResult.getAnswer()); //zwj add

			
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();  // 选项 A
				String value = questionContent.getChoiceList().get(key); // 选项内容 =10.。
				
				System.out.println(" QuestionAdapter zwjadd questionContent.getChoiceList().get(key()---"
				    +key+ " == " +value); //zwj add 获取选项的内容

				
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			sb.append("<p class=\"question-body-text\">").append(questionContent.getTitle());
			if (questionContent.getTitleImg() != null)
				if (!questionContent.getTitleImg().trim().equals(""))
					sb.append(
							"<img class=\"question-content-img question-img\" src=\"")
							.append(baseUrl)
							.append(questionContent.getTitleImg())
							.append("\" />");
			sb.append("</p>");
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;

		}
		sb.append("</li>");
		return sb.toString();
	}
}
