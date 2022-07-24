package com.examstack.management.controller.page.teacher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.user.Group;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.Page;
import com.examstack.common.util.PagingUtil;
import com.examstack.management.persistence.UserMapper;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.QuestionService;
import com.examstack.management.service.UserService;
import com.mysql.cj.log.Log;



import com.examstack.management.persistence.UserMapper;


@Controller
public class UserPageTeacher {
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	
	/**
	 * 学员管理 -- 单位管理员、teacher 只获取和自己同部门的学员信息
	 * @return
	 */
	
	@RequestMapping(value = { "teacher/user/student-list" }, method = RequestMethod.GET)
	public String userListPage(Model model, HttpServletRequest request) {

		System.out.println("0000000000000");
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("mmmmmmmmmmmm");
		
		String userName = userInfo.getUsername();
		int userID =userInfo.getUserid();
		
		System.out.println("=userListPage==="+  userName + "   "+userInfo.getUserid());
		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(20);
	 
		int groupId=0;
 
		int depID =0; // teacher 二级管理员所在单位编号
		
		depID = userService.getUserDepartID(userID);
		System.out.println("depID..." + depID);
		
		String searchStr = "";
	/*	
		if(request.getParameter("groupId") != null){
			groupId = Integer.parseInt(request.getParameter("groupId"));		     
			searchStr = request.getParameter("searchStr");
		}
		*/
		List<User> userList = userService.getUserListByParams(depID, "ROLE_STUDENT", searchStr, page);
			
		
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "", "admin/user-list");
	//	List<Group> groupList = userService.getGroupListByUserId(userInfo.getUserid(), null);
	//	model.addAttribute("groupId", groupId);
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
	//	model.addAttribute("groupList", groupList);
		return "user-list2";
	}
	
	
	/**
	 * 学员管理  -- 获取到所有学员信息, 包括不是本单位的
	 * @return
	 */
	/*
	@RequestMapping(value = { "teacher/user/student-list" }, method = RequestMethod.GET)
	public String userListPage(Model model, HttpServletRequest request) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(20);
		int groupId = 0;
		String searchStr = "";
		if(request.getParameter("groupId") != null){
			groupId = Integer.parseInt(request.getParameter("groupId"));
			searchStr = request.getParameter("searchStr");
		}
		List<User> userList = userService.getUserListByGroupIdAndParams(groupId, "ROLE_STUDENT", searchStr, page);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "", "admin/user-list");
		List<Group> groupList = userService.getGroupListByUserId(userInfo.getUserid(), null);
		model.addAttribute("groupId", groupId);
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("groupList", groupList);
		return "user-list";
	}
	*/
	
	/**
	 * 考试历史
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "teacher/user/student-examhistory" }, method = RequestMethod.GET)
	public String examHistoryPage(Model model, HttpServletRequest request){
		return "";
	}
	
	/**
	 * 学员资料
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "teacher/user/student-profile" }, method = RequestMethod.GET)
	public String studentProfilePage(Model model, HttpServletRequest request){
		return "";
	}
	
	
	@RequestMapping(value = { "teacher/user/inner/user-list/{groupId}-{authority}" }, method = RequestMethod.GET)
	public String showUserListInnerAdminPage(Model model, HttpServletRequest request, @PathVariable Integer groupId, @PathVariable String authority) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(15);
		String searchStr = "";
		if(request.getParameter("searchStr") != null){
			searchStr = request.getParameter("searchStr");
		}
		

		int userID =userInfo.getUserid();
		
		System.out.println("==showUserListInnerAdminPage==userID  " + "   "+userInfo.getUserid());

		int depID =0; // teacher 二级管理员所在单位编号
		
		depID = userService.getUserDepartID(userID);
		System.out.println("showUserListInnerAdminPage depID..." + depID);
	//	List<User> userList = userService.getUserListByGroupIdAndParams(groupId, authority, searchStr, page);
		List<User> userList = userService.getUserListByParams(depID, "ROLE_STUDENT", searchStr, page);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "&searchStr=" + searchStr, "teacher/user/inner/user-list2/" + groupId + "-" + authority);
		
		List<Group> groupList = userService.getGroupListByUserId(userInfo.getUserid(), null);
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
		
		model.addAttribute("groupList", groupList);
		model.addAttribute("groupId", groupId);
		
		model.addAttribute("authority", "ROLE_STUDENT");
		model.addAttribute("searchStr", searchStr);
		return "inner/user-list2";
	}
	
	/*
	@RequestMapping(value = { "teacher/user/inner/user-list/{groupId}-{authority}" }, method = RequestMethod.GET)
	public String showUserListInnerAdminPage(Model model, HttpServletRequest request, @PathVariable Integer groupId, @PathVariable String authority) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int index = 1;
		if (request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(15);
		String searchStr = "";
		if(request.getParameter("searchStr") != null){
			searchStr = request.getParameter("searchStr");
		}
		List<User> userList = userService.getUserListByGroupIdAndParams(groupId, authority, searchStr, page);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "&searchStr=" + searchStr, "teacher/user/inner/user-list2/" + groupId + "-" + authority);
		
		List<Group> groupList = userService.getGroupListByUserId(userInfo.getUserid(), null);
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
		model.addAttribute("groupList", groupList);
		model.addAttribute("groupId", groupId);
		model.addAttribute("authority", "ROLE_STUDENT");
		model.addAttribute("searchStr", searchStr);
		return "inner/user-list";
	} */

	/**
	 * 添加用户界面
	 * 暂时保留，可能废弃
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teacher/user/add-user", method = RequestMethod.GET)
	private String addUserPage(Model model, HttpServletRequest request) {

		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);
		return "add-user";
	}
}
