/**
 * $Id: EMInMemoryTest.java 2 2010-11-01 12:48:09Z moroz $
 */
package vio.model.doc;

import vio.test.model.TestUtils;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger; 
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.fail;

public class EMInMemoryTest extends EMBase {

    private static Logger logger = Logger.getLogger("Model.ent.test.JPAInMemoryTest"); //
    private static String UNIT_NAME = "InMemoryTestDB-OpenJPA";
    private static TestUtils.ConnectionInfo connectinfo;

    @Override
    protected Logger getTestLog() {
        return logger;
    }

    @Override
    protected EntityManager getEm() {
        return connectinfo.em;
    }

    @BeforeClass
    public static void beforeClass() {
        logger.info("\n===================================\n");

       try {
            connectinfo = setupDBConnection(UNIT_NAME, logger, null);
        } catch (Exception e) {
            fail("exception during connection instanciation, with message: " + e.getMessage());
        }
        
    }

    @AfterClass
    public static void afterClass() {
        logger.info("closing EntityManager");
        if (connectinfo.em != null) {
            connectinfo.em.close();
        }
        logger.info("closing EntityManagerFactory");
        if (connectinfo.emf != null) {
            connectinfo.emf.close();
        }
        logger.info("stopping in-memory HSQL database.");
        try {
            connectinfo.connection.createStatement().execute("SHUTDOWN");
        } catch (Exception ex) {
        }
        logger.info("end all tests");
    }

}
