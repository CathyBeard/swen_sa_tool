/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalysistool;

import java.util.Date;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.*;

/**
 *
 * @author ArvidsJE
 */
public class InvestopediaScraperTest {
    
    public InvestopediaScraperTest() {
    }

    /**
     * Test of scrape method, of class InvestopediaScraper.
     */
    @Test
    public void testScrape() {
        System.out.println("scrape");
        InvestopediaScraper instance = new InvestopediaScraper();
        instance.scrape();
    }

    /**
     * Test of scapeSingleHistoricalTables method, of class InvestopediaScraper.
     */
    @Test
    public void testScapeSingleHistoricalTables() {
        System.out.println("scapeSingleHistoricalTables");
        String symbolString = "";
        InvestopediaScraper instance = new InvestopediaScraper();
        instance.scapeSingleHistoricalTables(symbolString);
    }
    
}
