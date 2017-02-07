import static org.junit.Assert.assertEquals;
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

}