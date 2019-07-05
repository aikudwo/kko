package com.aikudwo.ccy;

import com.aikudwo.ccy.ioc.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.TimeZone;

@EnableRetry
@EnableAsync
@EnableScheduling
@SpringBootApplication
@ServletComponentScan
@PropertySource({"classpath:config/dingtalk_api.properties", "classpath:config/dingtalk_secret.properties"})
public class CcyApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@PostConstruct
	void setDefaultTimezone() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}

	public static void main(String[] args) {
		String aaa = new String("aaa");
		ReferenceQueue<String> queue = new ReferenceQueue<>();
		PhantomReference<String> sprf = new PhantomReference<>(aaa, queue);
		int a = 6;
		Integer b = 6;
		if(a==b){
			System.out.println("666666666666666");
		}
		if(b.equals(a)){
			System.out.println("88888888888888888888");
		}
		ApplicationContext axt = SpringApplication.run(CcyApplication.class, args);
		Person bean = axt.getBean(Person.class);
		System.out.println(bean.getName() +"----是 ----" + bean.getId());
		bean.call();
	}

	@Override
	public void run(String... args) throws Exception{

	}
}
