package yerp.common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import yerp.common.util.ConstantUtil;
import yerp.common.util.StringUtil;

public class LoginCheckInterceptor implements HandlerInterceptor {
	private String domain = null;
	
	public LoginCheckInterceptor(String domain) {
		this.domain = domain;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute(ConstantUtil.SESSION_USER_ID);
		Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		boolean pageFlag = true;
		
		if(request.getQueryString() != null) {
			for(String page : ConstantUtil.PAGE_ID_ARRAY) {
				if(request.getQueryString().indexOf(page) > 0 || request.getRequestURI().indexOf(page) > 0) {
					pageFlag = false;
					break;
				}
			}
		}
		
		JSONObject result = new JSONObject();
		if(!StringUtil.isNull(request.getHeader("type")) && request.getHeader("type").equals("ajax")) {
			if("Y".equals(session.getAttribute("directPage"))) {
				return true;
			}
		}
		
//		if(pageFlag) {
//			if (StringUtil.isNull(userId)) {
//				if (!StringUtil.isNull(request.getHeader("type")) && request.getHeader("type").equals("ajax")) {
//					if(pageFlag) {
//						result.put(ConstantUtil.ERROR, "loginRequired");
//						response.setHeader("Content-Type", "application/json; charset=UTF-8");
//						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//						response.getWriter().write(result.toJSONString());
//					}
//					else {
//						return true;
//					}
//				} else {
//					response.sendRedirect("/?w2xPath=/login.xml");
//				}
//				return false;
//			}
//		}
		
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute(ConstantUtil.SESSION_USER_ID);
		Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		boolean pageFlag = true;
		
		if(request.getQueryString() != null) {
			for(String page : ConstantUtil.PAGE_ID_ARRAY) {
				if(request.getQueryString().indexOf(page) > 0 || request.getRequestURI().indexOf(page) > 0) {
					pageFlag = false;
					break;
				}
			}
		}
		
		if(!pageFlag) {
			session.setAttribute("directPage", "Y");
		}
		else {
			session.setAttribute("directPage", "N");
		}
	}
}
