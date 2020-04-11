package kidsMansion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class mailSender {
	
	 
	public static void main(String args[]){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		//sdf.format();
		// Timer timer ;
		
		 System.out.println(" Invoking " + sdf.format(new Date()));
		
		 for (int i = 0 ; i< 1;i ++){
			 	try {
					System.out.println("Before Sending --> iteration " + i + " " + sdf.format(new Date()));  
					String subject = "Testing Mail Break Up Task";
					String mailBody ="Dear Parents<BR> Please note we are celebrating childern's day on 14th November 2014.<Br> Please ensure to send your child in party wear.";
					String emails ="srinidhi.mc@gmail.com,srinidhi_mc@yahoo.com,vinaya.srinidhi@gmail.ccom";
					boolean isMailSent = new sendMailModified().sendMailContent(subject, emails, mailBody);
					System.out.println("Mail sent  --> iteration " + i + " " + sdf.format(new Date()) + " --> " + isMailSent);
					//TimeUnit.MINUTES.sleep(5);
				
			/*} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();*/
			} catch (Exception e){
				e.printStackTrace();
			}
			 //	increment += 1000;
	            
	     }
		 System.out.println(" Completion " + sdf.format(new Date()));	
		
	}
	
	
	static void processing() throws InterruptedException{
		
		System.out.println("Processing....");
		Thread.sleep(5000);
	}
	
	

}


class ScheduleRunner extends TimerTask
{
/**
 * executed when time is up.
 */
public void run()
   {
	SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//	sdf.format("dd-M-yyyy hh:mm:ss");
     System.out.println(" Calling the Run Method " + sdf.format(new Date()));
	 System.out.println("");
	
   }
} // end inner class ScheduleRunner
