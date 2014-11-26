package vio.model.doc;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/*
 * Представляет метаданные архивного документа
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries(value={
   @NamedQuery(
      name = "Document.docType",
      query="select a.docType from Document a where a.id = :docId"),
     @NamedQuery(
      name = "Document.description",
      query="select a.description from Document a where a.id = :docId")
})
@Entity
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //наименование документа
    private String name;
    //Описание документа
    @JsonIgnore //jackson uses JavaBean model
    @XmlTransient
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 5000)
    private String description;
    //шифр документа
    private String code;
    // регистрационный номер
    private String regNum;
    //формат (Книга, брошюра, пакет ...)
    @ManyToOne
    @JoinColumn(name = "formatId")
    private Format format;
    //тип документа
    @JsonIgnore
    @XmlTransient
    @ManyToOne
    @JoinColumn(name = "doctypeId")
    private DocumentType docType;
    //цвет (справочное)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colorId")
    private Color color;
    //
    @JsonIgnore
    @XmlTransient
    @ManyToMany(mappedBy = "docs")
    private Collection<Theme> themes;
    //
    @JsonIgnore
    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Document parentDoc;
    /*
     * TODO: может стоит удалать по каскаду ?
     */
    @JsonIgnore
    @XmlTransient
    @OneToMany(mappedBy = "parentDoc",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Collection<Document> childDocs;

    public Document() {
    }

    /**
     * Метаданные архивного документа
     *
     * @param name   наименование документа
     * @param code   шифр документа
     * @param regNum регистрационный номер
     *
     * @see Format
     * @param format формат (Книга, брошюра, пакет ...)
     *
     * @see Color
     * @param color  цвет (справочное)
     */
    public Document(String name,
            String code,
            String regNum, Format format,
            Color color) {
        this.name = name;
        this.code = code;
        this.regNum = regNum;
        this.format = format;
        this.color = color;
    }

    public Collection<Document> getChildDocs() {
        return childDocs;
    }

    public void setChildDocs(Collection<Document> childDocs) {
        this.childDocs = childDocs;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public DocumentType getDocType() {
        return docType;
    }

    public void setDocType(DocumentType docType) {
        this.docType = docType;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Document getParentDoc() {
        return parentDoc;
    }

    public void setParentDoc(Document parentDoc) {
        this.parentDoc = parentDoc;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public Collection<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Collection<Theme> themes) {
        this.themes = themes;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated:Override Object's methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.id != other.id)
                && (!this.name.equals(other.getName()))
                && (!this.code.equals(other.getCode()))
                && (!this.regNum.equals(other.getRegNum()))
                && (!this.format.equals(other.getFormat()))
                && (!this.color.equals(other.getColor()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getPackage().getName() + "[id=" + id + "]";
    }
    // </editor-fold>
}
