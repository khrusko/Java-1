/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.model;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 *
 * @author karlo_6zwakzv
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Director implements Comparable<Director>
{

    private static final String DELSPACE = "\\s+";
    private static final String DEL = ",";

    private int id;
    @XmlElement(name = "fullName")
    private String fullName;


    public Director(String fullName) {
        this.fullName = fullName;
    }

    public Director(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
    public Director() {
    }


    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return getFullName();
    }

    public static List<Director> ParseDirector(String director) {

      List<Director> listDirector = new ArrayList<Director>();
      if ("".equals(director)) {
           return listDirector;
       }
       String[] split = director.split(" ", -1);
       listDirector.add(new Director(split[0].trim() + " " + split[1].trim()));
       return listDirector;

    }

    public int compareTo(Director d) {

        return fullName.compareTo(d.fullName);

    }

}
