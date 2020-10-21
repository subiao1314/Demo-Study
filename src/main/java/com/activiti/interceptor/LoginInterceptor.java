package com.activiti.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.activiti.entity.ActUser;

/**
 * 登陆验证的拦截器
 * @author gjie
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	//进入handler方法之前执行
	//用于身份认证，身份授权
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//获取请求地址
		String url = request.getRequestURI();
		//判断url是否公开(比如：登陆，查看首页等等，这些url不需要进行拦截),实际使用公开地址时，公开地址是配置在配置文件里的。
		if(url.indexOf("login") >= 0){
			return true;
		}
		
		//获取session
		HttpSession session = request.getSession();
		//获取session中的用户信息
		ActUser user = (ActUser)session.getAttribute("USER");
		
		if(user != null){//判断session中是否有用户信息，有则表示用户已经登陆
			
			if (handler instanceof HandlerMethod) {
				HandlerMethod hm = (HandlerMethod) handler;
				Method method = hm.getMethod();
				MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
				if(myAnnotation != null){
					String roleId = myAnnotation.role();
					if (user.getRoleId().toString().equals(roleId)) {
						return true;
					} else {
						response.sendRedirect(request.getContextPath() + "/login");//这里可以跳转到错误信息提示页（您没有权限访问该页面，请与管理员联系）
						return false;
					}
				}
			}
			return true;
		}
		
		//程序走到这里，说明用户没有登陆，可以重定向到登陆页面
//		request.getRequestDispatcher("/WEB-INF/views/login/index.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath() + "/login");
		
		return false;
	}

	//进入handler方法之后&&返回modelAndView之前执行
	//应用场景从ModelAndView出发：可以将公用的模型数据在这里传给视图，也可以在这里统一指定视图的路径
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	//handler方法执行以后再执行此方法
	//统一的异常处理，统一的日志处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
