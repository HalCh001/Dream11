#Author: chiranjit.halder@gmail.com

	Feature: Get real time Dream11 for Ongoing IPL Match

  Scenario: Fetch & update real time Player credits from Dream11.com
  Given Open browser
  When Get Player Credits
  Then Update excel with Player Credits to corresponding Team Sheet

