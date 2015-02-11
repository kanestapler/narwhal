package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
    
    @Test
    public void testGetArgWithLengthOf10() {
        ArgumentParser ap = new ArgumentParser();
        myArgs.put("Length", 10);
        assertEquals(ap.getArg("Length"), 10);
    }
    
    @Test
    public void AddArgLengthToAP(){
        ArgumentParser ap = new ArgumentParser();
        ap.myArgs.put("Length", 1);
        int t = ap.myArgs.get("Length");
        assertEquals(1, t);
    }
    
    
}