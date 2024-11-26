Feature: Test the forecast weather endpoint

Background:
* url 'http://localhost:8080/api/v1/weather'

Scenario: Request current weather for Toulouse
Given path 'forecast'
And param city = 'toulouse'
When method get
Then status 200
And match $ == {city:'toulouse',date:'#regex ^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$',generalTendency:'#? ["Improvement", "Stable", "Deterioration"].includes(_)',temperaturesTendency:'#? ["Increasing", "Stable", "Decreasing"].includes(_)',barometricPressuresTendency:'#? ["Significant increase", "Increasing", "Stable", "Decreasing", "Significant decrease"].includes(_)',averageWindSpeedsInBeaufortScale:'#number? _ >= 0 && _ <= 12'}

Scenario: Request current weather for a non existing city
Given path 'forecast'
And param city = 'nonexistingcity'
When method get
Then status 500
And match $ == {exception:'weather.rest_api.spring_boot.demo.exception.InconsistentRawDataException',message:'No data'}
