package com.emrekisa.roket.cucumber.stepdefs;

import com.emrekisa.roket.RoketApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = RoketApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
