package yerp.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import yerp.common.annotation.CommonParam;

@Controller
public class PageController {

	@GetMapping(value = "/page/ssoLogin")
	public String ssoLoginPage(@CommonParam Map<String, Object> parameter, HttpSession session,
			HttpServletRequest request) {
		return "ssoLogin";
	}

	@GetMapping(value = "/page/ssoLoginPopup")
	public String ssoLoginPopupPage(@CommonParam Map<String, Object> parameter, HttpSession session,
			HttpServletRequest request) {
		return "ssoLoginPopup";
	}

	@PostMapping(value = "/page/report")
	@CrossOrigin(origins = "http://localhost:9090")
	public String reportPage(@CommonParam Map<String, Object> parameter, HttpSession session,
			HttpServletRequest request) {
		return "report";
	}

	@GetMapping(value = "/")
	public String index(@CommonParam Map<String, Object> parameter, HttpSession session,
			HttpServletRequest request, Model model) {
//		String jspUrl = request.getRequestURI();
		String movePage = request.getParameter("w2xPath");
		String userId = (String) session.getAttribute("SESSION_USER_IDNT");
		
		if(userId == null || userId.equals("")) {
			movePage = "/login.xml";
		}
		else {
			movePage = "/index.xml";
		}
		model.addAttribute("movePage", movePage);
		
		return "websquare";
	}
	
	@GetMapping(value = "/login")
	public String login(@CommonParam Map<String, Object> parameter, HttpSession session,
			HttpServletRequest request, Model model) {
		model.addAttribute("movePage", "/login.xml");
		return "websquare";
	}
	
	@GetMapping(value = "/index")
	public String index_model(@CommonParam Map<String, Object> parameter, HttpSession session,
			HttpServletRequest request, Model model) {
		model.addAttribute("movePage", "/index.xml");
		return "websquare";
	}
	
}