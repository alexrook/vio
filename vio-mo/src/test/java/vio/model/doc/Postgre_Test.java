/*
 * $Id: Postgre_Test.java 2 2010-11-01 12:48:09Z moroz $
 */
package vio.model.doc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import vio.test.model.TestUtils;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.fail;

public class Postgre_Test extends EMBase {

    private static Logger logger = Logger.getLogger("vio.model.doc.Postgre_Test");
    private static String UNIT_NAME = "Postgre-TEST";
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
    public static void beforeClass() throws FileNotFoundException {
        logger.info("\n===================================\n");
        try {
            connectinfo = setupDBConnection(UNIT_NAME, logger, null);
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(new FileOutputStream("tada")));
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
        /*
         try {
         Statement stmt = connectinfo.connection.createStatement();
         stmt.addBatch(
         "drop table abstractdoclocation cascade;" +
         "drop table basedoclocation cascade;" +
         "drop table color cascade;" +
         "drop table document cascade;" +
         "drop table docthemes cascade;" +
         "drop table documenttype cascade;" +
         "drop table format cascade;" +
         "drop table innerdoclocation cascade;" +
         "drop table openjpa_sequence_table cascade;" +
         "drop table person cascade;" +
         "drop table subscribe cascade;" +
         "drop table theme cascade;");
         stmt.executeBatch();
         } catch (SQLException ex) {
         logger.info(
         "error while drop tables in postgree schema\n"
         + ex.getMessage() + "\n"
         + ex.getNextException().getMessage());
         }
         */
        logger.info("closing database connection");
        try {
            connectinfo.connection.close();
        } catch (Exception ex) {
        }
        logger.info("end all tests");
    }
}
