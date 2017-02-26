import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class Task1 {

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
    
    
    /**
     *  EntryMap Tests
     */
    
    /**
     *  EntryMap Spec 1 - Template cannot be null or empty. Valid template should pass.
     */
    
    // Spec 1 - Template is null
    @Test(expected=RuntimeException.class)
    public void EntryMapSpec1_NullTemplate(){
    	map.store(null, VALID_ARG_2, false);
        	
    }
    
    // Spec 1 - Template is empty string
    @Test(expected=RuntimeException.class)
    public void EntryMapSpec1_EmptyTemplate(){
    	map.store("", VALID_ARG_2, false);
        	
    }
    
    // Spec 1 - Template is valid
    @Test
    public void EntryMapSpec1_ValidTemplate(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    
    // Spec 1 - Template is composed of multiple words
    @Test
    public void EntryMapSpec1_ValidTemplateWithMultipleWords(){
    	map.store(VALID_ARG_1, "james james james", false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello james james james", result);
    }
    
    
    /**
     *  EntryMap Spec 2 - Replace value cannot be null. Empty or valid should pass.
     */
    
    // Spec 2 - Replace value is null
    @Test(expected=RuntimeException.class)
    public void EntryMapSpec2_NullReplace(){
    	map.store(VALID_ARG_1, null, false);
        
    }

    // Spec 2 - Replace value is empty
    @Test
    public void EntryMapSpec2_EmptyReplace(){
    	map.store(VALID_ARG_1, "", false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello ", result);
    }
    
    // Spec 2 - Replace value is valid
    @Test
    public void EntryMapSpec2_ValidReplace(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    

    
    /**
     *  EntryMap Spec 3 - Case sensitive flag is optional and can be null
     * 	In case of null, template matching is case insensitive
     *  Check true, false, null
     */
    
    
    // Spec 3 - Template matching case sensitive
    @Test
    public void EntryMapSpec3_TrueCaseSensitive(){
    	map.store("name", VALID_ARG_2, true);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }
    
    // Spec 3 - Template matching case insensitive
    @Test
    public void EntryMapSpec3_FalseCaseSensitive(){
    	map.store("NAME", VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }
    
    // Spec 3 - Template matching should be case insensitive when null
    @Test
    public void EntryMapSpec3_NullCaseSensitive(){
    	map.store("NAME", VALID_ARG_2, null);
    	map.store("NAME1", VALID_ARG_1, null);
    	map.store("NAME2", VALID_ARG_2, null);
    	map.store("NAME2", VALID_ARG_1, null);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }

    /**
     * EntryMap Spec 4 - Entry map should store template inputs in the order they were added
     */
    
    // Spec 4 - Templates stored in the map should be as they were inputed. 
    @Test
    public void EntryMapSpec4_AlphabeticalOrder(){ 
    	map.store("name", "Einar", true);
    	map.store("name", "Ritvik", false);
    	String result1 = engine.evaluate("Second name is ${NAME}", map, "keep-unmatched");
    	String result2 = engine.evaluate("First name is ${name}", map, "keep-unmatched");
    	assertEquals("Second name is Ritvik",result1);
    	assertEquals("First name is Einar", result2);
    }
    
    // Spec 4 - Template order should remain in the input order and not default into a alphabetical order
    @Test
    public void EntryMapSpec4_ReverseAlphabeticalOrder(){
    	map.store("name", "Romeo", false);
    	map.store("name", "Juliet", false);
    	String result1 = engine.evaluate("First name is ${name}", map, "keep-unmatched");
    	assertEquals("First name is Romeo",result1);
    }
    
    
    
    /**
     *  EntryMap Spec 5 - EntryMap should contain only unique entries. 
     *  We assumed entries are unique if all 3 parameters are exactly identical
     */
    
    // Spec 5 - Map should contain only one copy of identical entries
    @Test
    public void EntryMapSpec5_RejectDuplivateEntries(){ 

    	map.store("name", "adam", false);
    	map.store("name", "adam", false);
    	int size = map.getEntries().size();
    	assertEquals(1,size);
    	
    }
    
    // Spec 5 - Map should contain both entries as they are unique entries due to the case sensitivity boolean
    @Test
    public void EntryMapSpec5_DifferentEntryByCaseSensitivity() {
    	map.store("name", "bernie", true);
    	map.store("name", "bernie", false);
    	String result1 = engine.evaluate("FEEL THE BERN!, ${NAME}", map, "keep-unmatched");
    	String result2 = engine.evaluate("feel the bern, ${name}",map, "keep-unmatched");
    	assertEquals("FEEL THE BERN!, bernie",result1);
    	assertEquals("feel the bern, bernie",result2);
    }
    
    // Spec 5 - Map should contain both values evern thought the keys are identical. Different due to value and boolean
    @Test
    public void EntryMapSpec5_DifferentEntryByValueAndCaseSensitivity() 
    {
    	map.store("name", "Donald", true);
    	map.store("name", "trump", false);
    	String result1 = engine.evaluate("${name} Duck is so funny!",map,"keep-unmatched");
    	String resutl2 = engine.evaluate("I still have a ${NAME} card up my sleeve!", map, "keep-unmatched");
    	assertEquals("Donald Duck is so funny!",result1);
    	assertEquals("I still have a trump card up my sleeve!",resutl2);
    }
    
    
    // Spec 5 - Map should contain both entries as the entries are different due to different keys
    @Test
    public void EntryMapSpec5_DifferentEntryByKey() 
    {
    	map.store("firstName", "Hilary", false);
    	map.store("firstname", "Hilary", false);
    	int size = map.getEntries().size();
    	assertEquals(2,size);
    }
    
    
    
    
    /**
     * 
     * TemplateEngine Specifications
     * 
     */
    
    /**
     *  TemplateEngine Spec 1 - Template can be null or empty. If null or empty, evaluate returns unchanged template string.
     */
    
    // Spec 1 - Template is null
    @Test
    public void TemplateEngineSpec1_NullTemplate(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate(null, map,"delete-unmatched");
    	assertEquals(null, result);
        	
    }
    
    // Spec 1 - Template is empty string
    @Test
    public void TemplateEngineSpec1_EmptyTemplate(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("", map,"delete-unmatched");
    	assertEquals("", result);
        	
    }
    
    // Spec 1 - Template is valid
    @Test
    public void TemplateEngineSpec1_ValidTemplate(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    

    
    
    
    /**
     *  TemplateEngine - Spec 2 - EntryMap can be null. If EntryMap is null, unchanged template string can be returned
     */
    
    // Spec 2 - EntryMap is null
    @Test
    public void TemplateEngineSpec2_NullEntryMap(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", null,"delete-unmatched");
    	assertEquals("Hello ${name}", result);
        	
    }
    
    // Spec 2 - EntryMap is valid
    @Test
    public void TemplateEngineSpec2_ValidEntryMap(){
    	map.store(VALID_ARG_1, VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map, "delete-unmatched");
    	assertEquals("Hello Adam", result);
    }
    
    
    
    
    
    /**
     *  TemplateEngine - Spec 3 - Matching mode - Null or other value defaults to "delete-unmatched"
     *  AMBIGUOUS SPECIFICATION - Spec says matching mode both allowed and not allowed to be null.
     */
    
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
    
    // Spec 3 - Matching mode is valid string: "delete-unmatched"
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
    
    /**
     *  TemplateEngine - Spec 4 - Everything between "${" and "}" are counted as templates
     */
    
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
    
    
    // Spec 4 - Valid templates composed of sub templates
    // Note the "keep-unmatched" setting used here
    @Test
    public void TemplateEngineSpec4_NestedTemplateStrings(){
    	map.store("name ${names}", "James", false);
    	map.store("car ${cars}", "bmw", false);
    	map.store("age ${ages}", "old", false);
    	
    	String result = engine.evaluate("That's ${name ${names}}, he's ${age ${ages}} and drives an ugly ${car ${cars}}", map, "keep-unmatched");
    	assertEquals("That's James, he's old and drives an ugly bmw", result);
    }
    
    
    
    /**
     *  TemmplateEngine Spec 5 - No visible characters should not affect the matching of templates
     */
    
    // Spec 5 - Matching should not be affected by space characters
    @Test
    public void TemplateEngineSpec5_TemplateWithSpaceCaseInsensitive()    {
    	map.store("first name", "Jackie", false);
    	String result = engine.evaluate("first name is ${firstname}",map, "keep-unmatched");
    	assertEquals("first name is Jackie", result);
    }
    
    // Spec 5 - Matching should not be affected by tab characters
    @Test
    public void TemplateEngineSpec5_TemplateWithTabCaseInsensitive() {
    	map.store("first"+'\t'+"name", "Jackie", false);
    	String result = engine.evaluate("first name is ${firstname}", map, "keep-unmatched");
    	assertEquals("first name is Jackie", result);	
    }
    
    // Spec 5 - Matching should not be affected by new line characters
    @Test
    public void TemplateEngineSpec5Test3_TemplateWithNewLineCaseInsensitive(){
    	map.store("first"+'\n'+"name", "Jackie", false);
    	String result = engine.evaluate("first name is ${firstname}", map, "keep-unmatched");
    	assertEquals("first name is Jackie", result);	
    }
    
    // Spec 5 - Matching should not be affected by space characters when case sensitive
    @Test
    public void TemplateEngineSpec5Test_TemplateWithSpaceCaseSensitve()    {
    	map.store("First Name", "Chan", true);
    	String result = engine.evaluate("first name is ${FirstName}", map, "keep-unmatched");
    	assertEquals("first name is Chan", result);	
    }
    
    // Spec 5 - Matching should not be affected by tab characters when case sensitive
    @Test
    public void TemplateEngineSpec5_TemplateWithTabCaseSensitve() {
    	map.store("First"+'\t'+"Name", "Chan", true);
    	String result = engine.evaluate("first name is ${FirstName}", map, "keep-unmatched");
    	assertEquals("first name is Chan", result);	
    }
     
    // Spec 5 - Matching should not be affected by tab characters when case sensitive
    @Test
    public void TemplateEngineSpec5_TemplateWithNewLineCaseSensitve(){
    	map.store("First"+'\n'+"Name", "Chan", true);
    	String result = engine.evaluate("first name is ${FirstName}", map, "keep-unmatched");
    	assertEquals("first name is Chan", result);	
    }
    
    
    /**
     * Template Engine Spec 6 - The open and end braces for templates must properly match each other
     */
    
    // Spec 6 - Input String contains more open braces than closed braces
    @Test
    public void TemplateEngineSpec6_MoreOpenBraces(){
    	map.store("date", "24th", false);
    	map.store("month", "January", false);
    	String result = engine.evaluate("Today's ${date is ${date} of ${month}", map, "keep-unmatched");
    	assertEquals("Today's ${date is 24th of January",result);
    }
    
    // Spec 6 - Input String contains more closed braces than open braces
    @Test
    public void TemplateEngineSpec6_MoreClosedBraces(){
    	map.store("date", "24th", false);
    	map.store("month", "January", false);
    	String result = engine.evaluate("Today's date is ${date} of ${month} }", map, "keep-unmatched");
    	assertEquals("Today's date is 24th of January }",result);
    }

    // Spec 6 - Input String contains one pair of braces in reverse order
    @Test
    public void TemplateEngineSpec6_ReverseOrder(){
    	map.store("date", "24th", false);
    	map.store("month", "January", false);
    	String result = engine.evaluate("Today's date }is ${date} of ${month} ${", map, "keep-unmatched");
    	assertEquals("Today's date }is 24th of January ${",result);
    }
    
    // Spec 6 - Input String contains many nested templates and has one extra close braces
    @Test
    public void TemplateEngineSpec6Test_ComplexString(){
    	map.store("date", "10th", false);
    	map.store("month", "January", false);
    	map.store("10th of January", "Febuary",false);
    	map.store("${ This is due 10th of Febuary }", "It Works", false);
    	String result = engine.evaluate("${${ This is due ${date} of ${ ${date} of ${month} } }}}", map, "keep-unmatched");
    	assertEquals("It Works}",result);
    }
    
    
    /** 
     * TemplateEngine Spec 7 - Templates are ordered according the their length
     */
    
    // Spec 7 - Testing the ordering by length
    // Note the "keep-unmatched" setting used here
    @Test
    public void TemplateEngineSpec7_LengthTest(){
    	map.store("schoolies ${a}", "this should never appear", false);
    	map.store("carcar ${bb}", "neither should this", false);
    	map.store("ccc ${ages}", "nor this", false);
    	map.store("a", "zazzle", false);
    	map.store("bb", "whammy", false);
    	map.store("ccc", "vrooom", false);
    	map.store("schoolies zazzle", "boom", false);
    	map.store("ages", "moot moot", false);
    	
    	String result = engine.evaluate("That's ${schoolies ${a}}, it's ${carcar ${bb}} and drives an ugly ${ccc ${ages}}", map, "keep-unmatched");
    	
    	/**
    	 *  Order should be:
    	 *  1.) ${a}
    	 *  2.) ${bb}
    	 *  3.) ${ccc}
    	 *  4.) ${ages}
    	 *  5.) ${carcar}
    	 *  6.) ${schoolies}
    	 *  7.) ${ccc ${ages}}
    	 *  8.) ${carcar ${bb}}
    	 *  9.) ${schoolies ${a}}
    	 *  
    	 *  
    	 *  
    	 *  Map contains:
    	 *  1.) ($schoolies $a}, blurghh)
    	 *  2.) (carcar ${bb}, rawrr)
    	 *  3.) (ccc ${ages}, doot doot)
    	 *  2.) (a, zazzle)
    	 *  2.) (bb, whammy)
    	 *  3.) (ccc, vrooom)
    	 *  4.) (schoolies zazzle, boom)
    	 */
    	
    	assertEquals("That's boom, it's ${carcar whammy} and drives an ugly ${ccc moot moot}", result);
    }
    
    // Spec 7 - Testing the ordering when template lengths are equal. Equal length templates should be ordered left-right.

    @Test
    public void TemplateEngineSpec7_EqualLengthTest(){
    	
    	map.store("name", "Einar", true);
    	map.store("name", "Ritvik", false);
    	String result1 = engine.evaluate("Second ${name} is ${NAME}", map, "keep-unmatched");
    	String result2 = engine.evaluate("Second ${NAME} is ${name}", map, "keep-unmatched");
    	
    	
    	assertEquals("Second Einar is Ritvik", result1);
    	assertEquals("Second Ritvik is Einar", result2);
    }
    
    
    /**
     *  TemplateEngine Spec 8 - The template engine should appropriately replace templates if matches are found
     *  If matches are not found, they should keep the templates if last parameter is "keep-unmatched" else 
     *  it should delete templates from the string if the last template is "delete-unmatched" 
     */
    
    // Spec 8 - Template engine should correctly replace template in string when matched
    @Test
    public void TemplateEngineSpec8Test1ReplaceTemplate(){
    	map.store("name", "James", false);
    	String result = engine.evaluate("The name is ${name}.", map, "keep-unmatched");
    	assertEquals("The name is James.", result);	
    }
    
    // Spec 8 - Template engine should correctly replace ALL templates in string if multiple matches
    @Test
    public void TemplateEngineSpec8Test2ReplaceMultipleTemplate(){
    	map.store("fName", "James", false);
    	map.store("lName", "Bond", false);
    	String result = engine.evaluate("The name is ${fName}. ${fName} ${lName}", map, "keep-unmatched");
    	assertEquals("The name is James. James Bond", result);	
    }
    
    // Spec 8 - Template engine should correctly keep unmatched templates if last parameter is 'keep-unmatched'
    @Test
    public void TemplateEngineSpec8Test3NoMatchKeepUnMatched(){
    	String result = engine.evaluate("I would like a ${liqour} martini. Shaken, not ${mixing method}", map, "keep-unmatched");
    	assertEquals("I would like a ${liqour} martini. Shaken, not ${mixing method}", result);	
    } 
    
    // Spec 8 - Template engine should correctly remove unmatched templates if last parameter is 'delete-unmatched"
    @Test
    public void TemplateEngineSpec8Test4NoMatchDeleteUnmatched(){
    	map.store("temp", "nothing", false);
    	String result = engine.evaluate("I ran out ${of ideas}", map, "delete-unmatched");
    	assertEquals("I ran out ",result);
    }
    
    
    
    // Additional tests for coverage
    @Test
    public void TemplateEngineCoverageTestUnderSequanceFalse(){
    	map.store("patter", "value", false);
    	String result = engine.evaluate("{pattern}",map,"delete-unmatched");
    	assertEquals("{pattern}",result);
    }
    @Test
    public void EntryMapCoverageTestEqualsSelfReferenceCheck(){

    	map.store("test", "nothing", false);
    	Boolean identicalElements = map.getEntries().get(0).equals(map.getEntries().get(0));
    	
    	String result = engine.evaluate("zzz ${test}", map, "delete-unmatched");
    	assertEquals("zzz nothing",result);
    }
    
    @Test
    public void EntryMapCoverageTestEqualsNullOrOtherClassCheck(){
    	map.store("test", "nothing", false);

    	// (T|F)
    	Boolean identicalElements = map.getEntries().get(0).equals(null);

    	
    	// (F|T)
    	Boolean testBoolean = false;
    	Boolean identicalElements2 = map.getEntries().get(0).equals(testBoolean);
    	
    	String result = engine.evaluate("zzz ${test}", map, "delete-unmatched");
    	assertEquals("zzz nothing",result);
    }
    
    @Test
    public void EntryMapCoverageTestEqualsDifferentPattern(){
    	map.store("test", "nothing", false);
    	map.store("test2", "nothing2", false);
    	
    	map.getEntries().get(0).equals(map.getEntries().get(1));

    	
    	String result = engine.evaluate("zzz ${test}", map, "delete-unmatched");
    	assertEquals("zzz nothing",result);
    }
    
    @Test
    public void EntryMapCoverageTestEqualsDifferentValue(){
    	map.store("test", "nothing", false);
    	map.store("test", "nothing2", false);

    	map.getEntries().get(0).equals(map.getEntries().get(1));
    	
    	String result = engine.evaluate("zzz ${test}", map, "delete-unmatched");
    	assertEquals("zzz nothing",result);
    }
    
    @Test
    public void EntryMapCoverageTestHashCodeNullCaseSensitivity(){
    	
    	
    	map.store("test", "nothing", false);
    	map.store("test", "nothing2", false);

    	
    	EntryMap.Entry testEntry = map.getEntries().get(0);
    	testEntry.caseSensitive = null;
    	
    	testEntry.hashCode();
    }
    
    @Test
    public void EntryMapCoverageTestEqualsBothNullCaseSensitivity(){
    	
    	
    	map.store("test", "nothing", false);
    	map.store("test", "nothing", true);

    	
    	EntryMap.Entry testEntry = map.getEntries().get(0);
    	testEntry.caseSensitive = null;
    	
    	EntryMap.Entry testEntry2 = map.getEntries().get(1);
    	testEntry2.caseSensitive = null;
    	
    	testEntry2.equals(testEntry);
    	
    }
    
    
    
    @Test
    public void EntryMapCoverageTestEqualsOneNullCaseSensitivity(){
    	map.store("test", "nothing", false);
    	map.store("test", "nothing", true);
    	EntryMap.Entry testEntry = map.getEntries().get(0);
    	testEntry.caseSensitive = null;
    	testEntry.equals(map.getEntries().get(1));
    }
    
    @Test
    public void EntryMapCoverageTestEmptyTemplate(){
    	
    	
    	map.store("test", "nothing", false);
    	String result = engine.evaluate("${}", map, "delete-unmatched");
    	assertEquals(result,"");
    }
    
    @Test
    public void TemplateEngineCoverageTestIntMaxTemplateLength(){
    	
    	// Should generate a string of length 2^31 - 1, but runs out of memory on my machine :( 
    	StringBuilder s = new StringBuilder("s");
    	for (int i = 0; i < 31; i++){
    		if (i == 30){
    			s.append(s.substring(0, s.length() - 1 - 1));
    		} else {
    			s.append(s.toString());
    		}
    		System.out.println(s.length());
    		
    	}
    	
    	map.store("NAME", "Ritvik", false);
    	String result1 = engine.evaluate("Second ${" + s + "} is ${NAME}", map, "delete-unmatched");
    	
    	
    	assertEquals("Second  is Ritvik", result1);
    	
    }
}