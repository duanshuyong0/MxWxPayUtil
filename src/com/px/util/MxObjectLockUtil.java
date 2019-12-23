package com.px.util;

import java.util.HashMap;

public class MxObjectLockUtil {
	private HashMap<String, PlanLock> planLockHashMap = new HashMap<>(40);
	private volatile static MxObjectLockUtil instance;

	private MxObjectLockUtil() throws IllegalAccessException {
		if (instance != null) {
			throw new IllegalAccessException("����Ϊ����ģʽ���������ɶ���Ķ���");
		}
	}

	public HashMap<String, PlanLock> getPlanLockHashMap() {
		return planLockHashMap;
	}

	public synchronized PlanLock getObjectLock(String key) {
		if (planLockHashMap.get(key) == null) {
			PlanLock planLock = new PlanLock();
			planLockHashMap.put(key, planLock);
			return planLock;
		}
		return planLockHashMap.get(key);
	}

	public PlanLock removeLock(String key) {
		PlanLock planLock = null;
		if (planLockHashMap.get(key) != null) {
			planLock = planLockHashMap.remove(key);
		}
		return planLock;
	}

	public static MxObjectLockUtil getInstance() {
		if (instance == null) {
			synchronized (MxObjectLockUtil.class) {
				if (instance == null) {
					try {
						instance = new MxObjectLockUtil();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}

}
