package se.antoneliasson.attendance.controllers;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import se.antoneliasson.attendance.models.Database;

/**
 *
 * @author anton
 */
@RunWith(JUnit4.class) public class ImportTest {
    Importer instance;
 
    @Before public void setUp() throws Exception {
        new File("test.db").delete();
        
        Database db = new Database("test.db");
        instance = new Importer(db);
    }
    
    /**
     * Test of simpleImport method, of class Import.
     */
    @Test public void testSimpleImport() {
        String filename = "testdata.csv";
        instance.csvImport(filename);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
