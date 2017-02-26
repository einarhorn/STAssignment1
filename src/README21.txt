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

	2) The branch found in line 127 of Template engine is always true in our machine. 
	TemplateEngineCoverageTestIntMaxTemplateLength() is a test we developed to make the branch false however our machines 
	run out of memory before we can finish the test.
	This accounts for 1 of the branches that we could not reach
 
 	3) The brand found in line 192 of TemplateEngine is always false.
 	The reason for this is that the template will always be at least one char length less that the length of the instance string
 	This accounts for 1 of the branches that we could not reach
 	
 	Final Branch Coverage : 67/94 branches covered ~ 71.3%.
 Entry Map
 