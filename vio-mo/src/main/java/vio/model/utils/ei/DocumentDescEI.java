package vio.model.utils.ei;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import vio.model.doc.Document;

/**
 * document description export&import
 *
 * @author moroz
 */
public class DocumentDescEI {

    public void exportDocDesc(String connUrl,
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

            ResultSet rs = stmt.executeQuery("SELECT * FROM opisan");

            JAXBContext jbctx = JAXBContext.newInstance(DocumentEICollection.class, Document.class);
            DocumentEICollection deic=new DocumentEICollection();
            deic.setImportedFromUrl(connUrl);
            deic.setGenerated(new Date());
            while (rs.next()) {

            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        } 
    }

}
