package com.boot.security.server.page.table;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * HandlerMethodArgumentResolver策略模式分析
 * 分页、查询参数解析
 * 
 * @author zhangmenglai
 *
 */
public class PageTableArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * supportParameter用来验证是否适合解析某种类型的参数
	 * @param parameter
	 * @return
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> cla = parameter.getParameterType();
		//这里我定义的是如果入参为继承基类的时候就进入这个自定义参数解析方法中
		return cla.isAssignableFrom(PageTableRequest.class);

		//如果函数包含我们的自定义注解，那就走resolveArgument()函数
		//return parameter.hasParameterAnnotation(Params.class);
	}

	/**
	 * resolveArgument负责解析参数
	 * @param parameter		MethodParameter parameter：请求参数
	 * @param mavContainer
	 * @param webRequest 	从NativeWebRequest中获取数据，ModelAndViewContainer用来提供访问Model
	 * @param binderFactory	WebDataBinderFactory用于创建一个WebDataBinder用于数据绑定、校验
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

		PageTableRequest tableRequest = new PageTableRequest();
		Map<String, String[]> param = request.getParameterMap();
		if (param.containsKey("start")) {
			tableRequest.setOffset(Integer.parseInt(request.getParameter("start")));
		}

		if (param.containsKey("length")) {
			tableRequest.setLimit(Integer.parseInt(request.getParameter("length")));
		}

		Map<String, Object> map = Maps.newHashMap();
		tableRequest.setParams(map);

		param.forEach((k, v) -> {
			if (v.length == 1) {
				map.put(k, v[0]);
			} else {
				map.put(k, Arrays.asList(v));
			}
		});

		setOrderBy(tableRequest, map);
		removeParam(tableRequest);

		return tableRequest;
	}

	/**
	 * 去除datatables分页带的一些复杂参数
	 * 
	 * @param tableRequest
	 */
	private void removeParam(PageTableRequest tableRequest) {
		Map<String, Object> map = tableRequest.getParams();

		if (!CollectionUtils.isEmpty(map)) {
			Map<String, Object> param = new HashMap<>();
			map.forEach((k, v) -> {
				if (k.indexOf("[") < 0 && k.indexOf("]") < 0 && !"_".equals(k)) {
					param.put(k, v);
				}
			});

			tableRequest.setParams(param);
		}
	}

	/**
	 * 从datatables分页请求数据中解析排序
	 * 
	 * @param tableRequest
	 * @param map
	 */
	private void setOrderBy(PageTableRequest tableRequest, Map<String, Object> map) {
		StringBuilder orderBy = new StringBuilder();
		int size = map.size();
		for (int i = 0; i < size; i++) {
			String index = (String) map.get("order[" + i + "][column]");
			if (StringUtils.isEmpty(index)) {
				break;
			}
			String column = (String) map.get("columns[" + index + "][data]");
			if (StringUtils.isBlank(column)) {
				continue;
			}
			String sort = (String) map.get("order[" + i + "][dir]");

			orderBy.append(column).append(" ").append(sort).append(", ");
		}

		if (orderBy.length() > 0) {
			tableRequest.getParams().put("orderBy",
					" order by " + StringUtils.substringBeforeLast(orderBy.toString(), ","));
		}
	}

}
