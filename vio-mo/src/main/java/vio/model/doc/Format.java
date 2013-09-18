package vio.model.doc;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Формат документа -> Книга, Брошюра, Папка ...
 */
@Entity
public class Format implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String val;

    public Format() {
    }

    public Format(String val) {
        setVal(val);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the value
     */
    public String getVal() {
        return val;
    }

    /**
     * @param value the value to set
     */
    public void setVal(String value) {
        this.val = value;
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
        if (!(object instanceof Format)) {
            return false;
        }
        Format other = (Format) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crimsec.archiv.jpa.Format[id=" + id + "]";
    }
    // </editor-fold>
}
