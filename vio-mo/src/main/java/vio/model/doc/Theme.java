package vio.model.doc;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //
    private String val;
    //
    @XmlTransient
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "docthemes",
            joinColumns = {
        @JoinColumn(name = "themeId")},
            inverseJoinColumns = {
        @JoinColumn(name = "docId")})
    private Collection<Document> docs;

    public Theme() {
    }

    public Theme(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public void setDocs(Collection<Document> docs) {
        this.docs = docs;
    }

    public Collection<Document> getDocs() {
        return docs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (!(object instanceof Theme)) {
            return false;
        }
        Theme other = (Theme) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crimsec.archiv.jpa.doc.Theme[id=" + id + "]";
    }
// </editor-fold>
}
