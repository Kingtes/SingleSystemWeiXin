package com.chujiu.core.exception.handler;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.chujiu.core.exception.ApplicationException;
import com.chujiu.core.exception.cache.ExceptionCodeCache;


/**
 * 
 * @ClassName: ExceptionHandler 
 * @Description: 公共异常处理类 
 * @author linlong
 * @date 2016年5月16日 下午2:02:53 
 *
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

	private final static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception) {
		String errorMessage = null;
		if (exception instanceof ApplicationException) {
			errorMessage = MessageFormatter
					.arrayFormat(
							ExceptionCodeCache.getExceptionCache(
									((ApplicationException) exception)
											.getErrorCode()).getMessage(),
							((ApplicationException) exception)
									.getErrorInfoParameters()).getMessage();
			log.error(errorMessage, exception);
		} else {
			errorMessage = "未知的异常信息";
			log.error(errorMessage, exception);
		}

		// 输出流写出
		OutputStream os = null;
		try {
			response.setContentType("application/json;charset=utf-8");
			os = response.getOutputStream();
		} catch (IOException e) {
			log.error("获取输出流失败", e);
		}
		try {
			os.write(errorMessage.getBytes());
		} catch (IOException e) {
			log.error("写入输出流失败", e);
		}
		return null;
		// 将异常写出到指定页面，这里指定的异常页面为：webapp/WEB-INF/pages/system/error.html
		/*Map<String,String> map = new HashMap<String,String>();
		map.put("errorMsg", errorMessage);
		return new ModelAndView("system/error",map);*/
	}
}
