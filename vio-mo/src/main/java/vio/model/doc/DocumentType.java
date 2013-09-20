package vio.model.doc;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Вид документа нормативный -> стандарты гос-е -> стандарты отраслевые -> тех.
 * условия -> ... технический ->справочники -> учебники конструкторский .....
 *
 * @author moroz
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class DocumentType implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //
    @JsonIgnore
    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "parentId")
    private DocumentType parentDocType;
    //
    @JsonIgnore
    @XmlTransient
    @OneToMany(mappedBy = "parentDocType", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Collection<DocumentType> childDocTypes;
    //
    private String val;

    public DocumentType() {
    }

    public DocumentType(String val) {
        this.val = val;
    }

    public DocumentType getParentDocType() {
        return parentDocType;
    }

    public void setParentDocType(DocumentType parentDocType) {
        this.parentDocType = parentDocType;
    }

    public Collection<DocumentType> getChildDocTypes() {
        return childDocTypes;
    }

    public void setChildDocTypes(Collection<DocumentType> childDocTypes) {
        this.childDocTypes = childDocTypes;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // <editor-fold defaultstate="collapsed" desc="Override Object's methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentType)) {
            return false;
        }

        DocumentType other = (DocumentType) object;
        if ((this.id != other.id) || (!this.getVal().equals(other.getVal()))) {
            return false;
        }
        //TODO:  Comapre 'other' Collection<DocumentType> childDocTypes && parentDocType (or not necessity   ) ?

        return true;
    }

    @Override
    public String toString() {
        return "com.crimsec.archiv.jpa.DocumentType[id=" + id + ", value=" + val + "]";

    }
    // </editor-fold>
}
