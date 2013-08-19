package vio.service.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import vio.model.doc.Theme;
import vio.service.AbstractFacade;

/**
 * @author moroz
 */
@RunWith(Arquillian.class)
public class ThemeFacadeTest {

    @EJB
    ThemeFacade themeFacade_1;

    public ThemeFacadeTest() {
    }

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        Archive<JavaArchive> result = ShrinkWrap.create(JavaArchive.class, "test.jar")
                .addPackage(ThemeFacadeTest.class.getPackage())
                .addPackage(AbstractFacade.class.getPackage())
                .addPackage(Theme.class.getPackage())
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("test-ds.xml", "test-ds.xml")
                .addAsResource("log4j.properties", "log4j.properties");

        result.as(ZipExporter.class).exportTo(new File("target/test-ear.zip"), true);
        //result.writeTo(new FileOutputStream("target/test-ear-arquillian.zip"), Formatters.VERBOSE);

        return result;
    }

    @Test
    public void dummy() {
        assertTrue(true);
    }

    @Test
    public void test_st_1_CreateAndGet() {
        assertNotNull("error:injected service ThemeFacade is null", themeFacade_1);

        Theme t1 = new Theme("theme 1");
        themeFacade_1.create(t1);
        assertNotNull("error:id of theme after persist is null",t1.getId());
        Theme t1_act = themeFacade_1.get(t1.getId());
        assertNotNull("error:method ThemeFacade.get return null", t1_act);
        assertEquals(t1.getVal(), t1_act.getVal());
    }
}