import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.services.model.Portfolio;
import com.trader.crud.xml.XmlCrud;

public class DatabaseTest {

	@Test
	public void testRead() {
		XmlCrud db = XmlCrud.getInstance();

		List<Portfolio> portfolios = db.readDatabase("/clientsDatabase/client");
		
		for(Portfolio p : portfolios) {
			System.out.println(p);
		}

		Assert.assertNotNull(portfolios);
	}
	
	@Test
	public void testUpdate() {
		
	}
	
	public void testCreate() {
		
	}
	
	public void testDelete() {
		
	}

}
