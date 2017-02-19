Group Members:

Einar Horn
s1690094

Ritvik Salim
s1689705





EntryMap Class

Spec 3 - Case sensitive flag is optional and can be null.
	However, when null is passed as the case sensitive parameter, the program 
	terminates with a NullPointerException.




TemplateEngine Class

Spec 3 - Spec first says "Matching mode cannot be NULL ", yet
	the next sentence contradicts this, stating: " If matching 
	mode NULL or other value...". Therefore, it is unclear whether
	the matching mode is allowed to be null or not. When the matching mode
	is tested with a null value, this produces a NullPointerException.
	
	
No other issues were found with the implementation of the specifications, based
off of our functional tests.