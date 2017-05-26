package com.chujiu.manager.system.controller;

import com.chujiu.dto.MenuTreeNode;
import com.chujiu.model.WeiXinContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created on   2016年5月16日
 * Title:       初九数据科技后台管理系统_[公共]
 * Description: [项目跟路径访问处理 ]
 * Copyright:   Copyright (c) 2016
 * Company:     初九数据科技（上海）有限公司
 * Department:  研发部
 * @author:     suliang
 * @version:    1.0
*/
@Controller
@RequestMapping("/")
public class IndexController {

	/**
	 * Created on   2016年5月16日
	 * Discription: [欢迎页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:23:26
	 */
	@RequestMapping("index")
	public String welcome() {
		return "system/welcome";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [403 无权访问 错误信息页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:23:39
	 */
	@RequestMapping("accessDenied")
	public String accessDenied(){
		return "system/accessDenied";
	}
	
	/**
	 * Created on   2016年5月16日
	 * Discription: [session超时页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:23:48
	 */
	@RequestMapping("sessionTimeout")
	public String sessionTimeout(HttpServletRequest request,HttpServletResponse response){
		boolean ajaxflag = request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
		// ajax请求时，session超时处理
		if (ajaxflag) {
			String jsonObject = "{\"sessionstatus\":\"timeout\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(jsonObject);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null != out){
					out.flush();
					out.close();
				}
			}
		}
		return "system/sessionTimeout";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [失效页面]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午2:24:12
	 */
	@RequestMapping("expired")
	public String expired(HttpServletRequest request,HttpServletResponse response){
		boolean ajaxflag = request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
		// 同一账号，在非同一浏览器或非同一电脑登录互踢
		if (ajaxflag) {
			String jsonObject = "{\"expiredtatus\":\"expired\"}";
			response.setContentType("application/json");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(jsonObject);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null != out){
					out.flush();
					out.close();
				}
			}
		}
		return "system/expired";
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [404错误处理]
	 * @return String
	 * @author:     suliang
	 * @update:     2016年5月16日 下午5:16:49
	 */
	@RequestMapping("pageNotFound")
	public String pageNotFound() {
		return "system/pageNotFound";
	}


	/**
	 * Created on   2016年5月16日
	 * Discription: [buildElement]
	 * @param parentsMap
	 * @param child
	 * @return MenuTreeNode
	 * @author:     linlong
	 * @update:     2016年5月16日 下午6:39:03
	 */
	private MenuTreeNode buildElement(Map<Integer, MenuTreeNode> parentsMap, MenuTreeNode child) {
		MenuTreeNode parent = parentsMap.get(child.getParentId());
		if (parent.getNodes() == null) {
			parent.setNodes(new ArrayList<MenuTreeNode>());
		}
		if (!parent.getNodes().contains(child)) {
			parent.getNodes().add(child);
		}
		if (parent.getParentId() != 1 && parent.getId() != 1) {
			return buildElement(parentsMap, parent);
		}
		return parent;
	}

	/**
	 * Created on   2016年5月16日
	 * Discription: [sortMenu]
	 * @param root void
	 * @author:     linlong
	 * @update:     2016年5月16日 下午6:39:53
	 */
	private void sortMenu(MenuTreeNode root) {
		if (root.getNodes() != null) {
			Collections.sort(root.getNodes());
			for (MenuTreeNode node : root.getNodes()) {
				sortMenu(node);
			}
		}
	}

}