/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hbw.espresso.jetty.logging;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Logger;
/**
 *
 * @author niles
 */
public class JettyLogging /* extends AbstractLogger */ {

	DateFormat dateFormat;
	List<OutputStream> loggingList;
	Boolean debug;
	String endl;
	public JettyLogging(Object... files) throws FileNotFoundException {
		this.dateFormat = new SimpleDateFormat("HH:mm:ss mm/dd/yyyy");
		endl = "\r\n";
		List<Object> filesList = Arrays.asList(files);
		for (Object object : filesList) {
			if ("String".equals(object.getClass().getName())) {
				loggingList.add((OutputStream) new FileOutputStream((String) object));
			} else {
				loggingList.add((OutputStream) object);
			}
		}
	}
	private void logMessage(String type, String message) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(ManagementFactory.getRuntimeMXBean().getName());
		builder.append(" ");
		builder.append(type);
		builder.append(" ");
		builder.append(this.dateFormat.format(new Date()));
		builder.append("] ");
		builder.append(message);
		builder.append(endl);
		String built = builder.toString();
		for (OutputStream o : loggingList) {
			try {
				o.write(built.getBytes());
			} catch (Exception e) {
				// We should probably do something about this
                                // Yes we should
			}
		}
	}
	
	protected Logger newLogger(String string) {
		return new JavaUtilLog(string);
	}

	
	public String getName() {
		return "Jetty Logger for Espresso";
	}

	
	public void warn(String string, Object... os) {
		logMessage("WARNING", string);
	}

	
	public void warn(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void warn(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void info(String string, Object... os) {
		logMessage("INFO", string);
	}

	
	public void info(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void info(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public Boolean isDebugEnabled() {
		return this.debug;
	}

	
	public void setDebugEnabled(boolean bln) {
		this.debug = (Boolean) bln;
	}

	
	public void debug(String string, Object... os) {
		if (this.debug) {
			logMessage("DEBUG", string);
		}
	}

	
	public void debug(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void debug(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void ignore(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
