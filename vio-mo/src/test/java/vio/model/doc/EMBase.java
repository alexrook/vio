package vio.model.doc;

import vio.test.model.TestUtils;

import java.util.ArrayList;
import org.apache.log4j.Appender;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test entities via enterprise manager local transaction
 *
 * @author moroz
 */
public abstract class EMBase extends TestUtils {

    // <editor-fold defaultstate="collapsed" desc="common fixture variables">
    //Color Entity
    Color redColor = null;
    Color greenColor = null;
    Color blueColor = null;
    // Format Entity
    Format bookFormat = null;
    Format folderFormat = null;
    Format bookletFormat = null;
    //Theme Entity
    Theme simpleTheme = null;
    Theme manyTheme = null;
    Theme otherTheme = null;
    //DocumentType Entity
    DocumentType normativeType = null;
    DocumentType technicalType = null;
    DocumentType designType = null;
    //normative subtypes
    DocumentType normativeStandartType = null;
    DocumentType normativeTechicalConditionsType = null;
    DocumentType normativeIAEAType = null;
    //technical subtypes
    DocumentType technicalReferenceType = null;
    DocumentType technicalManualType = null;
    //designer subtypes
    DocumentType designDrawingType = null;
    DocumentType designCalculationlType = null;
    //Documents
    Document docOneL1 = null;
    Document docOneL2_One = null;
    Document docOneL2_Two = null;
    Document docTwoL1 = null;
    Document docTwoL2_One = null;
    Document docTwoL2_Two = null;
    Document docThreeL1 = null;
    Document docThreeL2_One = null;
    Document docThreeL2_Two = null;

// </editor-fold>
    @Before
    public void before() {   //setup common fixture
        getTestLog().info("setup common fixture");
        //colors
        redColor = new Color("Red");
        greenColor = new Color("Green");
        blueColor = new Color("Blue");
        //formats
        bookFormat = new Format("Book");
        folderFormat = new Format("Folder");
        bookletFormat = new Format("Booklet");
        //themes
        simpleTheme = new Theme("Simple Theme with one document");
        manyTheme = new Theme("Theme with many document's");
        otherTheme = new Theme("Simple Theme with many document who want many themes :-)");
        //DocumentType Entity
        normativeType = new DocumentType("normative");
        technicalType = new DocumentType("technical");
        designType = new DocumentType("design");
        //normative subtypes
        normativeStandartType = new DocumentType("Standart");
        normativeTechicalConditionsType = new DocumentType("TechicalConditions");
        normativeIAEAType = new DocumentType("IAEA");
        //technical subtypes
        technicalReferenceType = new DocumentType("Reference");
        technicalManualType = new DocumentType("Manual");
        //designer subtypes
        designDrawingType = new DocumentType("Drawing");
        designCalculationlType = new DocumentType("Calculation");

        //Documents
        docOneL1 = new Document("docOneL1", "docOneL1_Code", "docOneL1_RegNum", bookFormat, redColor);
        docOneL2_One = new Document("docOneL2_One", "docOneL2_One_Code", "docOneL2_One_RegNum", bookletFormat, blueColor);
        docOneL2_Two = new Document("docOneL2_Two", "docOneL2_Two_Code", "docOneL2_Two_RegNum", bookFormat, redColor);

        docTwoL1 = new Document("docTwoL1", "docTwoL1_Code", "docTwoL1_RegNum", folderFormat, blueColor);
        docTwoL2_One = new Document("docTwoL2_One", "docTwoL2_One_Code", "docTwoL2_One_RegNum", bookletFormat, blueColor);
        docTwoL2_Two = new Document("docTwoL2_Two", "docTwoL2_Two_Code", "docTwoL2_Two_RegNum", bookletFormat, blueColor);

        docThreeL1 = new Document("docThreeL1", "docThreeL1_Code", "docThreeL1_RegNum", folderFormat, blueColor);
        docThreeL2_One = new Document("docThreeL2_One", "docThreeL2_One_Code", "docThreeL2_One_RegNum", folderFormat, blueColor);
        docThreeL2_Two = new Document("docThreeL2_Two", "docThreeL2_Two_Code", "docThreeL2_Two_RegNum", folderFormat, blueColor);


    }

    @Test
    public void testColorEntity() {
        getTestLog().info("Color Entity::Begin test");

        Appender a = startWriteSQLLog("target/testColorEntity.sql");

        getTestLog().info("Color Entity::persist");
        getEm().getTransaction().begin();
        getEm().persist(redColor);
        getEm().persist(greenColor);
        getEm().persist(blueColor);
        getEm().getTransaction().commit();

        //detached stated
        Color exp_redColor = getEm().find(Color.class, redColor.getId());
        Color exp_greenColor = getEm().find(Color.class, greenColor.getId());
        Color exp_blueColor = getEm().find(Color.class, blueColor.getId());

        assertEquals(redColor, exp_redColor);
        assertEquals(greenColor, exp_greenColor);
        assertEquals(blueColor, exp_blueColor);
        assertNotSame("Color Entity::persist::fail::expected not same Color entity", redColor, exp_blueColor);

        getTestLog().info("Color Entity::update/merge tests");
        final String new_color_red = "deep Red color";
        final String new_color_green = "Green grass";
        final String new_color_blue = "Sky blue";
        exp_redColor.setVal(new_color_red);
        exp_greenColor.setVal(new_color_green);
        exp_blueColor.setVal(new_color_blue);
        getEm().getTransaction().begin();
        getEm().merge(exp_redColor);
        getEm().merge(exp_greenColor);
        getEm().merge(exp_blueColor);
        getEm().getTransaction().commit();
        exp_redColor = getEm().find(Color.class, redColor.getId());
        assertNotNull(exp_redColor);
        assertEquals(new_color_red, exp_redColor.getVal());
        exp_greenColor = getEm().find(Color.class, greenColor.getId());
        assertNotNull(exp_greenColor);
        assertEquals(new_color_green, exp_greenColor.getVal());
        assertNotSame(new_color_green, exp_redColor.getVal());
        exp_blueColor = getEm().find(Color.class, blueColor.getId());
        assertEquals(new_color_blue, exp_blueColor.getVal());

        getTestLog().info("Color Entity::remove");
        getEm().getTransaction().begin();
        getEm().remove(redColor);
        getEm().remove(greenColor);
        getEm().remove(blueColor);
        getEm().getTransaction().commit();

        Color exp_null = getEm().find(Color.class, blueColor.getId());
        assertNull(exp_null);
        exp_null = getEm().find(Color.class, greenColor.getId());
        assertNull(exp_null);
        exp_null = getEm().find(Color.class, redColor.getId());
        assertNull(exp_null);

        stopWriteSQLLog(a);

        getTestLog().info("Color Entity::End test");
    }

    @Test
    public void testFormatEntity() {
        getTestLog().info("Format Entity::persist");

        Appender a = startWriteSQLLog("target/testFormatEntity.sql");

        getEm().getTransaction().begin();
        getEm().persist(bookFormat);
        getEm().persist(folderFormat);
        getEm().persist(bookletFormat);
        getEm().getTransaction().commit();

        Format exp_bookFormat = getEm().find(Format.class, bookFormat.getId());
        Format exp_folderFormat = getEm().find(Format.class, folderFormat.getId());
        Format exp_bookletFormat = getEm().find(Format.class, bookletFormat.getId());

        assertEquals(bookFormat, exp_bookFormat);
        assertEquals(folderFormat, exp_folderFormat);
        assertEquals(bookletFormat, exp_bookletFormat);

        assertNotSame("Format Entity::persist::fail::expected not same Format entity", bookFormat, exp_folderFormat);

        getTestLog().info("Format Entity::update/merge");
        final String bookFormat_newval = "Book_newval",
                folderFormat_newval = "Folder_newval",
                bookletFormat_newval = "bookletformat_newval";
        exp_bookFormat.setVal(bookFormat_newval);
        exp_folderFormat.setVal(folderFormat_newval);
        exp_bookletFormat.setVal(bookletFormat_newval);

        getEm().getTransaction().begin();
        getEm().merge(exp_bookFormat);
        getEm().merge(exp_folderFormat);
        getEm().merge(exp_bookletFormat);
        getEm().getTransaction().commit();

        exp_bookFormat = getEm().find(Format.class, exp_bookFormat.getId());
        assertEquals(bookFormat_newval, exp_bookFormat.getVal());
        exp_folderFormat = getEm().find(Format.class, exp_folderFormat.getId());
        assertEquals(folderFormat_newval, exp_folderFormat.getVal());
        exp_bookletFormat = getEm().find(Format.class, exp_bookletFormat.getId());
        assertEquals(bookletFormat_newval, exp_bookletFormat.getVal());

        getTestLog().info("Format Entity::remove");
        getEm().getTransaction().begin();
        getEm().remove(bookFormat);
        getEm().remove(folderFormat);
        getEm().remove(bookletFormat);
        getEm().getTransaction().commit();

        Format exp_null = getEm().find(Format.class, bookFormat.getId());
        assertNull(exp_null);
        exp_null = getEm().find(Format.class, folderFormat.getId());
        assertNull(exp_null);
        exp_null = getEm().find(Format.class, bookletFormat.getId());
        assertNull(exp_null);

        stopWriteSQLLog(a);

        getTestLog().info("Format Entity::End test");
    }

    @Test
    public void testTheme() {
        getTestLog().info("Theme Entity::persist");

        Appender a = startWriteSQLLog("target/testTheme.sql");

        getEm().getTransaction().begin();
        getEm().persist(simpleTheme);
        getEm().persist(manyTheme);
        getEm().persist(otherTheme);
        getEm().getTransaction().commit();

        Theme exp_simpleTheme = getEm().find(Theme.class, simpleTheme.getId());
        Theme exp_manyTheme = getEm().find(Theme.class, manyTheme.getId());
        Theme exp_otherTheme = getEm().find(Theme.class, otherTheme.getId());

        assertEquals(simpleTheme, exp_simpleTheme);
        assertEquals(manyTheme, exp_manyTheme);
        assertEquals(otherTheme, exp_otherTheme);
        assertNotSame("Theme Entity::persist::fail::expected not same Theme entity", simpleTheme, exp_otherTheme);

        getTestLog().info("Theme Entity::update/merge");
        final String simpleTheme_newval = "simpleTheme_newval",
                manyTheme_newval = "manyTheme_newval",
                otherTheme_newval = "otherTheme_newval";
        exp_simpleTheme.setVal(simpleTheme_newval);
        exp_manyTheme.setVal(manyTheme_newval);
        exp_otherTheme.setVal(otherTheme_newval);

        getEm().getTransaction().begin();
        getEm().merge(exp_simpleTheme);
        getEm().merge(exp_manyTheme);
        getEm().merge(exp_otherTheme);
        getEm().getTransaction().commit();

        assertEquals(simpleTheme_newval, getEm().find(Theme.class, exp_simpleTheme.getId()).getVal());
        assertEquals(manyTheme_newval, getEm().find(Theme.class, exp_manyTheme.getId()).getVal());
        assertEquals(otherTheme_newval, getEm().find(Theme.class, exp_otherTheme.getId()).getVal());

        /*
         * TODO: test docs collection (Theme)
         */

        getTestLog().info("Theme Entity::remove");
        getEm().getTransaction().begin();
        getEm().remove(simpleTheme);
        getEm().remove(manyTheme);
        getEm().remove(otherTheme);
        getEm().getTransaction().commit();

        Theme exp_null = getEm().find(Theme.class, simpleTheme.getId());
        assertNull(exp_null);
        exp_null = getEm().find(Theme.class, manyTheme.getId());
        assertNull(exp_null);
        exp_null = getEm().find(Theme.class, otherTheme.getId());
        assertNull(exp_null);

        stopWriteSQLLog(a);
        getTestLog().info("Theme Entity::End test");

    }

    @Test
    public void testDocumentType() {
        getTestLog().info("DocumentType Entity::persist");
        Appender a = startWriteSQLLog("target/testDocumentType.sql");
        /*DocumentType Entity*/
        //         normativeType = new DocumentType("normative");
        //       technicalType = new DocumentType("technical");
        //     designType = new DocumentType("design");

        //normative subtypes
        ArrayList<DocumentType> normativeSbt = new ArrayList<DocumentType>();
        //   normativeStandartType = new DocumentType("Standart");
        normativeSbt.add(normativeStandartType);
        //  normativeTechicalConditionsType = new DocumentType("TechicalConditions");
        normativeSbt.add(normativeTechicalConditionsType);
        //normativeIAEAType = new DocumentType("IAEA");
        normativeSbt.add(normativeIAEAType);
        normativeType.setChildDocTypes(normativeSbt);

        //technical subtypes
        ArrayList<DocumentType> tectechnicalSbt = new ArrayList<DocumentType>();
        //technicalReferenceType =  new DocumentType("Reference");
        tectechnicalSbt.add(technicalReferenceType);
        //  technicalManualType = new DocumentType("Manual");
        tectechnicalSbt.add(technicalManualType);
        technicalType.setChildDocTypes(tectechnicalSbt);

        //designer subtypes
        ArrayList<DocumentType> designerSbt = new ArrayList<DocumentType>();
        //designDrawingType =  new DocumentType("Drawing");
        designerSbt.add(designCalculationlType);
        //designCalculationlType = new DocumentType("Calculation");
        designerSbt.add(designDrawingType);
        designType.setChildDocTypes(designerSbt);

        getEm().getTransaction().begin();
        getEm().persist(normativeType);
        getEm().persist(technicalType);
        getEm().persist(designType);
        getEm().getTransaction().commit();

        //http://www.youtube.com/watch?v=QsDCfrGpyJ8&feature=related
        //http://korrespondent.net/video/ukraine/961514
        getTestLog().info("DocumentType Entity::find & compare");
        DocumentType exp_design = getEm().find(DocumentType.class, designType.getId());
        assertEquals(designType, exp_design);
        assertArrayEquals("DocumentType Entity::Entity Comparsion::Array comparsion for DocumenType.getChildDocTypes() fails",
                designType.getChildDocTypes().toArray(), exp_design.getChildDocTypes().toArray());

        DocumentType exp_technical = getEm().find(DocumentType.class, technicalType.getId());
        assertEquals(technicalType, exp_technical);
        assertArrayEquals("DocumentType Entity::Entity Comparsion::Array comparsion for DocumenType.getChildDocTypes() fails",
                technicalType.getChildDocTypes().toArray(), exp_technical.getChildDocTypes().toArray());

        DocumentType exp_normative = getEm().find(DocumentType.class, normativeType.getId());
        assertEquals(normativeType, exp_normative);
        assertArrayEquals("DocumentType Entity::Entity Comparsion::Array comparsion for DocumenType.getChildDocTypes() fails",
                normativeType.getChildDocTypes().toArray(), exp_normative.getChildDocTypes().toArray());

        final String design_newname = "design_newname",
                technical_newname = "technical_newname",
                normative_newname = "normative_newname";
        exp_design.setVal(design_newname);
        exp_technical.setVal(technical_newname);
        exp_normative.setVal(normative_newname);

        getTestLog().info("DocumentType Entity::merge");
        final String normativeIAEAType_newname = "normativeIAEAType_newname",
                normativeStandartType_newname = "normativeStandartType_newname";
        getEm().find(DocumentType.class, normativeIAEAType.getId()).setVal(normativeIAEAType_newname);
        getEm().find(DocumentType.class, normativeStandartType.getId()).setVal(normativeStandartType_newname);

        final String technicalReferenceType_newname = "technicalReferenceType_newname";
        getEm().find(DocumentType.class, technicalReferenceType.getId()).setVal(technicalReferenceType_newname);
        final String designCalculationlType_newname = "designCalculationlType_newname",
                designDrawingType_newname = "designDrawingType_newname";
        getEm().find(DocumentType.class, designCalculationlType.getId()).setVal(designCalculationlType_newname);
        getEm().find(DocumentType.class, designDrawingType.getId()).setVal(designDrawingType_newname);

        getEm().getTransaction().begin();
        getEm().merge(normativeType);
        getEm().merge(technicalType);
        getEm().merge(designType);
        getEm().getTransaction().commit();

        assertEquals(design_newname, getEm().find(DocumentType.class, exp_design.getId()).getVal());
        assertEquals(technical_newname, getEm().find(DocumentType.class, exp_technical.getId()).getVal());
        assertEquals(normative_newname, getEm().find(DocumentType.class, exp_normative.getId()).getVal());
        assertEquals(normativeIAEAType_newname, getEm().find(DocumentType.class, normativeIAEAType.getId()).getVal());

        getTestLog().info("DocumentType Entity::remove");
        getEm().getTransaction().begin();
        for (DocumentType child : normativeType.getChildDocTypes()) {
            getEm().remove(child);
        }
        getEm().remove(normativeType);
        for (DocumentType child : technicalType.getChildDocTypes()) {
            getEm().remove(child);
        }
        getEm().remove(technicalType);
        for (DocumentType child : designType.getChildDocTypes()) {
            getEm().remove(child);
        }
        getEm().remove(designType);
        getEm().getTransaction().commit();

        stopWriteSQLLog(a);

        getTestLog().info("DocumentType Entity::End test");

    }

    @Test
    public void testDocument_Base() {
        getTestLog().info("Document Entity:: Base (constructor) test::Begin");
        Appender a = startWriteSQLLog("target/testDocument_Base.sql");

        // <editor-fold defaultstate="collapsed" desc="persist  necessary Colors & Formats">
        getTestLog().info("Document Entity::persist  necessary colors (because field Document.Color  must not be Cascade persists)");
        getEm().getTransaction().begin();
        getEm().persist(redColor);
        getEm().persist(greenColor);
        getEm().persist(blueColor);
        getEm().getTransaction().commit();

        getTestLog().info("Document Entity::persist  necessary formats (because field Document.Format  must not be Cascade persists)");
        getEm().getTransaction().begin();
        getEm().persist(bookFormat);
        getEm().persist(folderFormat);
        getEm().persist(bookletFormat);
        getEm().getTransaction().commit();
// </editor-fold>

        getTestLog().info("Document Entity:: Base (constructor) test::persist");
        getEm().getTransaction().begin();
        getEm().persist(docOneL1);
        getEm().persist(docTwoL1);
        getEm().persist(docThreeL1);
        getEm().getTransaction().commit();

        Document exp_docOneL1 = getEm().find(Document.class, docOneL1.getId());
        Document exp_docTwoL1 = getEm().find(Document.class, docTwoL1.getId());
        Document exp_docThreeL1 = getEm().find(Document.class, docThreeL1.getId());

        assertEquals(exp_docOneL1, docOneL1);
        assertEquals(exp_docTwoL1, docTwoL1);
        assertEquals(exp_docThreeL1, docThreeL1);

        getTestLog().info("Document Entity::Base (constructor) test::update/merge");
        /* docOneL1 = new Document("docOneL1", "docOneL1_Code", "docOneL1_RegNum", bookFormat, redColor);   see setUp()   */
        final String docOneL1_new_name = "docOneL1_newname";
        final String docOneL1_new_code = "docOneL1_newcode";
        exp_docOneL1.setName(docOneL1_new_name);
        exp_docOneL1.setCode(docOneL1_new_code);
        exp_docOneL1.setFormat(bookletFormat);

        /* docTwoL1 = new Document("docTwoL1", "docTwoL1_Code", "docTwoL1_RegNum", folderFormat, blueColor); see setUp() */
        final String docTwoL1_new_name = "docTwoL1_newname";
        final String docTwoL1_new_code = "docTwoL1_newcode";
        exp_docTwoL1.setName(docTwoL1_new_name);
        exp_docTwoL1.setCode(docTwoL1_new_code);
        exp_docTwoL1.setColor(redColor);

        /*docThreeL1 = new Document("docThreeL1", "docThreeL1_Code", "docThreeL1_RegNum", folderFormat, blueColor); see setUp() */
        final String docThreeL1_new_name = "docThreeL1_newname";
        final String docThreeL1_new_code = "docThreeL1_newcode";
        exp_docThreeL1.setName(docThreeL1_new_name);
        exp_docThreeL1.setCode(docThreeL1_new_code);
        exp_docThreeL1.setFormat(folderFormat);  //this's not a bug, a'm try force set  THE SAME  format
        exp_docThreeL1.setColor(greenColor);

        getEm().getTransaction().begin();
        getEm().merge(exp_docOneL1);
        getEm().merge(exp_docTwoL1);
        getEm().merge(exp_docThreeL1);
        getEm().getTransaction().commit();

        exp_docOneL1 = getEm().find(Document.class, docOneL1.getId());
        assertEquals(docOneL1_new_name, exp_docOneL1.getName());
        assertEquals(docOneL1_new_code, exp_docOneL1.getCode());
        assertEquals(bookletFormat, exp_docOneL1.getFormat());

        exp_docTwoL1 = getEm().find(Document.class, docTwoL1.getId());
        assertEquals(docTwoL1_new_name, exp_docTwoL1.getName());
        assertEquals(docTwoL1_new_code, exp_docTwoL1.getCode());
        assertEquals(redColor, exp_docTwoL1.getColor());

        exp_docThreeL1 = getEm().find(Document.class, docThreeL1.getId());
        assertEquals(docThreeL1_new_name, exp_docThreeL1.getName());
        assertEquals(docThreeL1_new_code, exp_docThreeL1.getCode());
        assertEquals(greenColor, exp_docThreeL1.getColor());
        assertEquals(folderFormat, exp_docThreeL1.getFormat());

        getTestLog().info("Document  Entity::Base (constructor) test::remove");
        getEm().getTransaction().begin();
        getEm().remove(docOneL1);
        getEm().remove(docTwoL1);
        getEm().remove(docThreeL1);
        getEm().getTransaction().commit();

        // <editor-fold defaultstate="collapsed" desc="remove Colors & Formats">
        getTestLog().info(
                "Document Entity::remove colors (because field Document.Color  must not be Cascade remove)");
        getEm().getTransaction().begin();
        getEm().remove(redColor);
        getEm().remove(greenColor);
        getEm().remove(blueColor);
        getEm().getTransaction().commit();

        getTestLog().info(
                "Document Entity::remove formats (because field Document.Format  must not be Cascade remove)");
        getEm().getTransaction().begin();
        getEm().remove(bookFormat);
        getEm().remove(folderFormat);
        getEm().remove(bookletFormat);
        getEm().getTransaction().commit();
        // </editor-fold>

        stopWriteSQLLog(a);

        getTestLog().info("Document Entity::Base (constructor) test::End");
    }
    /*
     *    '  ->  ' and vice versa
     *        { CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}
     * .   Document.java,   setUp()
     */

    @Test
    public void testDocument_CascadeDM() {
        getTestLog().info("Document Entity::testDocument_CascadeDM::Begin");

        Appender a = startWriteSQLLog("target/testDocument_CascadeDM.sql");

        // <editor-fold defaultstate="collapsed" desc="persist  necessary Colors & Formats">
        getTestLog().info("Document Entity::persist  necessary colors (because field Document.Color  must not be Cascade persists)");
        getEm().getTransaction().begin();
        getEm().persist(redColor);
        getEm().persist(greenColor);
        getEm().persist(blueColor);
        getEm().getTransaction().commit();

        getTestLog().info("Document Entity::persist  necessary formats (because field Document.Format  must not be Cascade persists)");
        getEm().getTransaction().begin();
        getEm().persist(bookFormat);
        getEm().persist(folderFormat);
        getEm().persist(bookletFormat);
        getEm().getTransaction().commit();
// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="setup parent and childs  Document entities, fills childDocs collections">
        getTestLog().info("Document Entity::testDocument_CascadePersist::setup parent and childs  Document entities");
        ArrayList<Document> docOneL1_Childs = new ArrayList<Document>();
        docOneL2_One.setParentDoc(docOneL1);
        docOneL1_Childs.add(docOneL2_One);
        docOneL2_Two.setParentDoc(docOneL1);
        docOneL1_Childs.add(docOneL2_Two);
        docOneL1.setChildDocs(docOneL1_Childs);

        ArrayList<Document> docTwoL1_Childs = new ArrayList<Document>();
        docTwoL2_One.setParentDoc(docTwoL1);
        docTwoL1_Childs.add(docTwoL2_One);
        docTwoL2_Two.setParentDoc(docTwoL1);
        docTwoL1_Childs.add(docTwoL2_Two);
        docTwoL1.setChildDocs(docTwoL1_Childs);

        ArrayList<Document> docThreeL1_Childs = new ArrayList<Document>();
        docThreeL2_One.setParentDoc(docThreeL1);
        docThreeL1_Childs.add(docThreeL2_One);
        docThreeL2_Two.setParentDoc(docThreeL1);
        docThreeL1_Childs.add(docThreeL2_Two);
        docThreeL1.setChildDocs(docThreeL1_Childs);
// </editor-fold>

        getTestLog().info("Document Entity::testDocument_CascadeDM::persist");

        getEm().getTransaction().begin();
        getEm().persist(docOneL1);
        getEm().persist(docTwoL1);
        getEm().persist(docThreeL1);
        getEm().getTransaction().commit();

        Document exp_docOneL1 = getEm().find(Document.class, docOneL1.getId());
        assertEquals(docOneL1, exp_docOneL1);
        assertArrayEquals(docOneL1_Childs.toArray(), exp_docOneL1.getChildDocs().toArray());
        Document exp_docTwoL1 = getEm().find(Document.class, docTwoL1.getId());
        assertEquals(docTwoL1, exp_docTwoL1);
        assertArrayEquals(docTwoL1_Childs.toArray(), exp_docTwoL1.getChildDocs().toArray());
        Document exp_docThreeL1 = getEm().find(Document.class, docThreeL1.getId());
        assertEquals(docThreeL1, exp_docThreeL1);
        assertArrayEquals(docThreeL1_Childs.toArray(), exp_docThreeL1.getChildDocs().toArray());

        getTestLog().info("Document Entity::testDocument_CascadeDM::update/merge");
        final String docOneL1_newname = "docOneL1_newname",
                docOneL2_Two_newname = "docOneL2_Two_newname",
                docOneL2_Two_newcode = "docOneL2_Two_newcode";
        exp_docOneL1.setName(docOneL1_newname);
        Document exp_docOneL2_Two = getEm().find(Document.class, docOneL2_Two.getId());
        assertNotNull(exp_docOneL2_Two);
        exp_docOneL2_Two.setName(docOneL2_Two_newname);
        exp_docOneL2_Two.setCode(docOneL2_Two_newcode);

        final String docTwoL1_newname = "docTwoL1_newname",
                docTwoL2_One_newname = "docTwoL2_One_newname",
                docTwoL2_One_newcode = "docTwoL2_One_newcode",
                docTwoL2_Two_newname = "docTwoL2_Two_newname",
                docTwoL2_Two_newcode = "docTwoL2_Two_newcode";
        exp_docTwoL1.setName(docTwoL1_newname);
        Document exp_docTwoL2_One = getEm().find(Document.class, docTwoL2_One.getId());
        assertNotNull(exp_docTwoL2_One);
        exp_docTwoL2_One.setName(docTwoL2_One_newname);
        exp_docTwoL2_One.setCode(docTwoL2_One_newcode);
        Document exp_docTwoL2_Two = getEm().find(Document.class, docTwoL2_Two.getId());
        assertNotNull(exp_docTwoL2_Two);
        exp_docTwoL2_Two.setName(docTwoL2_Two_newname);
        exp_docTwoL2_Two.setCode(docTwoL2_Two_newcode);

        final String docThreeL1_newname = "docThreeL1_newname",
                docThreeL2_One_newname = "docThreeL2_One_newname",
                docThreeL2_Two_newname = "docThreeL2_Two_newname";
        exp_docThreeL1.setName(docThreeL1_newname);
        //   docThreeL2_One = new Document("docThreeL2_One", "docThreeL2_One_Code", "docThreeL2_One_RegNum", folderFormat, blueColor); see setUp();
        Document exp_docThreeL2_One = getEm().find(Document.class, docThreeL2_One.getId());
        assertNotNull(exp_docThreeL2_One);
        exp_docThreeL2_One.setName(docThreeL2_One_newname);
        exp_docThreeL2_One.setFormat(bookFormat);
        //docThreeL2_Two = new Document("docThreeL2_Two", "docThreeL2_Two_Code", "docThreeL2_Two_RegNum", folderFormat, blueColor);see setUp();
        Document exp_docThreeL2_Two = getEm().find(Document.class, docThreeL2_Two.getId());
        assertNotNull(exp_docThreeL2_Two);
        exp_docThreeL2_Two.setName(docThreeL2_Two_newname);
        exp_docThreeL2_Two.setColor(redColor);

        getEm().getTransaction().begin();
        getEm().merge(exp_docOneL1);
        getEm().merge(exp_docTwoL1);
        getEm().merge(exp_docThreeL1);
        getEm().getTransaction().commit();

        exp_docOneL1 = getEm().find(Document.class, docOneL1.getId());
        assertEquals(docOneL1_newname, exp_docOneL1.getName());
        exp_docOneL2_Two = getEm().find(Document.class, docOneL2_Two.getId());
        assertEquals(docOneL2_Two_newname, exp_docOneL2_Two.getName());
        assertEquals(docOneL2_Two_newcode, exp_docOneL2_Two.getCode());

        exp_docTwoL1 = getEm().find(Document.class, docTwoL1.getId());
        assertEquals(docTwoL1_newname, exp_docTwoL1.getName());
        exp_docTwoL2_One = getEm().find(Document.class, docTwoL2_One.getId());
        assertEquals(docTwoL2_One_newname, exp_docTwoL2_One.getName());
        assertEquals(docTwoL2_One_newcode, exp_docTwoL2_One.getCode());
        exp_docTwoL2_Two = getEm().find(Document.class, docTwoL2_Two.getId());
        assertEquals(docTwoL2_Two_newname, exp_docTwoL2_Two.getName());
        assertEquals(docTwoL2_Two_newcode, exp_docTwoL2_Two.getCode());

        exp_docThreeL1 = getEm().find(Document.class, docThreeL1.getId());
        assertEquals(docThreeL1_newname, exp_docThreeL1.getName());
        exp_docThreeL2_One = getEm().find(Document.class, docThreeL2_One.getId());
        assertEquals(docThreeL2_One_newname, exp_docThreeL2_One.getName());
        assertEquals(bookFormat, exp_docThreeL2_One.getFormat());
        exp_docThreeL2_Two = getEm().find(Document.class, docThreeL2_Two.getId());
        assertEquals(docThreeL2_Two_newname, exp_docThreeL2_Two.getName());
        assertEquals(redColor, exp_docThreeL2_Two.getColor());
        /*
         * TODO: add refresh test(Document_CascadeDM)
         */
        getTestLog().info("Document Entity::testDocument_CascadeDM::remove");

        getEm().getTransaction().begin();

        for (Document child : docOneL1.getChildDocs()) {
            getEm().remove(child);
        }
        getEm().remove(docOneL1);

        for (Document child : docTwoL1.getChildDocs()) {
            getEm().remove(child);
        }
        getEm().remove(docTwoL1);

        for (Document child : docThreeL1.getChildDocs()) {
            getEm().remove(child);
        }
        getEm().remove(docThreeL1);

        getEm().getTransaction().commit();

        // <editor-fold defaultstate="collapsed" desc="remove Colors & Formats">
        getTestLog().info(
                "Document Entity::remove colors (because field Document.Color  must not be Cascade remove)");
        getEm().getTransaction().begin();
        getEm().remove(redColor);
        getEm().remove(greenColor);
        getEm().remove(blueColor);
        getEm().getTransaction().commit();

        getTestLog().info(
                "Document Entity::remove formats (because field Document.Format  must not be Cascade remove)");
        getEm().getTransaction().begin();
        getEm().remove(bookFormat);
        getEm().remove(folderFormat);
        getEm().remove(bookletFormat);
        getEm().getTransaction().commit();
        // </editor-fold>

        stopWriteSQLLog(a);

        getTestLog().info("Document Entity::testDocument_CascadeDM::End");
    }

  
  
}
