package be.vdab.services;



import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import be.vdab.dao.FiliaalDAO;
import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalMetDezeNaamBestaatAlException;



@Service
@Transactional(readOnly=true)
public class FiliaalServiceImpl implements FiliaalService{

	private final FiliaalDAO filiaalDAO;
	private final MailService mailService;
	private final Logger logger=LoggerFactory.getLogger(FiliaalServiceImpl.class);
	
	@Autowired
	public FiliaalServiceImpl(FiliaalDAO filiaalDAO, MailService mailService) {
	this.filiaalDAO = filiaalDAO;
	this.mailService = mailService;
	}
	
	
	@Override
	@Transactional(readOnly = false)
	public void create(Filiaal filiaal) {
	try {
	filiaalDAO.save(filiaal);
	mailService.zendMail("vdabgebruikersnaam@gmail.com", "Nieuw filiaal",
	"Het filiaal <strong>" + filiaal.getNaam() +
	"</strong> werd toegevoegd.<br>Je kan <a href='"+
	ServletUriComponentsBuilder.fromCurrentContextPath().path(
	"/filialen/{id}/wijzigen").buildAndExpand(filiaal.getId()).toUri() +
	"'>hier</a> het filiaal wijzigen");
	} catch (DataIntegrityViolationException ex) {
	if (filiaalDAO.findByNaam(filiaal.getNaam()) != null) {
	throw new FiliaalMetDezeNaamBestaatAlException();
	}
	throw ex;
	}
	}

	@Override
	public Filiaal read(long id) {
		
		return filiaalDAO.findOne(id);
	}

	@Override
	@Transactional(readOnly=false)
	public void update(Filiaal filiaal) {
		filiaalDAO.save(filiaal);
		
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(long id) {
		filiaalDAO.delete(id);
	System.out.println("net delete uitgevoerd id="+id);
		
	}

	@Override
	public Iterable<Filiaal> findAll() {
		
		return filiaalDAO.findAll(new Sort("naam"));
	}

	@Override
	@PreAuthorize("hasRole('manager')")
	public Iterable<Filiaal> findByPostcodeBetween(int van, int tot) {
		
		return filiaalDAO.findByAdresPostcodeBetweenOrderByNaamAsc(van, tot);
	}

	@Override
	public long findAantalFilialen() {
		
		return filiaalDAO.count();
	}

}
