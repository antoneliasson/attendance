package se.antoneliasson.attendance.models;

import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author anton
 */
public class DatabaseTest {
    private Database instance;
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException {
        instance = new Database("example.db");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class Registry.
     */
    @Ignore @Test
    public void testInsert() {
        System.out.println("insert");
        Map<String, String> fields = null;
        instance.insertPerson(fields);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class Registry.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        String filter = "h";
        List<Person> result = instance.find(filter);
        assertEquals(2, result.size());
    }
    
}
