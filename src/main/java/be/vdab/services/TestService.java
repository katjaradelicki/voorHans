package be.vdab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class TestService {
	
	private final MailService mailService;
	
	@Autowired
	TestService(MailService mailService){
		this.mailService=mailService;
	}
	@Scheduled(fixedRate=30000)
	public void stuurMail() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("testje");
		mailService.zendMail("vdabgebruikersnaam@gmail.com","Test", stringBuilder.toString());
		
	}

}
