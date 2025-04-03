package com.hsbc.pws.common.log;

import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;


public class ThreadMdcUtil {

	public static void setTraceIdIfAbsent() {
		if (MDC.get("traceId") == null) {
			MDC.put("traceId", UUID.randomUUID().toString().replace("-", ""));
		}
	}

	public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
		return () -> {
			if (context == null) {
				MDC.clear();
			} else {
				MDC.setContextMap(context);
			}
			
			setTraceIdIfAbsent();
			
			try {
				return callable.call();
			} finally {
				MDC.clear();
			}
		};
	}

	public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
		return new Runnable() {
			@Override
			public void run() {
				if (context == null) {
					MDC.clear();
				} else {
					MDC.setContextMap(context);
				}
				
				setTraceIdIfAbsent();
				
				try {
					runnable.run();
				} finally {
					MDC.clear();
				}
			}
		};
	}
}
