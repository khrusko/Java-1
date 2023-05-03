/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karlo_6zwakzv
 */

@XmlRootElement(name = "allmovies")
@XmlAccessorType(XmlAccessType.FIELD)
public class AllMovies
{

    @XmlElementWrapper
    @XmlElement(name = "movie")
    private List<Movie> movies;

    public AllMovies() {
    }

    public AllMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

}
