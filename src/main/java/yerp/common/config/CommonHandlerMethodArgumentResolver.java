package yerp.common.config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import yerp.common.annotation.CommonParam;
import yerp.common.util.CommonUtil;
import yerp.common.util.ConstantUtil;

public class CommonHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public Object resolveArgument(MethodParameter arg0, ModelAndViewContainer arg1, NativeWebRequest webRequest,
                                  WebDataBinderFactory arg3) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();

        Map<String, Object> param = new HashMap<String, Object>();
        JSONObject normalParam = new JSONObject();
        JSONObject commonParam = new JSONObject();

        for (Iterator<String> iterator = webRequest.getParameterNames(); iterator.hasNext(); ) {
            String key = iterator.next();
            String[] values = webRequest.getParameterValues(key);

            if (values != null) {
                normalParam.put(key, (values.length > 1) ? values : values[0]);
            }
        }

        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Iterator<String> pathIter = pathVariables.keySet().iterator();
        while (pathIter.hasNext()) {
            String key = pathIter.next();
            Object value = pathVariables.get(key);
            if (value != null) {
                commonParam.put(key, (String) value);
            }
        }

        String userId = (String) session.getAttribute(ConstantUtil.SESSION_USER_ID);
        if (userId != null && !userId.equals("")) {
            commonParam.put(ConstantUtil.SESSION_USER_ID, userId);
            commonParam.put(ConstantUtil.SESSION_IP, CommonUtil.getRemoteAddr(request));
        }

        if(request.getMethod().equalsIgnoreCase("GET")) {
            param.put(ConstantUtil.NORMAL, normalParam);
        } else {
        	ServletInputStream is = request.getInputStream();
        	BufferedInputStream bin = new BufferedInputStream(is);
        	BufferedReader br = new BufferedReader(new InputStreamReader(bin, request.getCharacterEncoding()));
        	String body = "";
            String tmp = null;
            
            while ((tmp = br.readLine()) != null) {
            	body += tmp;
            }
            try {
            	JSONObject bodyObj = (JSONObject) new JSONParser().parse(body);
                param.put(ConstantUtil.NORMAL, bodyObj.get(ConstantUtil.NORMAL));
                param.put(ConstantUtil.BODY, bodyObj.get(ConstantUtil.BODY));	
            }catch(Exception e) {}            
        }
        param.put(ConstantUtil.COMMON, commonParam);
        
        return param;

    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Map.class.isAssignableFrom(parameter.getParameterType()) && parameter.hasParameterAnnotation(CommonParam.class);
    }
}