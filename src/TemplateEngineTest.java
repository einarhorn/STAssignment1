import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import st.EntryMap;
import st.TemplateEngine;

public class TemplateEngineTest {

    private EntryMap map;

    private TemplateEngine engine;

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }

    // Constants
    final String VALID_ARG_1 = "name";
    final String VALID_ARG_2 = "Adam";
    
    
    
    
    // EntryMap Spec 1 - Template cannot be null or empty. Valid template should pass.
    
    // EntryMap - Spec 1 - Template is null
    @Test(expected=RuntimeException.class)
    public void EntryMapSpec1_NullTemplate(){
    	map.store(null, VALID_ARG_2, false);
        	
    }
    
    // EntryMap - Spec 1 - Template is empty string
    @Test(expected=RuntimeException.class)
    public void EntryMapSpec1_EmptyTemplate(){
    	map.store("", VALID_ARG_2, false);
        	
    }
    
    // EntryMap - Spec 1 - Template is valid
    @Test
    public void EntryMapSpec1_ValidTemplate(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    
 // EntryMap - Spec 1 - Template is composed of multiple words
    @Test
    public void EntryMapSpec1_ValidTemplateWithMultipleWords(){
    	map.store(VALID_ARG_1, "james james james", false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello james james james", result);
    }
    
    
    
    
    
    // EntryMap Spec 2 - Replace value cannot be null. Empty or valid should pass.
    
    // EntryMap - Spec 2 - Replace value is null
    @Test(expected=RuntimeException.class)
    public void EntryMapSpec2_NullReplace(){
    	map.store(VALID_ARG_1, null, false);
        
    }

    // EntryMap - Spec 2 - Replace value is empty
    @Test
    public void EntryMapSpec2_EmptyReplace(){
    	map.store(VALID_ARG_1, "", false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello ", result);
    }
    
    // EntryMap - Spec 2 - Replace value is valid
    @Test
    public void EntryMapSpec2_ValidReplace(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    

   
    
    
    
    
    // EntryMap Spec 3 - Case sensitive flag is optional and can be null
    // 			In case of null, template matching is case insensitive
    // Check true, false, null
    
    // Template matching case sensitive
    @Test
    public void EntryMapSpec3_TrueCaseSensitive(){
    	map.store("name", VALID_ARG_2, true);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }
    
    // Template matching case insensitive
    @Test
    public void EntryMapSpec3_FalseCaseSensitive(){
    	map.store("NAME", VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }
    
    // Template matching should be case insensitive when null
    @Test
    public void EntryMapSpec3_NullCaseSensitive(){
    	map.store("NAME", VALID_ARG_2, null);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }

    
    // TemplateEngine Specifications
    
    // TemplateEngine - Spec 1 - Template can be null or empty. If null or empty, evaluate returns unchanged template string.
    
    // TemplateEngine - Spec 1 - Template is null
    @Test
    public void TemplateEngineSpec1_NullTemplate(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate(null, map,"delete-unmatched");
    	assertEquals(null, result);
        	
    }
    
    // TemplateEngine - Spec 1 - Template is empty string
    @Test
    public void TemplateEngineSpec1_EmptyTemplate(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("", map,"delete-unmatched");
    	assertEquals("", result);
        	
    }
    
    // TemplateEngine - Spec 1 - Template is valid
    @Test
    public void TemplateEngineSpec1_ValidTemplate(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    

    
    // TemplateEngine - Spec 2 - EntryMap can be null. If EntryMap is null, unchanged template string can be returned
    // TemplateEngine - Spec 2 - EntryMap is null
    @Test
    public void TemplateEngineSpec2_NullEntryMap(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", null,"delete-unmatched");
    	assertEquals("Hello ${name}", result);
        	
    }
    
    // TemplateEngine - Spec 2 - EntryMap is valid
    @Test
    public void TemplateEngineSpec2_ValidEntryMap(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map, "delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    
    
    
    // TemplateEngine - Spec 3 - Matching mode - Null or other value defaults to "delete-unmatched"
    // AMBIGUOUS SPECIFICATION - Spec says matching mode both allowed and not allowed to be null.
    
    // Spec 3 - Matching mode is null. Should be equivalent to if matching mode was set to "delete-unmatched"
    @Test
    public void TemplateEngineSpec3_NullMatchingMode(){
    	map.store("randomTemplate", VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map, null);
    	assertEquals("Hello ", result);
    }
        
    // Spec 3 - Matching mode is invalid string. Should be equivalent to if matching mode was set to "delete-unmatched"
    @Test
    public void TemplateEngineSpec3_InvalidMatchingMode(){
    	map.store("randomTemplate", VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map, "blarrr");
    	assertEquals("Hello ", result);
    }
    
    //Spec 3 - Matching mode is valid string: "delete-unmatched"
    @Test
    public void TemplateEngineSpec3_DeleteUnmatchedMatchingMode(){
    	map.store("randomTemplate", VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map, "delete-unmatched");
    	assertEquals("Hello ", result);
    }
    
    // Spec 3 - Matching mode is valid string: "keep-unmatched"
    @Test
    public void TemplateEngineSpec3_KeepUnmatchedMatchingMode(){
    	map.store("randomTemplate", VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map, "keep-unmatched");
    	assertEquals("Hello ${name}", result);
    }
    
    
    // TemplateEngine - Spec 4 - Everything between "${" and "}" are counted as templates
    
    // Spec 4 - Simple valid templates
    @Test
    public void TemplateEngineSpec4_SimpleTemplateStrings(){
    	map.store("name", "James", false);
    	map.store("car", "bmw", false);
    	map.store("age", "old", false);
    	
    	String result = engine.evaluate("That's ${name}, he's ${age} and drives an ugly ${car}", map, "delete-unmatched");
    	assertEquals("That's James, he's old and drives an ugly bmw", result);
    }
    
    // Spec 4 - Simple valid templates composed of multiple words
    @Test
    public void TemplateEngineSpec4_MultipleWordTemplateStrings(){
    	map.store("name names namez", "James", false);
    	map.store("car cars carz", "bmw", false);
    	map.store("age ages agez", "old", false);
    	
    	String result = engine.evaluate("That's ${name names namez}, he's ${age ages agez} and drives an ugly ${car cars carz}", map, "delete-unmatched");
    	assertEquals("That's James, he's old and drives an ugly bmw", result);
    }
    
    

    @Test
    public void EntryMapSpec4Test1(){ 
    	map.store("name", "Einar", true);
    	map.store("name", "Ritvik", false);
    	String result1 = engine.evaluate("Second name is ${NAME}", map, "keep-unmatched");
    	String result2 = engine.evaluate("First name is ${name}", map, "keep-unmatched");
    	assertEquals("Second name is Ritvik",result1);
    	assertEquals("First name is Einar", result2);
    }
    
    @Test
    public void EntryMapSpec4Test2(){
    	map.store("name", "Romeo", false);
    	map.store("name", "Juliet", false);
    	String result1 = engine.evaluate("First name is ${name}", map, "keep-unmatched");
    	assertEquals("First name is Romeo",result1);
    }
    
    @Test
    public void EntryMapSpec5Test1(){ // THIS ONE DOESNT PASSES BUT THAT IS DUE TO ANOTHER ERROR IN THE SPEC OF TEMPLATE ENGINE

    	map.store("name", "adam", false);
    	map.store("name", "adam", false);
    	String result1 = engine.evaluate("The only name is ${name}", map, "keep-unmatched");
    	String result2 = engine.evaluate("There should be no other ${name}",map, "keep-unmatched");
    	assertEquals("The only name is adam",result1);
    	assertEquals("There should be no other ${name}",result2);
    }
    
    @Test
    public void EntryMapSpec5Test2() {
    	map.store("name", "bernie", true);
    	map.store("name", "bernie", false);
    	String result1 = engine.evaluate("FEEL THE BERN!, ${NAME}", map, "keep-unmatched");
    	String result2 = engine.evaluate("feel the bern, ${name}",map, "keep-unmatched");
    	assertEquals("FEEL THE BERN!, bernie",result1);
    	assertEquals("feel the bern, bernie",result2);
    }
    
    @Test
    public void EntryMapSpec5Test3() // THIS ONE DOESNT PASSES BUT THAT IS DUE TO ANOTHER ERROR IN THE SPEC OF TEMPLATE ENGINE
    {
    	map.store("name", "Donald", false);
    	map.store("name", "trump", false);
    	String result1 = engine.evaluate("${name} Duck is so funny!",map,"keep-unmatched");
    	String resutl2 = engine.evaluate("I still have a ${name} card up my sleeve!", map, "keep-unmatched");
    	assertEquals("Donald Duck is so funny",result1);
    	assertEquals("I still have a trump card up my sleeve!",resutl2);
    }
    
    @Test
    public void EntryMapSpec5Test4() 
    {
    	map.store("firstName", "Hilary", false);
    	map.store("firstname", "Hilary", false);
    	int size = map.getEntries().size();
    	assertEquals(2,size);
    }
    
    @Test
    public void TemplateEngineSpec5Test1()    {
    	map.store("first name", "Jackie", false);
    	String result = engine.evaluate("first name is ${firstname}",map, "keep-unmatched");
    	assertEquals("first name is Jackie", result);
    }
    
    @Test
    public void TemplateEngineSpec5Test2() {
    	map.store("first"+'\t'+"name", "Jackie", false);
    	String result = engine.evaluate("first name is ${firstname}", map, "keep-unmatched");
    	assertEquals("first name is Jackie", result);	
    }
    
    @Test
    public void TemplateEngineSpec5Test3(){
    	map.store("first"+'\n'+"name", "Jackie", false);
    	String result = engine.evaluate("first name is ${firstname}", map, "keep-unmatched");
    	assertEquals("first name is Jackie", result);	
    }
    
    @Test
    public void TemplateEngineSpec5Test4()    {
    	map.store("First Name", "Chan", true);
    	String result = engine.evaluate("first name is ${FirstName}", map, "keep-unmatched");
    	assertEquals("first name is Chan", result);	
    }
    
    @Test
    public void TemplateEngineSpec5Test5() {
    	map.store("First"+'\t'+"Name", "Chan", true);
    	String result = engine.evaluate("first name is ${FirstName}", map, "keep-unmatched");
    	assertEquals("first name is Chan", result);	
    }
    
    @Test
    public void TemplateEngineSpec5Test6(){
    	map.store("First"+'\n'+"Name", "Chan", true);
    	String result = engine.evaluate("first name is ${FirstName}", map, "keep-unmatched");
    	assertEquals("first name is Chan", result);	
    }
    
}