package at.tgm.insy.theexporter.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static at.tgm.insy.theexporter.Main.main;
import static org.junit.Assert.assertEquals;

public class CommandLineControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream(); 

    @Before
    public void setUpStreams() { 
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent)); 
    }
    
    @Test
    public void testUnsupportedDatabase() throws Exception {
        String[] args = new String[]{};
        main(args);
        assertEquals("Required Arguments (Database, Expression and Table) not found."+
                        "Please look at the help page using -help",
                     "",
                     outContent.toString()
                    );
    }
    
    @Test
    public void testGetHost() throws Exception {
        String[] args = new String[]{
                "-h", ""                
        };
    }

    @Test
    public void testGetUser() throws Exception {

    }

    @Test
    public void testGetPassword() throws Exception {

    }

    @Test
    public void testGetDatabase() throws Exception {

    }

    @Test
    public void testGetDatabaseType() throws Exception {

    }

    @Test
    public void testGetOrderBy() throws Exception {

    }

    @Test
    public void testGetOrderSort() throws Exception {

    }

    @Test
    public void testGetFilter() throws Exception {

    }

    @Test
    public void testGetSeparator() throws Exception {

    }

    @Test
    public void testGetExpression() throws Exception {

    }

    @Test
    public void testGetOutputFile() throws Exception {

    }

    @Test
    public void testGetTable() throws Exception {

    }

    @After
    public void cleanUpStreams() { 
        System.setOut(null); 
        System.setErr(null); 
    }
}
