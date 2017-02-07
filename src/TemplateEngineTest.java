import static org.junit.Assert.assertEquals;
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
    public void EntryMapSpec1_Null(){
    	map.store(null, VALID_ARG_2, false);
        	
    }
    
    // EntryMap - Spec 1 - Template is empty string
    @Test(expected=RuntimeException.class)
    public void EntryMapSpec1_Empty(){
    	map.store("", VALID_ARG_2, false);
        	
    }
    
    // EntryMap - Spec 1 - Template is valid
    @Test
    public void EntryMapSpec1_Valid(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    
    
    
    
    
    
    // EntryMap Spec 2 - Replace value cannot be null. Empty or valid should pass.
    
    // EntryMap - Spec 2 - Replace value is null
    @Test(expected=RuntimeException.class)
    public void EntryMapSpec2_Null(){
    	map.store(VALID_ARG_1, null, false);
        
    }

    // EntryMap - Spec 2 - Replace value is empty
    @Test
    public void EntryMapSpec2_Empty(){
    	map.store(VALID_ARG_1, "", false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello ", result);
    }
    
    // EntryMap - Spec 2 - Replace value is valid
    // No need to test this, this is identical to Spec 1 w/ valid template

   
    
    
    
    
    // EntryMap Spec 3 - Case sensitive flag is optional and can be null
    // 			In case of null, template matching is case insensitive
    // Check true, false, null
    
    // Template matching case sensitive
    @Test
    public void EntryMapSpec3_True(){
    	map.store("name", VALID_ARG_2, true);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }
    
    // Template matching case insensitive
    @Test
    public void EntryMapSpec3_False(){
    	map.store("NAME", VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }
    
    // Template matching should be case insensitive when null
    @Test
    public void EntryMapSpec3_Null(){
    	map.store("NAME", VALID_ARG_2, null);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }

    
    
    
    
    
    @Test
    public void Test1() {
        map.store("name ${x} z", "Adam", false);
        String result = engine.evaluate("Hello ${name ${x} z}", map,"delete-unmatched");
        assertEquals("Hello Adam", result);
    }

    @Test
    public void Test2() {
        map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    

}