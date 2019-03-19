@SmokeTest @F-SmokeTestTag @SmokeTestTag2 @SmokeTestTag3
Feature: Loading different web sites
#------------------------------------------------------------
@S-Websites-1 @SmokeTestTag4 @SmokeTestTag5
Scenario: Loading Google home page

	When User is on Google Home Page
		And System fetches all the links from the page
		Then User navigates to "walgreens" site
#-------------------------------------------------------------
@S-Websites-2 @SmokeTestTags
Scenario: Loading Walgreens home page

	When User is on Walgreens Home Page
		And System fetches all the links from the page
#-------------------------------------------------------------
@S-Websites-3 @SmokeTestTags
Scenario: Loading Facebook home page

	When User is on Facebook Home Page
		And System fetches all the links from the page
#-------------------------------------------------------------
@S-Websites-4 @SmokeTestTags
Scenario: Loading Toolsqa home page

	When User is on Toolsqa Home Page
		And System fetches all the links from the page
#-------------------------------------------------------------
@S-Websites-5
Scenario: Loading Maven home page

	When User is on Maven Home Page
		And System fetches all the links from the page
#-------------------------------------------------------------
@SO-Websites-6
Scenario Outline: Loading Google home page

	When User is on Google Home Page
		And System fetches all the links from the page
		Then User navigates to "<sites>" site
		
Examples:
| sites				|
| www.walgreens.com |
| www.facebook.com  |
| www.toolsqa.com   |

#-------------------------------------------------------------

