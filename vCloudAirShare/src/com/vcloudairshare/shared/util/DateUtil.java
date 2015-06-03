package com.vcloudairshare.shared.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Logger;

public class DateUtil {
	  private static final Logger log = Logger.getLogger(DateUtil.class.getName());

	  public static Date parseDate(String theDate) {
		    Date theReturn = new Date();
		    if (null != theDate && theDate.length() > 0) {
		      Date theTestDate = null;
			try {
				theTestDate = new SimpleDateFormat(
						"dd MMM yyyy HH:mm:ss Z").parse(theDate);
			} catch (ParseException e) {
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate =
		              new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("EEE MMM dd HH:mm:ssZ").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("yyyy-MM-dd").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null == theTestDate) {
		        try {
		          theTestDate = new SimpleDateFormat("yyyy MM dd").parse(theDate);
		        } catch (ParseException e) {
		        }
		      }
		      if (null != theTestDate) {
		        theReturn = theTestDate;
		      } else {
		        log.warning("Parse Failure - Tried to parse " + theDate
		            + " with known formats and failed.  Now giving up and using now");

		      }
		    }
		    return theReturn;
		  }

}
