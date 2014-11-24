package vio.model.utils.ei;

import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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

    
    /*
    * simply wrapper on Document for getting  @XmlTransient field
    */
    @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
    public static class DocumentExt extends Document {

        public void setDesc(String desc) {
            super.setDescription(desc);
        }

        public String getDesc() {
            return super.getDescription();
        }
    }

    String importedFromUrl;
    Date generated;

    private Collection<DocumentExt> documents;

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
    public Collection<DocumentExt> getDocuments() {
        return documents;
    }

    public void setDocuments(Collection<DocumentExt> documents) {
        this.documents = documents;
    }

}
