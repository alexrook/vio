
package vio.test.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;


/**
 * routines для установки соединения с базой данных
 * через JPA и javax.sql.Connection
 * 
 * @author moroz
 */
public abstract class TestUtils {

    protected static class ConnectionInfo {

        public EntityManager em;
        public EntityManagerFactory emf;
        public Connection connection;
    }

    protected abstract Logger getTestLog();

    protected Appender startWriteSQLLog(String filename) {
        try {
            Appender a = new FileAppender(new SimpleLayout(), filename);
            getSQLLogger().addAppender(a);
            return a;
        } catch (Exception ex) {
            return null;
        }
    }

    protected void stopWriteSQLLog(Appender a) {
        try {
            if (a!=null)    getSQLLogger().removeAppender(a);
        } catch (Exception ex) {
        }
    }

    protected abstract EntityManager getEm();

    protected Logger getSQLLogger() throws Exception {
        Logger result = Logger.getLogger("openjpa.jdbc.SQL"); //WARN: vendor-specific 
        if (result != null) {
            return result;
        } else {
            throw new Exception("could not  instantiate JDBC logger because EntityManagerFactory implementation unknown");
        }
    }

    protected static void runBatchSQL(Connection connection, Collection<String> batchSql, Logger log) {
        if ((batchSql != null) && (batchSql.size() > 0)) {
            try {
                log.info("execute batch SQL");
                Statement stmt = connection.createStatement();
                Iterator<String> i = batchSql.iterator();
                while (i.hasNext()) {
                    stmt.addBatch(i.next());
                }
                stmt.executeBatch();
            } catch (SQLException e) {
                log.error("error while execute SQL batch\n" + e.getMessage());
            }
        }

    }

    /**
     * Установка связи с базой данных.
     * @param unitname - persistence-unit name from persistence.xml
     * @param logger - appropriate logger
     * @param preEntityManagerBatch - SQL script, that runs after installing the DB-connection, it is useful for filling / removal of various data for test
     * @return ConnectionInfo структура хранящая данные о соединении
     * @throws Exception
     */
    public static ConnectionInfo setupDBConnection(
            String unitname, Logger logger, Collection<String> preEntityManagerBatch)
            throws Exception {

        ConnectionInfo result = new ConnectionInfo();

        logger.info("creating JPA EntityManager for unit tests");
        result.emf = Persistence.createEntityManagerFactory(unitname);
/*
        if (result.emf instanceof org.apache.openjpa.persistence.EntityManagerFactoryImpl) {
            OpenJPAConfiguration config = ((org.apache.openjpa.persistence.EntityManagerFactoryImpl) result.emf).getConfiguration();
            logger.info("старт соединения с URL:// " + config.getConnectionURL());
            Class.forName(config.getConnectionDriverName());
            result.connection = DriverManager.getConnection(config.getConnectionURL(),
                    config.getConnectionUserName(),
                    config.getConnectionPassword());
        } else {
            throw new Exception("could not  instantiate JDBC connection because EntityManagerFactory implementation unknown'");
        }
*/
        runBatchSQL(result.connection, preEntityManagerBatch, logger);

        result.em = result.emf.createEntityManager();

        return result;
    }
}
