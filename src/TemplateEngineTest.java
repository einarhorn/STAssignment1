import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
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

    @Test
    public void Test1() {
        map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        String result = engine.evaluate("Hello ${name} ${surname}", map,"delete-unmatched");
        assertEquals("Hello Adam Dykes", result);
    }

    @Test
    public void Test2() {
        map.store("name", "Adam", false);
        map.store("surname", "Dykes", false);
        map.store("age", "29", false);
        String result = engine.evaluate("Hello ${name}, is your age ${age ${symbol}}", map,"delete-unmatched");
        assertEquals("Hello Adam, is your age 29", result);
    }
    
    @Test
    public void EntryMapSpec4Test1(){ 
    	map.store("name", "Ritvik", true);
    	map.store("name", "Einar", false);
    	String result1 = engine.evaluate("First name is ${NAME}", map, "keep-unmatched");
    	String result2 = engine.evaluate("Second name is ${name}", map, "keep-unmatched");
    	assertEquals("First name is Einar",result1);
    	assertEquals("Second name is Ritvik", result2);
    }
    
    @Test
    public void EntryMapSpec4Test2(){// ASK ABOUT SPEC 4
    	
    }
    
    @Test
    public void EntryMapSpec4Test3(){
    	assertEquals("sda","sdss");
    }
    
    @Test
    public void EntryMapSpec5Test1(){
    	
    }
}