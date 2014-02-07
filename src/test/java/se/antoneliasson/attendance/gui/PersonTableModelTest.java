package se.antoneliasson.attendance.gui;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.antoneliasson.attendance.models.Database;

public class PersonTableModelTest {

    private PersonTableModel instance;

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
        instance = new PersonTableModel(db);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetValueAt() {
        assertEquals("Eva-Rakel Göransson", instance.getValueAt(0, 0));
        assertEquals("733568365", instance.getValueAt(0, 1));
    }

    @Test
    public void testFilterAll() {
        instance.filter("");
        assertEquals(8, instance.getRowCount());
    }

    @Test
    public void testFilterTwo() {
        instance.filter("h");
        assertEquals(2, instance.getRowCount());
    }

    @Test
    public void testFilterNone() {
        instance.filter("qwerty");
        assertEquals(0, instance.getRowCount());
    }
}
