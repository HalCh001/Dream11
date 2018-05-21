package cucumberSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps 
{
	@Given("^Open browser$")
	public void OpenBrowser()
	{
		System.out.println("Browser Launched");
	}
	
	@When("^Get Player Credits$")
	public void GetCredits()
	{
		System.out.println("Got Player Credits");
	}	
	
	@When("^Get Player Points$")
	public void getPoints()
	{
		System.out.println("Got Player Points");
	}
	
	
	
	@Then("^Update excel with Player Credits to corresponding Team Sheet$")
	public void UpdateCredits()
	{
		System.out.println("Credits Updated");
	}
	
	@Then("^Update excel with Player Points to corresponding Team Sheet$")
	public void UpdatePoints()
	{
		System.out.println("Points Updated");		
	}
	
	
	@Then("^Mail Dream11$")
	public Boolean Mail()
	{
		System.out.println("Mail Sent");
		return true;
		
	}

}
