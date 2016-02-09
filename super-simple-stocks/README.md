# Super Simple Stocks #

Super simple stocks is a technical assessment for a hiring process.

## Index

* [Problem Statement](#problem-statement)
  * [Requirements](#requirements)
  * [Constraints and Notes](#constraints-and-notes)
  * [Sample data from the Global Beverage Corporation Exchange](#sample-data-from-the-global-beverage-corporation-exchange)
  * [Formulas](#formulas)
* [Solution](#solution)
  * [Technical Stack](#technical-stack)
  * [Architecture](#architecture)
  * [Tests](#test)
  * [Difficulties](#difficulties)
  * [How to run](#how-to-run)
* [Stock Concepts](#stock-concepts)
  * [Capitalization-weighted Index](#capitalization-weighted-index)
  * [Stock types](#stock-types)
  * [Stock prices](#stock-prices)
  * [Dividends](#dividends)
  * [Price-Earnings Ratio](#price-earnings-ratio)
  * [Stocks vs Shares](#stocks-vs-shares)

## Problem Statement ##

### Requirements ###

Provide working source code that will:
  
  a. For a given stock:

    i.   Calculate the dividend yield.
    ii.  Calculate the P/E Ratio.
    iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and price.
    iv.  Calculate Stock Price based on trades recorded in past 15 minutes.

  b. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

### Constraints and Notes ###

  1. Written in one of these languages:
   
    * Java, C#, C++, Python.

  2. No database or GUI is required, all data need only be held in memory.

  3. Formulas and data provided are simplified representations for the purpose of this exercise.

### Sample data from the Global Beverage Corporation Exchange ###

Stock Symbol | Type      | Last Dividend | Fixed Dividend | Par Value
:------------|:----------|--------------:|---------------:|-----------:
TEA          | Common    |  0            |                | 100           
POP          | Common    |  8            |                | 100
ALE          | Common    | 23            |                |  60
GIN          | Preferred |  8            | 2%             | 100
JOE          | Common    | 13            |                | 250

### Formulas ###

               | Common                                                             | Preferred
:--------------|:------------------------------------------------------------------:|:--------------------------------------------------------------------------:
Dividend yield | ![Common Dividend Yield](/images/readme/common-dividend-yield.png) | ![Preferred Dividend Yield](/images/readme/preferred-dividend-yield.png)
P/E Ratio      | ![P/E ratio](/images/readme/p-e-ratio.png)                         | ![P/E ratio](/images/readme/p-e-ratio.png)
Geometric Mean | ![Geometric Mean](/images/readme/geometric-mean.png)               | ![Geometric Mean](/images/readme/geometric-mean.png)
Stock Price    | ![Stock price](/images/readme/stock-price.png)                     | ![Stock price](/images/readme/stock-price.png)

- - -

## Solution ##

The problem statement doesn't mention what is expected as the deliverable, therefore a module that can be later integrated with other code was the best solution.
A module with a service layer for each of the requested tasks is the core of this library. It can be packaged into a jar and used out of the box. 

There is only one service component defined, the `MarketExchangeService` with a default implementation `SimpleMarketExchangeService` with methods to retrieve stocks, calculate 
dividends and price earning ratios as well as stock prices. In addition to stock math, the service also provides methods to buy and sell shares of a stock as well  as calculate 
the Global Beverage Corporation Exchange.

`SimpleMarketExchangeService` has two dependencies, a `StockRepository` and a `TradeRepository` enabling the service to perform CRUD operations on Stocks and Trades.

The solution for CRUD operations uses a fast in-memory database, i.e., the database exists only during the program execution. Even thought the problem statement
clearly states that no database is required, the use of an in-memory database allows easy object retrieval, save and search capabilities and can very well be changed 
to another technology such as a caching for instance (e.g. Apache Geode or Pivotal GemFire).

The model/domain contains two entities, Stock and Trade.

A stock contains the symbol, type of stock (common and preferred), last dividend, fixed dividend, par value and price.

A trade contains a timestamp, quantity of shares on the trade, type of trade (buy and sell), the stock, and price at which the shares were transactioned.
 
### Technical Stack ###

To implement this solution, the following libraries / tools were used:

  * Maven -- Used for dependency management and as a build tool.
  * Spring:
    * Spring Core -- Used for Dependency Injection.
    * Spring Data -- Used for obtaining data and storing data.
  * H2 -- A very fast open source database used as an in-memory data store. 
  * jUnit -- unit test framework.
  * Mockito -- Mock framework to mock dependencies when unit testing.
  * Git -- Version control system.

### Tests ###

The following tests have been implemented:

   * Retrieve available stocks
    
   * Retrieve trades of a given stock
    
   * Calculate dividend yield of a common stock
   
   * Calculate dividend yield of a preferred stock
   
   * Calculate price earnings ratio of a common stock
   
   * Calculate price earnings ratio of a preferred stock
      
   * Record of a purchase transaction.
   
   * Record of a sale transaction.
   
   * Calculate the stock price of a stock without trades.

   * Calculate the stock price of a stock with trades older than 15 minutes.

   * Calculate the stock price of a stock with trades in the last 15 minutes.
 
   * Calculate the GBCE index with two stocks available.

### Difficulties ###

There were some difficulties throughout this challenge. In this section a brief explanation of the difficulties is given and how they were tackled.

* Stock price calculation

The provided formula to calculate the stock price takes only into account the trades of that stock in the last 15 minutes. 
This doesn't cover all cases, what if a stock has no trades in the last 15 minutes? what is the price of a stock in the first trade?

In the real world a company is first evaluated, it releases a percentage of it's company as shares into the market and the price of 
each stock is the value of the company times the percentage of the company in stocks over the number of stocks. For instance, the company "Olympic Evergreen" 
wants to release 40% of it's company to the market and has been evaluated at 1000 dollars. The company wishes to make available 100 stocks.
This means that the price of each stock will be 1000*40%/100 = 4 pounds.

For this reason each stock has an attribute `price` that when added to the available stocks, it will have an initial price allowing the first trade to occur 
and the provided formula to kick in afterwards.

* Quantity of stocks

I don't know if this was intended or not. In order for the stock price to vary according to supply and demand, there was the need to keep track of all total stocks.
Since this module didn't require a database, either the data would've have to be fetched from another service or loaded at startup. Since the problem wasn't clear on 
that point, this solution is not aware on how many stocks exist on the market. It does know how many stocks were traded over time since its uptime and so the formula
used only those values. 

This causes the formula to be reduced to the initial stock price. Take for instance a stock with an initial price of 10 pounds and 3 transactions in the same minute of 50, 100 and 160 shares.
The price of each stock for the first trade would then be 10 pounds.
The price of each stock for the second trade would then be 10*50/50 = 10 pounds.
The price of each stock for the third trade would then be (10*50+10*100)/(50+100) = 10 pounds.

This problem was left as is and the provided formula is used.

### How to run ###

Since this project uses maven, to run the demo, execute the tests and package the module into a jar some simple maven commands are only what it takes.

The minimum requirements for this project are a JDK 1.8 and Maven 3.3, afterwards maven will download all required dependencies. 
 
#### Running the demo ####

```
mvn spring-boot:run
```

#### Executing the tests ####

```
mvn test
```

#### Package module ####

````
mvn package
````

- - -

## Stock Concepts ##

### Capitalization-weighted Index ###

A capitalization-weighted index, also called a market value weighted index is a stock market index whose components are weighted according to the total 
market value of their outstanding shares. Every day an individual stock's price changes and thereby changes a stock index's value. The impact that individual 
stock's price change has on the index is proportional to the company's overall market value (the share price multiplied by the number of outstanding shares), 
in a capitalization-weighted index. In other types of indices, different ratios are used. [1]

### Stock types ###

Preferred and common stocks are different in two key aspects.

First, preferred stockholders have a greater claim to a company's assets and earnings. This is true during the good times when the company has excess cash and 
decides to distribute money in the form of dividends to its investors. In these instances when distributions are made, preferred stockholders must be paid before 
common stockholders. However, this claim is most important during times of insolvency when common stockholders are last in line for the company's assets. This 
means that when the company must liquidate and pay all creditors and bondholders, common stockholders will not receive any money until after the preferred 
shareholders are paid out.

Second, the dividends of preferred stocks are different from and generally greater than those of common stock. When you buy a preferred stock, you will have an 
idea of when to expect a dividend because they are paid at regular intervals. This is not necessarily the case for common stock, as the company's board of 
directors will decide whether or not to pay out a dividend. Because of this characteristic, preferred stock typically don't fluctuate as often as a company's 
common stock and can sometimes be classified as a fixed-income security. Adding to this fixed-income personality is the fact that the dividends are typically 
guaranteed, meaning that if the company does miss one, it will be required to pay it before any future dividends are paid on either stock.

To sum up: a good way to think of a preferred stock is as a security with characteristics somewhere in-between a bond and a common stock. [2]

### Stocks prices ###

A share price is the price of a single share of a number of saleable stocks of a company, derivative or other financial asset. In layman's terms, the stock 
price is the highest amount someone is willing to pay for the stock, or the lowest amount that it can be bought for. [4]

[3] Stock prices change every day as a result of market forces. By this we mean that share prices change because of supply and demand. If more people want to 
buy a stock (demand) than sell it (supply), then the price moves up. Conversely, if more people wanted to sell a stock than buy it, there would be greater 
supply than demand, and the price would fall. 

Understanding supply and demand is easy. What is difficult to comprehend is what makes people like a particular stock and dislike another stock. This comes 
down to figuring out what news is positive for a company and what news is negative. There are many answers to this problem and just about any investor you ask 
has their own ideas and strategies. 

That being said, the principal theory is that the price movement of a stock indicates what investors feel a company is worth. Don't equate a company's value 
with the stock price. The value of a company is its market capitalization, which is the stock price multiplied by the number of shares outstanding. For example, 
a company that trades at $100 per share and has 1 million shares outstanding has a lesser value than a company that trades at $50 that has 5 million shares 
outstanding ($100 x 1 million = $100 million while $50 x 5 million = $250 million). To further complicate things, the price of a stock doesn't only reflect a 
company's current value, it also reflects the growth that investors expect in the future. 

The most important factor that affects the value of a company is its earnings. Earnings are the profit a company makes, and in the long run no company can 
survive without them. It makes sense when you think about it. If a company never makes money, it isn't going to stay in business. Public companies are required 
to report their earnings four times a year (once each quarter). Wall Street watches with rabid attention at these times, which are referred to as earnings 
seasons. The reason behind this is that analysts base their future value of a company on their earnings projection. If a company's results surprise (are better 
than expected), the price jumps up. If a company's results disappoint (are worse than expected), then the price will fall. 

Of course, it's not just earnings that can change the sentiment towards a stock (which, in turn, changes its price). It would be a rather simple world if this 
were the case! During the dotcom bubble, for example, dozens of internet companies rose to have market capitalizations in the billions of dollars without ever 
making even the smallest profit. As we all know, these valuations did not hold, and most internet companies saw their values shrink to a fraction of their highs. 
Still, the fact that prices did move that much demonstrates that there are factors other than current earnings that influence stocks. Investors have developed 
literally hundreds of these variables, ratios and indicators. Some you may have already heard of, such as the price/earnings ratio, while others are extremely 
complicated and obscure with names like Chaikin oscillator or moving average convergence divergence. 

So, why do stock prices change? The best answer is that nobody really knows for sure. Some believe that it isn't possible to predict how stock prices will change, 
while others think that by drawing charts and looking at past price movements, you can determine when to buy and sell. The only thing we do know is that stocks 
are volatile and can change in price extremely rapidly. 

The important things to grasp about this subject are the following: 

1. At the most fundamental level, supply and demand in the market determines stock price. 
2. Price times the number of shares outstanding (market capitalization) is the value of a company. Comparing just the share price of two companies is meaningless. 
3. Theoretically, earnings are what affect investors' valuation of a company, but there are other indicators that investors use to predict stock price. 
Remember, it is investors' sentiments, attitudes and expectations that ultimately affect stock prices. 
4. There are many theories that try to explain the way stock prices move the way they do. Unfortunately, there is no one theory that can explain everything.

### Dividends ###

A dividend is a distribution of a portion of a company's earnings, decided by the board of directors, to a class of its shareholders. Dividends can be issued as 
cash payments, as shares of stock, or other property. [6]

A financial ratio that indicates how much a company pays out in dividends each year relative to its share price. Dividend yield is represented as a percentage 
and can be calculated by dividing the dollar value of dividends paid in a given year per share of stock held by the dollar value of one share of stock. The formula 
for calculating dividend yield may be represented as Annual Dividends per Share over Price per Share.

Yields for a current year are often estimated using the previous year’s dividend yield or by taking the latest quarterly yield, multiplying by 4 (adjusting for 
seasonality) and dividing by the current share price. [7]

### Price-Earnings Ratio ###

The Price-to-Earnings Ratio or P/E ratio is a ratio for valuing a company that measures its current share price relative to its per-share earnings.

The price-earnings ratio can be calculated as the "Market Value per Share" over "Earnings per Share".

For example, suppose that a company is currently trading at $43 a share and its earnings over the last 12 months were $1.95 per share. The P/E ratio for the stock could then be calculated as 43/1.95, or 22.05.

EPS is most often derived from the last four quarters. This form of the price-earnings ratio is called trailing P/E, which may be calculated by subtracting a company’s share value at the beginning of the 12-month period from its value at the period’s end, adjusting for stock splits if there have been any. Sometimes, price-earnings can also be taken from analysts’ estimates of earnings expected during the next four quarters. This form of price-earnings is also called projected or forward P/E. A third, less common variation uses the sum of the last two actual quarters and the estimates of the next two quarters.

The price-earnings ratio is also sometimes known as the price multiple or the earnings multiple. [8]

### Stocks vs Shares ###

In today's financial markets, the distinction between stocks and shares has been somewhat blurred. Generally, these words are used interchangeably to refer to 
the pieces of paper that denote ownership in a particular company, called stock certificates. However, the difference between the two words comes from the context 
in which they are used.

For example, "stock" is a general term used to describe the ownership certificates of any company, in general, and "shares" refers to a the ownership 
certificates of a particular company. So, if investors say they own stocks, they are generally referring to their overall ownership in one or more companies. 
Technically, if someone says that they own shares - the question then becomes - shares in what company?

Bottom line, stocks and shares are the same thing. The minor distinction between stocks and shares is usually overlooked, and it has more to do with syntax than 
financial or legal accuracy. [5]

[1]: https://en.wikipedia.org/wiki/Capitalization-weighted_index
[2]: http://www.investopedia.com/ask/answers/182.asp
[3]: http://www.investopedia.com/university/stocks/stocks4.asp
[4]: https://en.wikipedia.org/wiki/Share_price
[5]: http://www.investopedia.com/ask/answers/140.asp
[6]: http://www.investopedia.com/terms/d/dividend.asp
[7]: http://www.investopedia.com/terms/d/dividendyield.asp
[8]: http://www.investopedia.com/terms/p/price-earningsratio.asp
