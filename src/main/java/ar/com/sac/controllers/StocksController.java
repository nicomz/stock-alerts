package ar.com.sac.controllers;

import ar.com.sac.model.IStockWrapper;
import ar.com.sac.model.Quote;
import ar.com.sac.model.QuoteId;
import ar.com.sac.services.IStockService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import yahoofinance.Utils;
import yahoofinance.YahooFinance;

@EnableWebMvc
@RestController
@RequestMapping("/stocks")
public class StocksController {
   
   @Autowired
   private IStockService stockService;
   
   @RequestMapping(value="/{symbol}" ,method = RequestMethod.GET)
   public IStockWrapper get(@PathVariable("symbol") String symbol ) throws IOException {
      return stockService.getStock( symbol );
   }
   
   @RequestMapping(method = RequestMethod.GET)
   public Collection<IStockWrapper> get( @RequestParam("symbols") String[] symbols ) throws IOException {
       return stockService.getStocks( symbols ).values();
   }
   
   @RequestMapping(value= "/history/{symbol}", method = RequestMethod.GET)
   public List<Quote> getHistory(@PathVariable("symbol") String symbol ) throws IOException {
      return stockService.getHistory( symbol );
   }
   
   
   @RequestMapping( value = "/import/csv", method = RequestMethod.POST )
   public ResponseEntity<HttpStatus> importQuotes( @RequestParam( value = "symbol" ) String symbol,
                                                   @RequestPart( value = "content_file" ) MultipartFile input ) throws IOException {

      List<Quote> quotes = new ArrayList<>();
      BufferedReader reader=new BufferedReader(new InputStreamReader(input.getInputStream()));

      int lineNumber = 0;
      while(reader.ready()){
           String line = reader.readLine();
           if(lineNumber == 0){
              lineNumber++;
              continue;
           }
           quotes.add( parseCSVLine(symbol, line) );
           lineNumber++;
      }
      
      stockService.importQuotes( quotes );

      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }

   //This method is similar to the parseCSVLine from YahooFinanceAPI
   private Quote parseCSVLine( String symbol, String line ) {
      Quote quote = new Quote();
      String[] data = line.split(YahooFinance.QUOTES_CSV_DELIMITER);
      quote.setId( new QuoteId(symbol,  Utils.parseHistDate(data[0])) );
      quote.setOpen( Utils.getBigDecimal(data[1]));
      quote.setHigh(Utils.getBigDecimal(data[2]));
      quote.setLow( Utils.getBigDecimal(data[3]));
      quote.setClose(Utils.getBigDecimal(data[4]));
      quote.setVolume(Utils.getLong(data[5]));
      return quote;
   }
   
   @RequestMapping(value="/symbols" ,method = RequestMethod.GET)
   public List<String> getSymbols() throws IOException {
      return stockService.getSymbols();
   }
   
   @RequestMapping(value="/daily_update" ,method = RequestMethod.GET)
   public ResponseEntity<HttpStatus> dailyUpdateDB() throws IOException {
      stockService.updateDBJob();
      return new ResponseEntity<HttpStatus> ( HttpStatus.OK );
   }
   
}
