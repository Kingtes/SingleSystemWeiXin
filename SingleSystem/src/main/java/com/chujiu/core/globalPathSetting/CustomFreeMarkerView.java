package com.chujiu.core.globalPathSetting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * 
 * @ClassName: CllFreeMarkerView
 * @Description: 设定全局的路径变量，便于前台引用js，css，image等
 * @author suliang
 * @date 2016年5月16日 下午2:04:37
 *
 */
public class CustomFreeMarkerView extends FreeMarkerView {
	private static final String CONTEXT_PATH = "webRootPath";
	private static final String BASE_PATH = "basePath";
	private static final String STATIC_PATH = "staticPath";
	private static final String IMG_SERVER_PATH = "imgServerBaseUrl";/*图片服务器*/

	private static String imgServerBaseUrl;
	@Override
	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) throws Exception {
		String webRootPath = request.getContextPath();
		String port = "";
		if (80 == request.getServerPort() || 443 == request.getServerPort()) {
			port = "";
		} else {
			port = ":" + Integer.toString(request.getServerPort());
		}
		String basePath = request.getScheme() + "://" + request.getServerName() + port + webRootPath;
		model.put(CONTEXT_PATH, webRootPath);
		model.put(BASE_PATH, basePath);
		model.put(STATIC_PATH, request.getSession().getServletContext().getAttribute(STATIC_PATH));
		model.put(IMG_SERVER_PATH, imgServerBaseUrl);
		super.exposeHelpers(model, request);
	}

	public CustomFreeMarkerView(){
		if( null == imgServerBaseUrl) {
			File conf = new File(getClass().getResource("/conf/config.properties").getFile());
			Properties properties = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(conf);
				properties.load(fis);
				imgServerBaseUrl = properties.getProperty("imgServerBaseUrl");
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					} finally {
						fis = null;
					}
				}
			}
		}
	}
}
