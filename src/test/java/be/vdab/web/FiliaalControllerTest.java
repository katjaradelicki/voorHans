package be.vdab.web;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import be.vdab.entities.Filiaal;
import be.vdab.services.FiliaalService;
import be.vdab.valueobjects.Adres;

public class FiliaalControllerTest {
	private FiliaalController filiaalController;
	private Iterable<Filiaal> filialen;
	private FiliaalService filiaalService;
	private Filiaal filiaal;
	private ServletContext servletCotext;

	@Before
	public void setUp() {
		servletCotext=Mockito.mock(ServletContext.class);
		Mockito.when(servletCotext.getRealPath("/images")).thenReturn("");
		filialen = Collections.emptyList();
		filiaalService = Mockito.mock(FiliaalService.class);
		Mockito.when(filiaalService.findAll()).thenReturn(filialen);
		filiaalController = new FiliaalController(filiaalService,servletCotext);
		filiaal = new Filiaal("naam1", true, BigDecimal.ONE, new Date(),new Adres("straat1","huisnr1", 1, "gemeente1"));
		Mockito.when(filiaalService.read(1L)).thenReturn(filiaal);
	}

	@Test
	public void findAllActiveertJuisteView() {
		Assert.assertEquals("filialen/filialen", filiaalController.findAll()
				.getViewName());
	}

	@Test
	public void findAllMaakRequestAttribuutFilialen() {
		Assert.assertEquals(filialen, filiaalController.findAll().getModelMap()
				.get("filialen"));
	}
}
