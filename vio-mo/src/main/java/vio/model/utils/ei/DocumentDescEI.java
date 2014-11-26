package vio.model.utils.ei;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import vio.model.doc.Document;
import vio.model.utils.ei.DocumentEICollection.DocumentExt;
import static java.lang.System.out;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.util.Properties;
import javax.xml.bind.Unmarshaller;

/**
 * document description export&import
 *
 * @author moroz
 */
public class DocumentDescEI {

    private static String USAGE = "\nUSAGE:\n\n"
            + "\t <java_call> <export|import> (import-export).properties (import-export).filename\n"
            + "where\n"
            + "'export' or 'import'- name of program mode\n\n"
            + "(import-export).properties - database connection properties (may be same)\n"
            + "\trecognized:\n"
            + "\t(postgresql|oracle).db.url - postgresql or oracle connection urls\n"
            + "\t(postgresql|oracle).db.username - postgresql or oracle connection login\n"
            + "\t(postgresql|oracle).db.password - postgresql or oracle connection password\n\n"
            + "(import-export).filename - file name for export or import data (may be same)\n\n";

    public void exportDocDescFromOracle(String connUrl,
            String user,
            String password, File file) throws IOException {
        Connection conn;
        try {

            conn = DriverManager.getConnection(
                    connUrl,
                    user, password);

            if (conn == null) {
                throw new SQLException("can't establish a connection to Oracle database");
            }

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT shifr,NAMEDOC,DESCRIPT FROM opisan");

            JAXBContext jbctx = JAXBContext.newInstance(DocumentEICollection.class, Document.class);
            DocumentEICollection deic = new DocumentEICollection();
            deic.setImportedFromUrl(connUrl);
            deic.setGenerated(new Date());
            Collection<DocumentExt> items = new ArrayList<DocumentExt>(3300);
            int i = 0;
            while (rs.next()) {
                i++;
                DocumentExt item = new DocumentExt();
                item.setId(rs.getInt("shifr"));
                item.setName(rs.getString("NAMEDOC") != null ? rs.getString("NAMEDOC").trim() : "");
                item.setDesc(rs.getString("DESCRIPT") != null ? rs.getString("DESCRIPT").trim() : "");
                items.add(item);
                if (i % 100 == 0) {
                    out.println("select " + i + " rows");
                }
            }
            out.println(i + " rows selected");
            deic.setDocuments(items);
            stmt.close();
            conn.close();

            Marshaller m = jbctx.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(deic, file);

        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    public void importDocDescToPostgreSQL(String connUrl,
            String user,
            String password, File file) throws IOException {
        Connection conn;
        try {

            JAXBContext jbctx = JAXBContext.newInstance(DocumentEICollection.class, Document.class);

            Unmarshaller unm = jbctx.createUnmarshaller();

            DocumentEICollection deic = (DocumentEICollection) unm.unmarshal(file);

            conn = DriverManager.getConnection(
                    connUrl,
                    user, password);

            conn.setAutoCommit(false);

            if (conn == null) {
                throw new SQLException("can't establish a connection to PostgreSQL database");
            }
            PreparedStatement stm = conn.prepareStatement("update document set description=? where id=?");

            Savepoint sp = conn.setSavepoint();
            int i = 0;
            try {
                for (DocumentExt item : deic.getDocuments()) {
                    /* out.println(item.getId());
                     out.println(item.getDesc().length());
                     out.println("\n-----------\n");*/
                    stm.setString(1, item.getDesc());
                    stm.setInt(2, item.getId());
                    i+=stm.executeUpdate();
                    if (i % 100 == 0) {
                        out.println(i + " rows imported");
                    }
                }
                conn.commit();
                out.println(i + " rows imported and transaction commited");
            } catch (SQLException e) {
                conn.rollback(sp);
                throw new SQLException("can't import data to PostgreSQL database", e);
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }

    }

    public static void main(String args[]) {

        if (args.length < 3) {
            usage();
            System.exit(1);

        }
        if (args[0].equalsIgnoreCase("export")) {
            DocumentDescEI dei = new DocumentDescEI();

            Properties props = new Properties();

            try {
                props.load(new FileInputStream(args[1]));
                dei.exportDocDescFromOracle(
                        props.getProperty("oracle.db.url"),
                        props.getProperty("oracle.db.username"),
                        props.getProperty("oracle.db.password"),
                        new File(args[2]));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (args[0].equalsIgnoreCase("import")) {
            DocumentDescEI dei = new DocumentDescEI();

            Properties props = new Properties();

            try {
                props.load(new FileInputStream(args[1]));
                dei.importDocDescToPostgreSQL(
                        props.getProperty("postgresql.db.url"),
                        props.getProperty("postgresql.db.username"),
                        props.getProperty("postgresql.db.password", ""),
                        new File(args[2]));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            usage();
            System.exit(1);
        }

    }

    private static void usage() {
        out.print(USAGE);
    }
}
