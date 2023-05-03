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
public class Actor implements Comparable<Actor>
{

  private static final String DELSPACE = "\\s+";
  private static final String DEL = ",";

  private int id;
  @XmlElement(name = "fullName")
  private String fullName;

  public Actor(int id, String fullName)
  {
    this.id = id;
    this.fullName = fullName;
  }

  public Actor()
  {
  }

  public Actor(String fullName)
  {
    this.fullName = fullName;
  }

  public int getId()
  {
    return id;
  }

  public String getFullName()
  {
    return fullName;
  }

  public void setFullName(String fullName)
  {
    this.fullName = fullName;
  }

  @Override
  public String toString()
  {
    return getFullName();
  }

  public static List<Actor> ParseActor(String actor)
  {
    if ("".equals(actor))
      return new ArrayList<>();
    List<Actor> actors = new ArrayList<>();
    String[] arrayOfActors = actor.trim().split(",", -1);

    for (String arrayOfActor : arrayOfActors)
    {
      String[] temp = arrayOfActor.trim().split(" ", -1);
      if (temp.length > 1)
        actors.add(new Actor(temp[0] + " " + temp[1]));
      else
        actors.add(new Actor(temp[0] + " " + temp[0]));
    }

    return actors;

  }

  public int compareTo(Actor a)
  {

    return fullName.compareTo(a.fullName);

  }

}
