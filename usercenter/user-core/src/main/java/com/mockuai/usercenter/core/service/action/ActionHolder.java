package com.mockuai.usercenter.core.service.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 操作容器，运用启动时，就加载进所有的Action<br>
 * 需要开发新功能接口，只要新增相应的具体action即可
 * @author wujin.zzq
 *
 */
public class ActionHolder implements ApplicationContextAware{
	private final static Logger log = LoggerFactory.getLogger(ActionHolder.class);

	private ApplicationContext applicationContext;
	private Map<String,Action> actions;
	/**
	 * 取得Spring容器里的Action
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Action getAction(String actionName){
		if(null==actions) {
			synchronized (this) {
				if (null == actions) {
					actions = new HashMap<String, Action>();
					Map<String, Action> map = applicationContext.getBeansOfType(Action.class);
					for (Action act : map.values()) {
						actions.put(act.getName(), act);
					}
				}

			}
		} else {
			log.error("actions is null when get action with name" + actionName);
		}
		return actions.get(actionName);
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		this.applicationContext=applicationContext;
	}
}
