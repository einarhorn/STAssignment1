Group Members:

Einar Horn
s1690094

Ritvik Salim
s1689705

Missed Branches
Template Engine
	1) The equals function in TemplateEngine is dead code. One way to test this is by comparing the Entry objects 
	however since they are private classes, we cannot do that with the given source code. 
	This accounts for 22 of the branches that we could not reach
	
	2.) The hashCode function in TemplateEngine has only partial branch coverage. We would be able to attain full branch coverage
	for this function if we were able to generate a Template object with null attributes for the startIndex, endIndex, or content values
	for a Template object. We were able to do this for the EntryMap class, as we were able to access a Entry object through the public getEntries
	function, however it is not possible to access a Template object in this case.
	THis accounts for 3 branches we could not reach.

	3) The branch found in line 127 of Template engine is always true in our machine, and therefore line 132 is never executed.
	TemplateEngineCoverageTestIntMaxTemplateLength() is a test we developed to make the branch false however our machines 
	run out of memory before we can finish the test.
	This accounts for 1 of the branches that we could not reach
 
 	4) The branch found in line 192 of TemplateEngine is always false.
 	The reason for this is that the template will always be at least one char length less that the length of the instance string, as the
 	template is guaranteed to be a substring of the instanced string.
 	This accounts for 1 of the branches that we could not reach
 	

 	
 	Final Branch Coverage : 67/94 branches covered ~ 71.3%.
 Entry Map
 
 	We were successfully able to cover all branches of this class.
 