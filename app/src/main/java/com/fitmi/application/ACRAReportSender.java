package com.fitmi.application;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
 
import android.content.Context;
import android.util.Log;
 
public class ACRAReportSender implements ReportSender {
 
 private String emailUsername ;
 private String emailPassword ;
  
  
  
 public ACRAReportSender(String emailUsername, String emailPassword) {
  super();
  this.emailUsername = emailUsername;
  this.emailPassword = emailPassword;
 }
 
 
 
 @Override
 public void send(CrashReportData report)
   throws ReportSenderException {
   
  // Extract the required data out of the crash report.
  String reportBody = createCrashReport(report);
   
  // instantiate the email sender
  GMailSender gMailSender = new GMailSender(emailUsername, emailPassword);
   
  try {
   // specify your recipients and send the email
   gMailSender.sendMail("CRASH REPORT", reportBody, emailUsername, "avinash.pandey@dreamztech.com, aviaot@gmail.com");
  } catch (Exception e) {
   Log.d("Error Sending email", e.toString());
  }
 }
 
 
 /** Extract the required data out of the crash report.*/
 private String createCrashReport(CrashReportData report) {
   
  // I've extracted only basic information.
  // U can add loads more data using the enum ReportField. See below.
  StringBuilder body = new StringBuilder();
  body
  .append("Device : " + report.getProperty(ReportField.BRAND) + "-" + report.getProperty(ReportField.PHONE_MODEL))
  .append("\n")
  .append("Android Version :" + report.getProperty(ReportField.ANDROID_VERSION))
  .append("\n")
  .append("App Version : " + report.getProperty(ReportField.APP_VERSION_CODE))
  .append("\n")
  .append("STACK TRACE : \n" + report.getProperty(ReportField.STACK_TRACE));
   
   
  return body.toString();
 }
}