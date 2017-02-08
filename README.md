# stock-alerts

Configure your financial alerts from your favorite stock market and receive notifications by email when their expressions are evaluated as true. "stock-alerts" is your personal technical analyst and adviser using your own rules. 

## Features
* Uses Yahoo Finance API (you can use all his ticker symbols)
* Integrated parser of a "Human Readable" financial expressions
* Processes alerts periodically (scheduled by a cron expression) or on demand
* Sends custom email notifications to a recipient when an alert is triggered
* RESTful API
* Made in JAVA

## Valid expressions examples
* PRICE(GOOGL)>318.5
* PRICE(GOOGL)>318.5&&130000<VOLUME(GOOGL)
* EMA(5,GOOGL)>EMA(20,GOOGL)&&RSI(14,GOOGL)>50
* MACD(12,26,MIRG.BA)<MACD_SIGNAL_LINE(12,26,9,MIRG.BA)

## Formulas
* __EMA__: Exponential Moving Average. Parameters: period and symbol.
* __RSI__: Relative Strength Index. Parameters: period and symbol.
* __PRICE__: Last price of a single stock. Parameter: symbol.
* __VOLUME__: Last volume of a single stock. Parameter: symbol.
* __MACD__: Moving Average Convergence/Divergence. Parameters: fastPeriod, slowPeriod and symbol.
* __MACD_SIGNAL_LINE__: Signal Line of Moving Average Convergence/Divergence. Parameters: fastPeriod, slowPeriod, signalPeriod and symbol.


## Operators
* __&&__: Logical AND operator
* __>__: GREATER THAN operator
* __<__: LESS THAN operator

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
* GET /stock-alerts/formulas/macd?fastPeriod=12&slowPeriod=26&symbol=GOOGL 
  * fastPeriod parameter is optional, default value is 12
  * slowPeriod parameter is optional, default value is 26
* GET /stock-alerts/formulas/macdsignalline?fastPeriod=12&slowPeriod=26&signalPeriod=9&symbol=GOOGL 
  * fastPeriod parameter is optional, default value is 12
  * slowPeriod parameter is optional, default value is 26
  * signalPeriod parameter is optional, default value is 9
* GET /stock-alerts/formulas/price?symbol=GOOGL
* GET /stock-alerts/formulas/volume?symbol=GOOGL
* GET /stock-alerts/alerts
  * Retrieves all active and inactive alerts loaded
* GET /stock-alerts/alerts?symbol=GOOGL
  * Retrieves alerts loaded by symbol
 * GET /stock-alerts/alerts/{alertId}
  * Retrieves a particular alert
* GET /stock-alerts/alerts/{alertId}/activate
  * Activates an alert
* GET /stock-alerts/alerts/{alertId}/deactivate
  * Deactivates an alert
* POST /stock-alerts/alerts
  * Creates a new Alert
  * Passing a JSON representation of an alert as body
* PUT /stock-alerts/alerts 
  * Updates an existing Alert
  * Passing a JSON representation of an alert as body
* DELETE /stock-alerts/alerts/{alertId}
  * Deletes an existing Alert
  * Passing an alert id by URL parameter
* GET /stock-alerts/alerts/{alertId}/process 
  * Processes a particular alerts immediately
* GET /stock-alerts/alerts/process 
  * Processes all active alerts immediately

## Alert object structure
* __id__ = An alert identifier. For example: GOOGLE1
* __active__ = true or false. Represents whether this alert is currently vigent or not.
* __description__ = Long description for this alert. This field will be the content of the email notification. For example: "The price of GOOGLE is optimum to buy right now!"
* __expression__ = Financial expression to be evaluated when this alert will processed
* __name__ = Short description of the alert. This field will be the subject of the email notification. For example: "GOOGLE buy signal"
* __sendEmail__ = true or false. If you want or not to receive notification by email about this alert.
* __symbol__ = A ticker symbol that the alert is related to.
* __opposedAlertId__ = Id of an opposed alert. For example: An opposed alert of a buy signal is a sell signal.

### JSON Example
```
{
   "id": "franBuy",
   "active": true,
   "description": "BBVA Frances buy signal",
   "expression": "EMA(14,FRAN.BA)>95",
   "name": "Buy Banco Francés as soon as possible",
   "sendEmail": true,
   "symbol": "FRAN.BA",
   "opposedAlertId": "franSell"
}
```
