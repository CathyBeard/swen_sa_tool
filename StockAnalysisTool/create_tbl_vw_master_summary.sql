/**
* Create stock ticker
* e.g. 100, MSFT, Microsoft
*      101, AAPL, Apple Inc.
**/
CREATE TABLE STOCK_TICKER (
	TICKER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
	SYMBOL TEXT NOT NULL UNIQUE,
	NAME TEXT NOT NULL UNIQUE
);

/**
* Source for the stock data
* e.g 100, Yahoo
*     101, Investopedia
**/
CREATE TABLE STOCK_SOURCE (
	SOURCE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
	NAME TEXT NOT NULL UNIQUE
);

/**
* Map date with the stock (normalized table to avoid redundancy
* in the STOCK_SUMMARY and STOCK_HISTORICAL tables)
* e.g. 100, "Mar, 07, 2019", 100, 100
**/
CREATE TABLE STOCK_DATE_MAP (
   STOCK_DT_MAP_ID INTEGER PRIMARY KEY AUTOINCREMENT,
   STOCK_DATE TEXT,
   TICKER_ID INTEGER REFERENCES STOCK_TICKET(TICKET_ID),
   SOURCE_ID INTEGER REFERENCES STOCK_SOURCE(SOURCE_ID)
);

/**
* Stock summary data
**/
CREATE TABLE STOCK_SUMMARY (
	SUMMARY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
	PREV_CLOSE_PRICE REAL,
	OPEN_PRICE REAL,
	BID_PRICE REAL,
	ASK_PRICE REAL,
	DAYS_RANGE_MIN REAL,
	DAYS_RANGE_MAX REAL,
	FIFTY_TWO_WEEKS_MIN REAL,
	FIFTY_TWO_WEEKS_MAX REAL,
	VOLUME INTEGER,
	AVG_VOLUME INTEGER,
	MARKET_CAP REAL,
	BETA_COEFFICIENT REAL,
	PE_RATIO REAL,
	EPS REAL,
	EARNING_DATE TEXT,
	DIVIDEND_YIELD REAL,
	EX_DIVIDEND_DATE TEXT,
	ONE_YEAR_TARGET_EST REAL,
  STOCK_DT_MAP_ID INTEGER REFERENCES STOCK_DATE_MAP(STOCK_DT_MAP_ID)
);


CREATE INDEX STOCK_DATE_IDX ON STOCK_DATE_MAP(STOCK_DATE);

CREATE VIEW STOCK_SUMMARY_VIEW AS
SELECT SDP.TICKER_ID, SDP.SOURCE_ID, MAX(SS.OPEN_PRICE) AS PRICE_MAX, MIN(SS.OPEN_PRICE) AS PRICE_MIN, AVG(SS.OPEN_PRICE) AS PRICE_AVERAGE
FROM STOCK_SUMMARY SS
INNER JOIN STOCK_DATE_MAP SDP ON SS.STOCK_DT_MAP_ID = SDP.STOCK_DT_MAP_ID
GROUP BY STOCK_DATE, SDP.SOURCE_ID;
