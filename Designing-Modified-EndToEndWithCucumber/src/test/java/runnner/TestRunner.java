package runnner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		 features = "C:\\Users\\hp\\eclipse-workspaceAPI_DesigningModified\\Designing-Modified-EndToEndWithCucumber\\src\\test\\resources\\EndToEndScenario.feature"
		 ,glue = {"stepDefinition"}  	
		 ,plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		 ,monochrome = true
				 ,publish = true
				 )
public class TestRunner {

}
