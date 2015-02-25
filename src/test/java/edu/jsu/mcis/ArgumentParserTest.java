package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ArgumentParserTest {
    private ArgumentParser ap;
	
    @Before
    public void startArgumentParser(){
        ap = new ArgumentParser();
    }
	
    @Test
    public void testAddArgumentsNoDescription() {
        String one = "Test Name 1";
        String two = "Test Name 2";
        String three = "Test Name 3";
        ap.addArguments(one, "String");
        ap.addArguments(two, "int");
        ap.addArguments(three, "boolean");
        String[] inp = {"Hello","5","true"};
        ap.parse(inp);
        assertEquals("Hello", (String) ap.getValue(one));
        assertEquals(5, (int) ap.getValue(two));
        assertEquals(true, (boolean) ap.getValue(three));
    }
    
    @Test
    public void testAddArgumentsWithDescription() {
        String one = "Test Name 1";
        String two = "Test Name 2";
        String three = "Test Name 3";
        ap.addArguments(one, "String", "It's a string thing");
        ap.addArguments(two, "int", "It's an int!");
        ap.addArguments(three, "boolean", "It is a bool");
        String[] inp = {"Hello","5","True"};
        ap.parse(inp);
        assertEquals("Hello", (String) ap.getValue(one));
        assertEquals(5, (int) ap.getValue(two));
        assertEquals(true, (boolean) ap.getValue(three));
    }
    
    @Test
    public void testParseValuesNotDashH() {
        String one = "Length";
        String two = "Width";
        String three = "Height";
        String four = "Name";
        ap.addArguments(one, "float", "Length of the object");
        ap.addArguments(two, "boolean", "Width of the object");
        ap.addArguments(three, "int", "Height of the object");
        ap.addArguments(four, "String", "Name of object");
        String[] inp = {"12.34", "False", "7", "Fred"};
        ap.parse(inp);
        assertEquals(12.34, (float) ap.getValue(one), 0.01);
        assertEquals(false, (boolean) ap.getValue(two));
        assertEquals(7, (int) ap.getValue(three));
        assertEquals("Fred", (String) ap.getValue(four));   
    }
    
    @Test
    public void testAddingProgramDescriptionAndName() {
        String one = "Length";
        String two = "Width";
        String three = "Height";
        String four = "Name";
        ap.addArguments(one, "float", "Length of the object");
        ap.addArguments(two, "boolean", "Width of the object");
        ap.addArguments(three, "int", "Height of the object");
        ap.addArguments(four, "String", "Name of object");
        ap.setProgramDescription("This program prints arguments");
        ap.setProgramName("ArgumentPrinter");
        String[] inp = {"12.34", "False", "7", "Fred"};
        ap.parse(inp);
        assertEquals(12.34, (float) ap.getValue(one), 0.01);
        assertEquals(false, (boolean) ap.getValue(two));
        assertEquals(7, (int) ap.getValue(three));
        assertEquals("Fred", (String) ap.getValue(four)); 
        
    }
   
    @Test
    public void testOptionalArgumentDefaultValue() {
        ap.addArguments("thing", "float", "Length of the object");
        ap.addOptionalArgument("stuff", "5");
        assertEquals("5", (String) ap.getValue("stuff"));
    }
    
    @Test
    public void testOptionalArgumentDefaultValueBeingChanged() {
        ap.addArguments("thing", "float", "Length of the object");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"--stuff", "10", "4.6"};
        ap.parse(inp);
        assertEquals("10", (String) ap.getValue("stuff"));
        
    }  
    
    @Test
    public void testSingleArgumentNoDefaultValue() {
        ap.addArguments("thing", "float", "Length of the object");
        ap.addOptionalArgument("stuff");
        String[] inp = {"--stuff", "5", "4.2"};
        ap.parse(inp);
        assertEquals("5", (String) ap.getValue("stuff"));
        
    }
    
    @Test
    public void testBooleanMultipleTimesTrueAndFalse() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        String[] inp = {"true", "false", "true"};
        ap.parse(inp);
        assertEquals(true, (boolean) ap.getValue("Arg 1"));
        assertEquals(false, (boolean) ap.getValue("Arg 2"));
        assertEquals(true, (boolean) ap.getValue("Arg 3"));  
    }
    
    @Test
    public void testShortOptionalArgumentForLongName() {
        ap.addArguments("Length", "int", "Length of the object");
        ap.addOptionalArgument("type", " ", "t");
        String[] inp = {"-t", "circle", "5"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
    }
    
    @Test
    public void testMultipleShortOptionalArgumentForLongName() {
        ap.addArguments("Length", "int", "Length of the object");
        ap.addArguments("Width", "int", "Width of the object");
        ap.addArguments("Height", "int", "Height of the object");
        ap.addOptionalArgument("type", " ", "t");
        ap.addOptionalArgument("color", " ", "c");
        String[] inp = {"-t", "circle", "5", "-c", "red", "7", "10"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
        assertEquals("red", (String) ap.getValue("color"));
    }
    
    @Test
    public void testOneShortOptionalArgumentForLongNameAndOneLong() {
        ap.addArguments("Length", "int", "Length of the object");
        ap.addArguments("Width", "int", "Width of the object");
        ap.addArguments("Height", "int", "Height of the object");
        ap.addOptionalArgument("type", " ", "t");
        ap.addOptionalArgument("color", " ", "c");
        String[] inp = {"-t", "circle", "5", "--color", "red", "7", "10"};
        ap.parse(inp);
        assertEquals("circle", (String) ap.getValue("type")); 
        assertEquals("red", (String) ap.getValue("color"));
    }
    
    @Test
    public void testDashDashFront() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"--stuff", "4", "true", "false", "true"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashMiddle() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = { "true", "false", "--stuff", "4", "true"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }  
    
    @Test
    public void testDashDashEnd() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addArguments("Arg 2", "boolean", "This should be false");
        ap.addArguments("Arg 3", "boolean", "This should be true");
        ap.addOptionalArgument("stuff", "5");
        String[] inp = {"true", "false", "true", "--stuff", "4"};
        ap.parse(inp);
        assertEquals("4", (String) ap.getValue("stuff"));
    }
    
    @Test
    public void testFlagTrueBeginning() {
        ap.addArguments("Arg 1", "boolean", "This should be true");
        ap.addFlag("t");
        String[] inp = {"-t", "false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
    }
    
    @Test
    public void testFlagFalseBeginning() {
        ap.addFlag("t");
        ap.addArguments("Arg 1", "boolean", "This should be true");
        String[] inp = {"true"};
        ap.parse(inp);
        assertEquals(false, ap.getValue("t"));
    }
    
    @Test
    public void testFlagTrueMiddle() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        String[] inp = {"true","-t","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
    }
    
    @Test
    public void testMultipleFlagsSeperatedBothTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-t","false", "-r"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
        assertEquals(true, ap.getValue("r"));
    }
    
    @Test
    public void testMultipleFlagsSeperatedOneTrueOtherFalse() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-t","false"};
        ap.parse(inp);        
        assertEquals(true, ap.getValue("t"));
        assertEquals(false, ap.getValue("r"));
    }
    
    @Test
    public void testTwoFlagsTogetherInMiddleBothTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("t");
        ap.addFlag("r");
        String[] inp = {"true","-tr","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("t"));
        assertEquals(true, ap.getValue("r"));
    }
    
    @Test
    public void testFourFlagsTwoTogetherTwoSeperateAllTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("k");
        ap.addFlag("a");
        ap.addFlag("n");
        ap.addFlag("e");
        String[] inp = {"true","-ke","false","-na"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(true, ap.getValue("n"));
        assertEquals(true, ap.getValue("e"));
    }
    
    @Test
    public void testFourFlagsTwoTogetherOneSeperateThreeTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("k");
        ap.addFlag("a");
        ap.addFlag("n");
        ap.addFlag("e");
        String[] inp = {"true","-ke","false","-a"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(false, ap.getValue("n"));
        assertEquals(true, ap.getValue("e"));
    }
    
    @Test
    public void testFourFlagsAllTogetherAllTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("k");
        ap.addFlag("a");
        ap.addFlag("n");
        ap.addFlag("e");
        String[] inp = {"true","-kane","false"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(true, ap.getValue("n"));
        assertEquals(true, ap.getValue("e"));
    }
    
    @Test
    public void testThreeFlagsTogetherOneFlaggedTwiceAllTrue() {
        ap.addArguments("Arg 1", "boolean", "Test argument as well");
        ap.addArguments("Arg 2", "boolean", "Test argument");
        ap.addFlag("k");
        ap.addFlag("a");
        ap.addFlag("n");
        String[] inp = {"true","-ka","false", "-an"};
        ap.parse(inp);
        assertEquals(true, ap.getValue("k"));
        assertEquals(true, ap.getValue("a"));
        assertEquals(true, ap.getValue("n"));
    }
	
    @Test
    public void testSetDefaultValue(){
        ap.addOptionalArgument("int");
        ap.addOptionalArgDefaultValue("int","20");
        assertEquals("20", (String) ap.getValue("int"));
    }
    
    @Test(expected = ArgumentParser.InvalidDataTypeException.class)
    public void testEnterFloatWhenShouldBeInt() {
        ap.addArguments("Arg 1", "int");
        String[] inp = {"1.5"};
        ap.parse(inp);
    }
    
    @Test(expected = ArgumentParser.InvalidDataTypeException.class)
    public void testEnterIntWhenShouldBeBoolean() {
        ap.addArguments("Arg 1", "boolean");
        String[] inp = {"1"};
        ap.parse(inp);
    }
    
    @Test(expected = ArgumentParser.InvalidDataTypeException.class)
    public void testEnterStringWhenShouldBeFloat() {
        ap.addArguments("Arg 1", "float");
        String[] inp = {"hello"};
        ap.parse(inp);
    }
    
    @Test(expected = ArgumentParser.PositionalArgumentException.class)
    public void testUserEntersTooManyPositionalArguments() {
        ap.addArguments("Arg 1", "int");
        String[] inp = {"1", "2"};
        ap.parse(inp);
    }
    
    @Test(expected = ArgumentParser.PositionalArgumentException.class)
    public void testNotEnoughPositionalArgsGiven() {
        ap.addArguments("Arg 1", "int");
        ap.addArguments("Arg2", "int");
        String[] inp = {"1"};
        ap.parse(inp);
    }
    
    @Test(expected = ArgumentParser.NoArgCalledException.class)
    public void testProductOwnerCallsForValueThatIsNotAnArgument() {
        ap.addArguments("Length", "int");
        String[] inp = {"5"};
        ap.parse(inp);         
        ap.getValue("Width");
    }
    
    @Test(expected = ArgumentParser.InvalidDataTypeException.class)
    public void testProductOwnerAddsArgumentWithInproperDataType() {
        ap.addArguments("Length", "int"); 
        ap.addArguments("Length", "type");
    }
    
    @Test (expected = ArgumentParser.InvalidOptionalArgumentException.class)
    public void testUserEnterInvalidLongArgument() {
        ap.addOptionalArgument("type");
        String[] inp = {"--circle"};
        ap.parse(inp);
    }
    
    @Test (expected = ArgumentParser.InvalidOptionalArgumentException.class)
    public void testUserEnterInvalidShortArgument() {
        ap.addOptionalArgument("type", "", "t");
        String[] inp = {"-c"};
        ap.parse(inp);
    }
    
}
