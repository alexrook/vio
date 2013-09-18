package vio.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import javax.ejb.EJB;
import javax.persistence.Tuple;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import vio.model.doc.Color;
import vio.model.doc.Theme;
import vio.service.AbstractFacade;
import vio.service.doc.ColorFacade;
import vio.service.doc.ThemeFacade;

/**
 * @author moroz
 */
@RunWith(Arquillian.class)
public class LearnCriteriaAPITest {

    public static Logger log = Logger.getLogger("vio.service.doc.LearnCriteriaAPITest");
    private static final String[] colors = {"Red", "Blue", "Green", "Yellow", "Black", "White"};
    private static final String[] themes = {"ThemeOne", "ThemeTwo", "ThemeThree", "ThemeFour", "ThemeFive"};
    //
    @EJB
    LearnCriteriaAPIBean cbl;
    //
    @EJB
    ColorFacade cfb;
    //
    @EJB
    ThemeFacade tfb;
    //
    @EJB
    TestHelperBean helper;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        Archive<JavaArchive> result = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addPackage(ColorFacade.class.getPackage())
                .addPackage(AbstractFacade.class.getPackage())
                .addPackage(LearnCriteriaAPIBean.class.getPackage())
                .addPackage(LearnCriteriaAPITest.class.getPackage())
                .addPackage(Color.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("test-ds.xml", "test-ds.xml")
                .addAsResource("log4j.properties", "log4j.properties");

        result.as(ZipExporter.class).exportTo(new File("target/test-ear.zip"), true);
        //result.writeTo(new FileOutputStream("target/test-ear-arquillian.zip"), Formatters.VERBOSE);

        return result;
    }

    @Before
    public void setup() {
        createEnt();
    }

    @After
    public void tearDown() {
        deleteEnt();
    }

    /*
     * % ->A substitute for zero or more characters
     * _ ->A substitute for a single character
     * [charlist] ->Sets and ranges of characters to match
     * [^charlist] or [!charlist] -> Matches only a character NOT specified within the brackets
     *	
     */
    @Test
    public void test_DD_step_1() {
        Collection<Color> cc = cbl.getColorsByName(colors[0]);
        assertNotNull(cc);
        assertTrue(!cc.isEmpty());
        for (Color c : cc) {
            log.info("Colors in collection:" + c.getVal());
        }

    }

    @Test
    public void test_DD_step_2() {
        Collection<Color> cc = cbl.getColorsByName("%e%");
        assertNotNull(cc);
        assertTrue(!cc.isEmpty());
        for (Color c : cc) {
            log.info("Colors in collection:" + c.getVal());
        }

    }

    @Test
    public void test_DD_step_3() {
        Collection<Color> cc = cbl.getColorsByName("_e%");
        assertNotNull(cc);
        assertTrue(!cc.isEmpty());
        for (Color c : cc) {
            log.info("Colors in collection:" + c.getVal());
        }

    }

    @Test
    public void test_DD_step_4() {

        log.info("=======================================Tuple-step-4============================");
        Collection<Tuple> cc = cbl.getCartesianProduct();
        assertNotNull(cc);
        assertTrue(!cc.isEmpty());
        for (Tuple tuple : cc) {
            log.info(Arrays.toString(tuple.toArray()));
        }

        Tuple t = (Tuple) cc.toArray()[0];
        log.info(t.get(0).toString());

    }

    private void createEnt() {
        for (String c : colors) {
            Color color = new Color(c);
            cfb.create(color);
        }
        for (String t : themes) {
            Theme theme = new Theme(t);
            tfb.create(theme);
        }
    }

    private void deleteEnt() {
        helper.executeNativeQueryWithNoResult("delete from color");
        helper.executeNativeQueryWithNoResult("delete from theme");
    }
}