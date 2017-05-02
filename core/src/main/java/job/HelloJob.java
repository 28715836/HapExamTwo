package job;

import com.hand.hap.job.AbstractJob;
import com.hand.hap.mail.service.IEmailService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2017/4/18.
 */
public class HelloJob extends AbstractJob{
    @Autowired
    private IEmailService emailService;
    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        System.out.println("Hello world");
    }
}
