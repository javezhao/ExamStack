package com.examstack.management.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.question.KnowledgePoint;
import com.examstack.common.domain.user.Department;
import com.examstack.common.domain.user.Group;
import com.examstack.common.domain.user.Role;
import com.examstack.common.domain.user.User;
import com.examstack.common.util.Page;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.common.util.file.ExcelUtil;
import com.examstack.management.persistence.UserMapper;
import com.examstack.management.security.UserInfo;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午8:21:31
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserMapper userMapper;
	
	
	
	/**
	 * 导入学生
	 * 
	 * @param filePath
	 * @param username
	 * @param fieldId
	 */
//	public void uploadStudents(String filePath, String username, HashMap<String,Role> roleMap) {
	public void uploadStudents(String filePath, UserInfo userInfo) {

		String strPath = ",webapps,files,user," + userInfo.getUsername() + ",tmp";
		
		System.out.println("UserServiceImpl  uploadStudents enter zwj....");

		filePath = System.getProperty("catalina.base") + strPath.replace(',', File.separatorChar) + File.separatorChar
				+ filePath;
		
		System.out.println("UserServiceImpl  uploadStudentstry zwj....filePath=  " + filePath);

		
	//	Map<String, KnowledgePoint> pointMap = this.getKnowledgePointMapByFieldId(fieldId, null);
		int index = 2;
		try {
			System.out.println("UserServiceImpl  uploadStudentstry zwj....");

			
			List<Map<String, String>> studentMapList = ExcelUtil.ExcelToList(filePath);
			for (Map<String, String> map : studentMapList) {

	//			System.out.println("UserServiceImpl  uploadStudentstry   enter for zwj....");

	//			System.out.println(map);
	//			System.out.println(map.get("用户名"));

				
				User user = new User();
				user.setUserName(map.get("用户名").trim());
				user.setTrueName(map.get("姓名").trim());
				user.setEmail(map.get("email"));
				user.setPhoneNum(map.get("手机").trim());
				user.setPassword(map.get("密码").trim());
				user.setDepartment(map.get("所属单位").trim());
				user.setRoles("ROLE_STUDENT");	

				user.setCreateTime(new Date());
				String password = user.getPassword() + "{" + user.getUserName().toLowerCase() + "}";
				PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
				String resultPassword = passwordEncoder.encode(password);
				user.setPassword(resultPassword);
				user.setEnabled(true);
				user.setCreateBy(userInfo.getUserid());
				user.setUserName(user.getUserName().toLowerCase());
				
			//	user.setDepartment(user.getDepartment().trim());
				
				if(user.getUserName() !="" && user.getDepartment() != "")
				{				
					this.addUsers(user, userInfo.getRoleMap());
					index++;
				}

			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UserServiceImpl  uploadStudents successful total= " + index);

		
	}
	
	
	@Override
	@Transactional
	public void addUsers(User user,HashMap<String,Role> roleMap) {

		System.out.println("UserServiceImpl addUsers enter    "); 


		System.out.println("id = " +user.getUserId()); 
		System.out.println("username = " +user.getUserName()); 

		
		
		int userId = -1;
		userMapper.insertUser(user); // 插入到用户表user
		
		System.out.println("UserServiceImpl addUsers insert end...    "); 

		
		userId = user.getUserId();
		
		System.out.println("UserServiceImpl addUsers zwj  userId  "+  userId); 
		System.out.println("UserServiceImpl addUsers zwj  getRoleId  "+  roleMap.get("ROLE_STUDENT").getRoleId()); 


		userMapper.grantUserRole(userId, roleMap.get("ROLE_STUDENT").getRoleId()); // 插入到角色表 et_user_2_role

		// 通过 dep_name 获取 dep_id
		System.out.println("UserServiceImpl addUsers zwj  getDepartment  "+  user.getDepartment()); 
		
		int depId = userMapper.getdepIDbydepname(user.getDepartment());
		System.out.println("UserServiceImpl addUsers zwj  getDepId  "+  depId ); 

		if (depId != 0 && depId != -1)
		{
			userMapper.addUser2Dep(userId, depId); // 插入到所在单位 et_user_2_department
			System.out.println("UserServiceImpl addUsers zwj  addUser2Dep....  "); 
		}			
	}
	
	

	@Override
	@Transactional
	public int addUser(User user,String authority,int groupId,HashMap<String,Role> roleMap) {
		try {
			System.out.println("UserServiceImpl addUser zwj  authority  "+  authority); // ROLE_STUDENT
			System.out.println("UserServiceImpl addUser zwj  groupId  "+  groupId); // 0
			
			int userId = -1;
			
			Message message = new Message();

			userMapper.insertUser(user);
			
			try {
				userMapper.insertUser(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block

				if(e.getMessage().contains(user.getUserName())){
					message.setResult("duplicate-username");
					message.setMessageInfo("重复的用户名");
				} else if(e.getMessage().contains(user.getNationalId())){
					message.setResult("duplicate-national-id");
					message.setMessageInfo("重复的身份证");
				} else if(e.getMessage().contains(user.getPhoneNum())){
					message.setResult("duplicate-phone");
					message.setMessageInfo("重复的电话");
				} else{
					message.setResult(e.getCause().getMessage());
					e.printStackTrace();
				}
			}
					
			userId = user.getUserId();
			userMapper.grantUserRole(userId, roleMap.get(authority).getRoleId());
			
			System.out.println("UserServiceImpl addUser zwj  getDepId  "+  user.getDepId() ); 

			
			if(user.getDepId() != 0 && user.getDepId() != -1)
				userMapper.addUser2Dep(userId, user.getDepId());
			
 
			return userId;
		} catch (Exception e) {
			String cause = e.getCause().getMessage();
			throw new RuntimeException(cause);
		}
	}
	

	
	
	@Override
	public List<User> getUserListByRoleId(int roleId,Page<User> page) {
		// TODO Auto-generated method stub
		List<User> userList = userMapper.getUserListByRoleId(roleId, page);
		return userList;
	}
	
	@Override
	@Transactional
	// zwj modify
	public void updateUser(User user, String oldPassword) {
		// TODO Auto-generated method stub
		

		System.out.println("UserServiceImpl zwj  updateUser before getUserId "+user.getUserId());
		System.out.println("UserServiceImpl zwj updateUser before getDepId "+user.getDepId());
		try {
			userMapper.updateUser(user, oldPassword);

			System.out.println("UserServiceImpl zwj  updateUser getUserId "+user.getUserId());
			System.out.println("UserServiceImpl zwj updateUser getNationalId "+user.getNationalId());
			System.out.println("UserServiceImpl zwj updateUser getEmail "+user.getEmail());

			System.out.println("UserServiceImpl zwj  updateUser getTrueName "+user.getTrueName());

			System.out.println("UserServiceImpl zwj  updateUser getPhoneNum "+user.getPhoneNum());
			System.out.println("UserServiceImpl zwj  updateUser getDepartment "+user.getDepartment());

		 
			/*
			if(user.getDepId() != -1){
				userMapper.deleteUser2Dep(user.getUserId());
				userMapper.addUser2Dep(user.getUserId(), user.getDepId());
			}	
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	/* original
	@Override
	@Transactional
	public void updateUser(User user, String oldPassword) {
		// TODO Auto-generated method stub
		

		System.out.println("UserServiceImpl zwj  updateUser before getUserId "+user.getUserId());
		System.out.println("UserServiceImpl zwj updateUser before getDepId "+user.getDepId());
		try {
			userMapper.updateUser(user, oldPassword);

			System.out.println("UserServiceImpl zwj  updateUser getUserId "+user.getUserId());
			System.out.println("UserServiceImpl zwj updateUser getDepId "+user.getDepId());
			System.out.println("UserServiceImpl zwj updateUser getCompany "+user.getCompany());


			System.out.println("UserServiceImpl zwj  updateUser getDepartment "+user.getDepartment());
		 
			
			if(user.getDepId() != -1){
				userMapper.deleteUser2Dep(user.getUserId());
				userMapper.addUser2Dep(user.getUserId(), user.getDepId());
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	*/
	
	// zwj add 
	@Override
	public List<Department> getDepartListByUserId(int userId, Page<Department> page) {
		// TODO Auto-generated method stub
		return userMapper.getDepartListByUserId(userId, page);

	}

	@Override
	public List<Group> getGroupListByUserId(int userId, Page<Group> page) {
		// TODO Auto-generated method stub
		return userMapper.getGroupListByUserId(userId, page);
	}

	@Override
	public void addGroup(Group group) {
		// TODO Auto-generated method stub
		userMapper.addGroup(group);
	}

	@Override
	public HashMap<String, Role> getRoleMap() {
		// TODO Auto-generated method stub
		List<Role> roleList = userMapper.getRoleList();
		HashMap<String,Role> map = new HashMap<String,Role>();
		for(Role r : roleList){
			map.put(r.getAuthority(), r);
		}
		return map;
	}

	@Override
	public void changeUserStatus(List<Integer> idList,boolean enabled) {
		// TODO Auto-generated method stub
		userMapper.changeUserStatus(idList, enabled);
	}

	@Override
	public void updateGroup(int groupId, String groupName) {
		// TODO Auto-generated method stub
		userMapper.updateGroup(groupId, groupName);
	}

	@Override
	public void deleteGroup(int groupId) {
		// TODO Auto-generated method stub
		userMapper.deleteGroup(groupId);
	}

	@Override
	public void addUserGroup(int userId, int groupId) {
		// TODO Auto-generated method stub
		userMapper.addUserGroup(userId, groupId);
	}
	
	/*
	 * zwj add
	 * 
	 */
	@Override
	public List<User> getUserListByDepartmentIdAndParams(int departmentId, String authority, String searchStr, Page<User> page){
		return userMapper.getUserListByDepartmentIdAndParams(departmentId, authority, searchStr, page);

	}
	

	@Override
	public List<User> getUserListByGroupIdAndParams(int groupId, String authority, String searchStr, Page<User> page) {
		// TODO Auto-generated method stub
		return userMapper.getUserListByGroupIdAndParams(groupId, authority, searchStr, page);
	}

	// zwj add 
	@Override
	public List<User> getUserListByParams(int depID, String authority, String searchStr, Page<User> page) {
		// TODO Auto-generated method stub
		return userMapper.getUserListByParams(depID, authority, searchStr, page);
	}
	
	public int getUserDepartID(int  userID) {
		// TODO Auto-generated method stub
		return userMapper.getUserDepartID(userID);
	}
	

	
	@Override
	public void addUsers2Group(String[] userNames, int groupId,HashMap<String,Role> roleMap) {
		// TODO Auto-generated method stub
		List<User> userList = userMapper.getUserByNames(userNames,roleMap.get("ROLE_STUDENT").getRoleId());
		List<Integer> idList = new ArrayList<Integer>();
		for(User user : userList){
			idList.add(user.getUserId());
		}
		userMapper.addUsers2Group(idList, groupId);
	}

	@Override
	public void deleteUserGroup(int userId, int groupId, int managerId) {
		// TODO Auto-generated method stub
		userMapper.deleteUserGroup(userId, groupId, managerId);
	}

	@Override
	public List<Department> getDepList(Page<Department> page) {
		// TODO Auto-generated method stub
		return userMapper.getDepList(page);
	}

	@Override
	public void addDep(Department dep) {
		// TODO Auto-generated method stub
		userMapper.addDep(dep);
	}

	@Override
	public void updateDep(Department dep) {
		// TODO Auto-generated method stub
		userMapper.updateDep(dep);
	}

	@Override
	public void deleteDep(int depId) {
		// TODO Auto-generated method stub
		userMapper.deleteDep(depId);
	}

	@Override
	public void updateUserPwd(String userName, String password, String authority) throws Exception {
		// TODO Auto-generated method stub
		User user = userMapper.getUserByName(userName);
		if(user.getRoles().contains(authority) && !"ROLE_ADMIN".equals(authority))
			throw new Exception("教师只能更新学员的密码！");
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		password = passwordEncoder.encode(password + "{" + userName + "}");
		User tmpUser = new User();
		tmpUser.setUserId(user.getUserId());
		tmpUser.setPassword(password);
		userMapper.updateUser(tmpUser, null);
		
	}




}
