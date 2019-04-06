# StockReporter

StockReporter is an application that scrapes stock information from financial websites and stores the data in a database.  This project is the result of the SWEN 670 Capstone course at University of Maryland University College.

## Getting Started

These instructions will setup the project on your local machine for development and testing.

### Prerequisites

This project was developed with Java, JSoup, and SQLite.  Please note that running this application requires JDK 1.8.  
    
### Installation

On your local machine, git clone https://github.com/kennylg2/swen_sa_tool.git

* Netbeans: Right click the project -> Properties -> Libraries -> Add Jar/Folder and select the "jarfiles" folder.
* Eclipse: Right click the project -> Properties -> Java Build Path -> Add JARs and select the project name and "jarfiles" folder.


### Databases

The application has two databases, "stockreporter.dev" for dev and testing and "stockreporter.prod" for production.

## Testing

Three test packages have been developed.

## Project Phase

During the first phase of this project the summary and historical data will be scraped and added to the database. Additional scraping and analytical tools may be added in later phases of the project. The team is currently working on scraping the summary data.
