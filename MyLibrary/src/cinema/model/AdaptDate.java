/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.model;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author karlo_6zwakzv
 */
public class AdaptDate extends XmlAdapter<String, LocalDate>
{

  @Override
  public String marshal(LocalDate d) throws Exception
  {

    return d.format(Movie.DATE_FORMATTER_BEGINDATE);
  }
  
  @Override
  public LocalDate unmarshal(String s) throws Exception
  {
    return LocalDate.parse(s, Movie.DATE_FORMATTER_BEGINDATE);

  }

}
