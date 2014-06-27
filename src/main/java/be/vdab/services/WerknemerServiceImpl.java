package be.vdab.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.dao.WerknemerDAO;
import be.vdab.entities.Werknemer;

@Service
@Transactional(readOnly=true)
public class WerknemerServiceImpl implements WerknemerService {

	private final WerknemerDAO werknemerDAO;
	private final MailService mailService;
	
	@Autowired
	WerknemerServiceImpl(WerknemerDAO werknemerDAO,MailService mailService){
		this.werknemerDAO=werknemerDAO;
		this.mailService=mailService;
	}
	
	@Override
	public Iterable<Werknemer> findAll() {
	return werknemerDAO.findAll(new Sort("familienaam", "voornaam")); 
	}
	@Override
	public Iterable<Werknemer> findMetHoogsteWedde() {
		
		return werknemerDAO.findMetHoogsteWedde();
	}

	@Override
	@Scheduled(fixedRate=60000)
	public void stuurMailMetWerknemersMetHoogsteWedde() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Werknemer werknemer : werknemerDAO.findMetHoogsteWedde()) {
		stringBuilder.append(werknemer.getVoornaam()).append(' ')
		.append(werknemer.getFamilienaam()).append(':')
		.append(werknemer.getWedde()).append("<br>");
		}
		mailService.zendMail("vdabgebruikersnaam@gmail.com","Werknemers met de hoogste wedde", stringBuilder.toString());
		
	}

}


