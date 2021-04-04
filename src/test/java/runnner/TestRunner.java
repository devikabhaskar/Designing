package runnner;

import org.junit.runner.RunWith;
import io.cucumber.core.api.*;
import io.cucumber.core.options.*;

@RunWith(Cucumber.class)
@io.cucumber.junit.CucumberOptions(
		features = "/src/test/resources"
		glue = {"stepDefinition"}
		)

public class TestRunner {

}
