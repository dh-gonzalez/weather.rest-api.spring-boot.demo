Feature: Test the current weather endpoint

Background:
* url 'http://localhost:8080/api/v1/weather'

Scenario: Request current weather for Toulouse
Given path 'current'
And param city = 'toulouse'
When method get
Then status 200
And match $ == {city:'toulouse',date:'#regex ^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$',description:'#string',temperatureInCelsius:'#number',windInKmPerHour:'#number? _ >= 0',relativeHumidityInPercentage:'#number? _ >= 0 && _ <= 100'}

Scenario: Request current weather for a non existing city
Given path 'current'
And param city = 'nonexistingcity'
When method get
Then status 500
And match $ == {exception:'org.springframework.web.reactive.function.client.WebClientException',message:'400 Bad Request from GET http://api.weatherbit.io/v2.0/current'}
