/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalysistool;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

public final class StockDao {
    private static StockDao instance = null;
    private Connection conn = null;
    private static String databaseName = "stocksdb.sqlite";
    private static String databaseUrl = "jdbc:sqlite:stocksdb.sqlite";
    
    public StockDao(){
        
        //check to see if the DB already exists
        File file = new File(databaseName);
        if (!file.exists()) {
        //Create the database
        this.connect();
        
        try {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The database has been created");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        ArrayList<String> sqlStrings = new ArrayList<String>();
        
        //Create the tables
        String stockTicker = "CREATE TABLE IF NOT EXISTS STOCK_TICKER (\n"
                + "	TICKER_ID INTEGER PRIMARY KEY,\n"
                + "	SYMBOL TEXT NOT NULL UNIQUE,\n"
                + "	NAME TEXT NOT NULL UNIQUE\n"
                + ");";
        
        sqlStrings.add(stockTicker);
        
        String stockSource = "CREATE TABLE IF NOT EXISTS STOCK_SOURCE (\n"
                + "	SOURCE_ID INTEGER PRIMARY KEY,\n"
                + "	NAME TEXT NOT NULL UNIQUE\n"
                + ");";
        
        sqlStrings.add(stockSource);
        
        String stockDateMap = "CREATE TABLE IF NOT EXISTS STOCK_DATE_MAP (\n"
                + "	STOCK_DT_MAP_ID INTEGER PRIMARY KEY,\n"
                + "	STOCK_DATE INTEGER,\n"
                + "	TICKER_ID INTEGER REFERENCES STOCK_TICKET(TICKET_ID),\n"
                + "	SOURCE_ID INTEGER REFERENCES STOCK_SOURCE(SOURCE_ID)\n"
                + ");";
        
        sqlStrings.add(stockDateMap);
        
        String stockSummary = "CREATE TABLE IF NOT EXISTS STOCK_SUMMARY (\n"
                + "	SUMMARY_ID INTEGER PRIMARY KEY,\n"
                + "	PREV_CLOSE_PRICE REAL,\n"
                + "	OPEN_PRICE REAL,\n"
                + "	BID_PRICE REAL,\n"
                + "	ASK_PRICE REAL,\n"
                + "	DAYS_RANGE_MIN REAL,\n"
                + "	DAYS_RANGE_MAX REAL,\n"
                + "	FIFTY_TWO_WEEKS_MIN REAL,\n"
                + "	FIFTY_TWO_WEEKS_MAX REAL,\n"
                + "	VOLUME INTEGER,\n"
                + "	AVG_VOLUME INTEGER,\n"
                + "	MARKET_CAP REAL,\n"
                + "	BETA_COEFFICIENT REAL,\n"
                + "	PE_RATIO REAL,\n"
                + "	EPS REAL,\n"
                + "	EARNING_DATE INTEGER,\n"
                + "	DIVIDEND_YIELD REAL,\n"
                + "	EX_DIVIDEND_DATE INTEGER,\n"
                + "	ONE_YEAR_TARGET_EST REAL,\n"
                + "	STOCK_DT_MAP_ID INTEGER REFERENCES STOCK_DATE_MAP(STOCK_DT_MAP_ID)\n"
                + ");";
        
        sqlStrings.add(stockSummary);
        
        //Placeholder for the StockHistorical string.
        
        //Creating the index
        String index = "CREATE INDEX STOCK_DATE_IDX ON STOCK_DATE_MAP(STOCK_DATE);";
        
        sqlStrings.add(index);
        
        //Creating the View strings
        String stockSummaryView = "CREATE OR REPLACE VIEW STOCK_SUMMARY_VIEW AS\n"
                + "	SELECT SDP.TICKER_ID, SDP.SOURCE_ID, MAX(SS.OPEN_PRICE) AS PRICE_MAX,\n"
                + "	MIN(SS.OPEN_PRICE) AS PRICE_MIN, AVG(SS.OPEN_PRICE) AS PRICE_AVERAGE\n"
                + "	FROM STOCK_SUMMARY SS\n"
                + "	INNER JOIN STOCK_DATE_MAP SDP ON SS.STOCK_DT_MAP_ID =\n"
                + "	SDP.STOCK_DT_MAP_ID\n"
                + "	GROUP BY STOCK_DATE, SDP.SOURCE_ID;";
        
        sqlStrings.add(stockSummaryView);
        
        //Placeholder for stockhistorical view
        
        //Execute the SQL strings in the DB.
        for(String str:sqlStrings){
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(str);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        this.disconnect();
      }
    }
    
    public static StockDao getInstance(){
        if(instance == null){
            instance = new StockDao();
        }
        return instance;
    }
    
    public void connect() {   //Connects to the database
        try {
            conn = DriverManager.getConnection(databaseUrl);            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void disconnect(){//Disconnects from the database
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void setStockTickerData(String stockName, String stockSymbol){
        String sql = "INSERT INTO STOCK_TICKER (SYMBOL, NAME) VALUES (?, ?);";
                    
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stockName);
            pstmt.setString(2, stockSymbol);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void setStockSource(String stockSource){
        String sql = "INSERT INTO STOCK_SOURCE (NAME) VALUES (?);";
        
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stockSource);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertStockSummaryData(StockSummary stockSummary){
        //Commenting this out until we get to the historical data part of the project. -Jason
        
        //I renamed this from insertStockHistoricalData to insertStockSummaryData. 
        //But it needs to be implemented to match the StockSummary class fields - Herve
        
//        String sql = "INSERT INTO "+Constants.TABLE_STOCKS+" ("
//                + Constants.FIELD_SYMBOL + ","
//                + Constants.FIELD_SOURCE + ","
//                + Constants.FIELD_DATE + ","
//                + Constants.FIELD_OPEN + ","
//                + Constants.FIELD_HIGH + ","
//                + Constants.FIELD_LOW + ","
//                + Constants.FIELD_CLOSE + ","
//                + Constants.FIELD_ADJUSTED_CLOSE + ","
//                + Constants.FIELD_VOLUME + ")"
//                + " values (?,?,?,?,?,?,?,?,?)";
//        try{
//            connect(); //<--CONNECTS TO DATABASE BEFORE STARTING AN OPERATION
//            PreparedStatement pstmt = conn.prepareStatement(sql);
////            pstmt.setString(1, stockSummary.getSymbol());
////            pstmt.setString(2, stockSummary.getSource());
////            pstmt.setDate(3, stockSummary.getDate());
//            pstmt.setInt(4, stockSummary.getOpen());
//            pstmt.setInt(5, stockSummary.getHigh());
//            pstmt.setInt(6, stockSummary.getLow());
//            pstmt.setInt(7, stockSummary.getClose());
//            pstmt.setInt(8, stockSummary.getAdjClose());
//            pstmt.setInt(9, stockSummary.getVolume());
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            disconnect();
//        }
        
    }
    
    public void getAvgStockSummaryView(){
        String sql = "SELECT * FROM STOCK_SUMMARY_VIEW;";
        
        try (
             Statement statement  = conn.createStatement();
             ResultSet results    = statement.executeQuery(sql)){
            System.out.println(results);
            while (results.next()) {
                //Still working on this, need an example of what the data looks liek and is displayed to finish.
                System.out.println(results.getInt("id") +  "\t" + 
                                   results.getString("name") + "\t" +
                                   results.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteAll(){
        String sql ="DELETE FROM "+ Constants.TABLE_STOCKS;
        try{
            connect();
            Statement stmt  = conn.createStatement();
            stmt.executeQuery(sql);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
    }
    
}
