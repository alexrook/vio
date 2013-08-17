package vio.model.doc;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Цвет документа
 *
 * @author moroz
 */
@Entity
public class Color implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String val;

    public Color() {
    }

    /**
     * @param val строковое предстваление цвета ('белый','красный' и т.п)
     */
    public Color(String val) {
        setVal(val);
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

    // <editor-fold defaultstate="collapsed" desc="Generated:Override Object's methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        try {

            if (!(object instanceof Color)) {
                return false;
            }
            Color other = (Color) object;
            if ((this.id == other.getId())
                    && (this.getVal().equals(other.getVal()))) {
                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getPackage().getName() + "[id=" + id + ", value=" + val + "]";
    }
// </editor-fold>
}
