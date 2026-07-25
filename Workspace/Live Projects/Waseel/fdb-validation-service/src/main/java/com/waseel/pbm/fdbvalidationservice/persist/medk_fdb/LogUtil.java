package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;


import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author MyEclipse Persistence Tools
 */
public class LogUtil {
	
	private static final Logger logger;
	
	static {
		logger = Logger.getLogger("JPAEXTRACTION");
		logger.setLevel(Level.ALL);
	}
	
	public static void log(String info, Level level, Throwable ex) {
    	logger.log(level, info, ex);
    }
    
    public static Logger getLogger() {
    	return logger;
    }
    
}
