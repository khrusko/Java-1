/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.dal;

import cinema.model.Admin;
import cinema.model.Movie;
import cinema.model.Actor;
import cinema.model.Director;
import cinema.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author karlo_6zwakzv
 */
public interface Repo
{
    //Users
    Optional<Admin> AuthAdmin(String username, String password) throws Exception;
    Optional<User> AuthUser(String username, String password) throws Exception;
    void createUser(String username, String password) throws Exception;

    //Actors
    int createActor(Actor actor) throws Exception;
    void createActors(List<Actor> actors) throws Exception;
    void updateActor(int id, Actor actordata) throws Exception;
    void deleteActor(int id)throws Exception;
    List<Actor> GetActors()throws Exception;
    Optional<Actor> getActor(int id) throws Exception;

    //Directors
    int createDirector(Director director) throws Exception;
    void createDirectors(List<Director> director) throws Exception;
    void updateDirector(int id,Director directordata) throws Exception;
    void deleteDirector(int id)throws Exception;
    List<Director> GetDirectors()throws Exception;
    Optional<Director> GetDirector(int id)throws Exception;

    //Movies
    int createMovie(Movie movie) throws Exception;
    void createMovies(List<Movie> movies) throws Exception;
    void updateMovie(int id,Movie data) throws Exception;
    void deleteMovie(int id)throws Exception;
    Optional<Movie> getMovie(int idMovie) throws Exception;
    List<Movie> GetMovies()throws Exception;
    Set<Actor> GetMovieActors(int movieId) throws Exception;

    //MovieActors
    void insertMovieActors(int movieId, int actorId)throws Exception;

    //Deletes
    int DeleteAllData()throws Exception;
    void DeleteData()throws Exception;
}
