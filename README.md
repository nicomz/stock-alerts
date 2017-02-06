# stock-alerts

Configure your financial alerts from your favorite stock market and receive notifications by email when their expressions are evaluated as true.

## Features
* Uses Yahoo Finance API (you can use all his ticker symbols)
* Integrated parser of a "Human Readable" financial expressions
* Processes alerts periodically (scheduled by a cron expression) or on demand
* Sends custom email notifications to a recipient when an alert is triggered
* RESTful API
* Made in JAVA

## Valid expressions examples
* PRICE(GOOGL)>318.5 
* EMA(5,GOOGL)>EMA(20,GOOGL)&&RSI(14,GOOGL)>50

## Formulas
* __EMA__: Exponential Moving Average. Parameters: period and symbol.
* __RSI__: Relative Strength Index. Parameters: period and symbol.
* __PRICE__: Last price of a single stock. Parameter: symbol.

## Operators
* __&&__: Logical AND operator
* __>__: GREATER THAN operator

## API REST
* GET /stock-alerts/ping 
  * Useful to test server is up
* GET /stock-alerts/emails/test
  * Sends a test email, useful to test email configuration
* GET /stock-alerts/stocks?symbol=GOOGL
  * Returns stock information
* GET /stock-alerts/stocks/history?symbol=GOOGL
  * Returns historical stock information
* GET /stock-alerts/formulas/ema?period=14&symbol=GOOGL
* GET /stock-alerts/formulas/rsi?period=14&symbol=GOOGL 
  * period parameter is optional, default value is 14
* GET /stock-alerts/formulas/price?symbol=GOOGL
* GET /stock-alerts/alerts 
  * Retrieves all active and inactive alerts loaded
* POST /stock-alerts/alerts 
  * Creates a new Alert
  * Passing a JSON representation of an alert as body
* PUT /stock-alerts/alerts 
  * Updates an existing Alert
  * Passing a JSON representation of an alert as body
* DELETE /stock-alerts/alerts?id=franBuy1 
  * Deletes an existing Alert
  * Passing an alert id by URL parameter
* GET /stock-alerts/alerts/process 
  * Processes all active alerts immediately

## Alert object structure
* __id__ = An alert identifier. For example: GOOGLE1
* __activ__ = true or false. Represents whether this alert is currently vigent or not.
* __description__ = Long description for this alert. This field will be the content of the email notification. For example: "The price of GOOGLE is optimum to buy right now!"
* __expression__ = Financial expression to be evaluated when this alert will processed
* __name__ = Short description of the alert. This field will be the subject of the email notification. For example: "GOOGLE buy signal"
* __sendEmail__ = true or false. If you want or not to receive notification by email about this alert.

### JSON Example
```
{
   "id": "franBuy1",
   “active": true,
   "description": "BBVA Frances buy signal",
   "expression": "EMA(14,FRAN.BA)>95",
   "name": "Buy Banco Francés as soon as possible",
   "sendEmail": true
}
```
