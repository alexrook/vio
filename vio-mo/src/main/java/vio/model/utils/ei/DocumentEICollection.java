package vio.model.utils.ei;


import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import vio.model.doc.Document;

/**
 * xml collection of Documents to support export&import utils
 *
 * @author moroz
 */
@XmlRootElement(name = "documents")
public class DocumentEICollection {

    String importedFromUrl;
    Date generated;
    
    private Collection<Document> documents;

    public String getImportedFromUrl() {
        return importedFromUrl;
    }

    public void setImportedFromUrl(String importedFromUrl) {
        this.importedFromUrl = importedFromUrl;
    }

    public Date getGenerated() {
        return generated;
    }

    public void setGenerated(Date generated) {
        this.generated = generated;
    }

    @XmlElementWrapper(name = "items")
    public Collection<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

}
