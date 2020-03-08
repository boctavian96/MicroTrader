import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.services.model.Portfolio;
import com.trader.crud.xml.XmlCrud;

public class DatabaseTest {

	@Test
	public void testRead() {
		XmlCrud db = XmlCrud.getInstance();

		List<Portfolio> portfolios = db.readDatabase("Lol");
		
		for(Portfolio p : portfolios) {
			System.out.println(p);
		}

		Assert.assertNotNull(portfolios);
	}

}
