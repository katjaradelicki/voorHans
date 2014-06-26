package be.vdab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.dao.FiliaalDAO;
import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalMetDezeNaamBestaatAlException;



@Service
@Transactional(readOnly=true)
public class FiliaalServiceImpl implements FiliaalService{

	private final FiliaalDAO filiaalDAO;
	
	@Autowired
	public FiliaalServiceImpl(FiliaalDAO filiaalDAO) {
		this.filiaalDAO=filiaalDAO;
	}
	
	
	@Override
	@Transactional(readOnly=false)
	public void create(Filiaal filiaal) {
		if(filiaalDAO.findByNaam(filiaal.getNaam())==null){
		filiaal.setId((filiaalDAO.save(filiaal)).getId());
		}else {
			throw new FiliaalMetDezeNaamBestaatAlException();
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
