package se.antoneliasson.attendance.gui;

import java.util.Observable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.antoneliasson.attendance.models.Database;
import se.antoneliasson.attendance.models.Registry;

public class PersonTableModelTest {

    PersonTableModel instance;

    public PersonTableModelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws ClassNotFoundException {
        Database db = new Database("example.db");
        Registry registry = new Registry(db);

        instance = new PersonTableModel(registry);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetValueAt() {
        int row = 0;
        int column = 1;
        Object expResult = "Eva-Rakel Göransson";
        Object result = instance.getValueAt(row, column);
        assertEquals(expResult, result);
    }

    @Test
    public void testFilterAll() {
        Observable o = null;
        instance.update(o, "");
        assertEquals(8, instance.getRowCount());
    }

    @Test
    public void testFilterTwo() {
        Observable o = null;
        instance.update(o, "h");
        assertEquals(2, instance.getRowCount());
    }

    @Test
    public void testFilterNone() {
        Observable o = null;
        instance.update(o, "qwerty");
        assertEquals(0, instance.getRowCount());
    }
}
