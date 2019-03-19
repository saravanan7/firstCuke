@CoreTest
Feature: Loading different web sites

#-------------------------------------------------------------
@SO-CoreTest-1
Scenario Outline: Loading Google home page

	When User is on Google Home Page
		And System fetches all the links from the page
		Then User navigates to "<sites>" site

@SIT		
Examples:
| sites				|
| www.facebook.com  |

@E2E
Examples:
| sites				|
| www.toolsqa.com   |

#-------------------------------------------------------------

