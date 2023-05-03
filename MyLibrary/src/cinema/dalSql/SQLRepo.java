/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.dalSql;

import cinema.dal.Repo;
import cinema.model.Actor;
import cinema.model.Admin;
import cinema.model.Director;
import cinema.model.Movie;
import cinema.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.sql.DataSource;

/**
 *
 * @author karlo_6zwakzv
 */
public class SQLRepo implements Repo
{


    //DIRECTOR
    private static final String ID_DIRECTOR = "IdDirector";
    private static final String DIRECTOR_FULL_NAME = "FullName";
    private static final String CREATE_DIRECTOR = "{ CALL createDirector(?,?)}";
    private static final String GET_DIRECTORS = "{ CALL getDirectors}";
    private static final String GET_DIRECTOR = "{ CALL getDirector(?)}";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector(?)}";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector(?,?)}";

    //ACTOR
    private static final String ID_ACTOR = "IdActor";
    private static final String ACTOR_FULL_NAME = "FullName";
    private static final String CREATE_ACTOR = "{CALL createActor(?,?)}";
    private static final String GET_ACTORS = "{CALL getActors}";
    private static final String DELETE_ACTOR = "{CALL deleteActor(?)}";
    private static final String UPDATE_ACTOR = "{CALL updateActor(?,?)}";
    private static final String GET_ACTOR = "{CALL getActor(?)}";

    //USERS
    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String AUTH_ADMIN = "{ CALL authAdmin (?,?)}";
    private static final String AUTH_USER = "{ CALL authUser (?,?)}";
    private static final String CREATE_USER = "{ CALL createUser (?,?)}"; //Username + Password

    //MOVIE
    private static final String ID_MOVIE = "IdMovie";
    private static final String MOVIE_ID = "MovieId";
    private static final String ACTOR_ID = "ActorId";
    private static final String TITLE_ENG = "TitleEng";
    private static final String TITLE_HRV = "TitleHrv";
    private static final String DESCRIPTION = "Description";
    private static final String PUB_DATE = "PublishedDate";
    private static final String BEGIN_DATE = "StartDate";
    private static final String IMAGE_PATH = "PicturePath";
    private static final String DURATION = "Duration";
    private static final String GENRENAME = "Category";
    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?)}";
    private static final String GET_MOVIES = "{ CALL getMovies}";
    private static final String GET_MOVIE = "{ CALL getMovie(?)}";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie(?)}";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie(?,?,?,?,?,?,?,?,?)}";
    private static final String INSERT_MOVIE_ACTORS = "{ CALL insertMovieActors(?,?)}";
    private static final String GET_MOVIE_ACTORS = "{ CALL getMovieActors(?)}";

    //Delete Data
    private static final String DELETE_ALL_DATA = "{CALL deleteAllData}";
    private static final String DELETE_DATA = "{CALL deleteAllMovies}";


    @Override
    public void DeleteData() throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DATA)) {
            stmt.executeUpdate();
        }

    }

    @Override
    public int DeleteAllData() throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(DELETE_ALL_DATA)) {

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                }
            }
        }
        return 0;
    }

    @Override
    public Optional<Admin> AuthAdmin(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(AUTH_ADMIN)) {

            stmt.setString("@" + USERNAME, username);
            stmt.setString("@" + PASSWORD, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    return Optional.of(new Admin(
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD)));
                }
            }

        }
        return Optional.empty();
    }

     @Override
    public Optional<User> AuthUser(String username, String password) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(AUTH_USER)) {

            stmt.setString("@" + USERNAME, username);
            stmt.setString("@" + PASSWORD, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    return Optional.of(new User(
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD)));
                }
            }

        }
        return Optional.empty();
    }

     @Override
    public void createUser(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(CREATE_USER)) {
            stmt.setString("@" + USERNAME, username);
            stmt.setString("@" + PASSWORD, password);
            //stmt.setString("@" + ID_USER, "0");

            stmt.executeUpdate();

        }
    }


    @Override
    public void insertMovieActors(int MovieId, int ActorId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(INSERT_MOVIE_ACTORS)) {

            stmt.setInt("@" + MOVIE_ID, MovieId);
            stmt.setInt("@" + ACTOR_ID, ActorId);

            stmt.executeUpdate();

        }
    }

    @Override
    public Set<Actor> GetMovieActors(int MovieId) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();

        Set<Actor> actors = new TreeSet<>();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_MOVIE_ACTORS)) {

            stmt.setInt("@" + MOVIE_ID, MovieId);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    actors.add(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_FULL_NAME)));
                }
            }
        }
        return actors;

    }


    @Override
    public int createActor(Actor actor) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(CREATE_ACTOR)) {
            stmt.setString("@" + ACTOR_FULL_NAME, actor.getFullName());

            stmt.registerOutParameter("@" + ID_ACTOR, Types.INTEGER);
            stmt.executeUpdate();

            return stmt.getInt("@" + ID_ACTOR);

        }

    }

    @Override
    public void createActors(List<Actor> actors) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(CREATE_ACTOR)) {

            for (Actor actor : actors) {
                stmt.setString("@" + ACTOR_FULL_NAME, actor.getFullName());
                stmt.registerOutParameter("@" + ID_ACTOR, Types.INTEGER);

                stmt.executeUpdate();
            }

        }
    }

    @Override
    public void updateActor(int id, Actor data) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {

            stmt.setString("@" + ACTOR_FULL_NAME, data.getFullName());

            stmt.setInt("@" + ID_ACTOR, id);
            stmt.executeUpdate();

        }

    }

    @Override
    public void deleteActor(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {

            stmt.setInt("@" + ID_ACTOR, id);

            stmt.executeUpdate();
        }

    }

    @Override
    public List<Actor> GetActors() throws Exception {

        List<Actor> actors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(GET_ACTORS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(ACTOR_FULL_NAME)
                ));
            }

        }
        return actors;
    }

    @Override
    public Optional<Actor> getActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_ACTOR)) {

            stmt.setInt("@" + ID_ACTOR, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_FULL_NAME)
                    ));
                }
            }
        }
        return Optional.empty();

    }

    @Override
    public int createDirector(Director director) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(CREATE_DIRECTOR)) {
            stmt.setString("@" + DIRECTOR_FULL_NAME, director.getFullName());

            stmt.registerOutParameter("@" + ID_DIRECTOR, Types.INTEGER);
            stmt.executeUpdate();

            return stmt.getInt("@" + ID_DIRECTOR);

        }

    }

    @Override
    public void createDirectors(List<Director> directors) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(CREATE_DIRECTOR)) {

            for (Director director : directors) {

                stmt.setString("@" + DIRECTOR_FULL_NAME, director.getFullName());
                stmt.registerOutParameter("@" + ID_DIRECTOR, Types.INTEGER);

                stmt.executeUpdate();
            }

        }

    }

    @Override
    public void updateDirector(int id, Director data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {

            stmt.setString("@" + DIRECTOR_FULL_NAME, data.getFullName());
            stmt.setInt("@" + ID_DIRECTOR, id);
            stmt.executeUpdate();

        }
    }

    @Override
    public void deleteDirector(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {

            stmt.setInt("@" + ID_DIRECTOR, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Director> GetDirector(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_DIRECTOR)) {

            stmt.setInt("@" + ID_DIRECTOR, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRECTOR_FULL_NAME)
                    ));
                }
            }
        }
        return Optional.empty();

    }

    @Override
    public List<Director> GetDirectors() throws Exception {

        List<Director> directors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(GET_DIRECTORS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(DIRECTOR_FULL_NAME)
                ));
            }

        }
        return directors;
    }

 @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(CREATE_MOVIE)) {
            stmt.setString("@" + TITLE_HRV, movie.getTitle());
            stmt.setString("@" + TITLE_ENG, movie.getOriginNaziv());
            stmt.setString("@" + PUB_DATE, movie.getPubDate());
            stmt.setString("@" + DESCRIPTION, movie.getDescription());
            stmt.setString("@" + BEGIN_DATE, movie.getBeginDate().format(Movie.DATE_FORMATTER_BEGINDATE));
            stmt.setString("@" + IMAGE_PATH, movie.getImagePath());
            stmt.setInt("@" + DURATION, movie.getDuration());

            stmt.setString("@" + GENRENAME, movie.getGenre());

            stmt.registerOutParameter("@" + ID_MOVIE, Types.INTEGER);
            stmt.executeUpdate();

            return stmt.getInt("@" + ID_MOVIE);

        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(CREATE_MOVIE)) {

            for (Movie movie : movies) {
                stmt.setString("@" + TITLE_HRV, movie.getTitle());
                stmt.setString("@" + TITLE_ENG, movie.getOriginNaziv());
                stmt.setString("@" + PUB_DATE, movie.getPubDate());
                stmt.setString("@" + DESCRIPTION, movie.getDescription());
                stmt.setString("@" + BEGIN_DATE, movie.getBeginDate().format(Movie.DATE_FORMATTER_BEGINDATE));
                stmt.setString("@" + IMAGE_PATH, movie.getImagePath());
                stmt.setInt("@" + DURATION, movie.getDuration());
                stmt.setString("@" + GENRENAME, movie.getGenre());

                stmt.registerOutParameter("@" + ID_MOVIE, Types.INTEGER);
                stmt.executeUpdate();
            }

        }

    }

    @Override
    public void updateMovie(int id, Movie movie) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {

           stmt.setString("@" + TITLE_HRV, movie.getTitle());
            stmt.setString("@" + TITLE_ENG, movie.getOriginNaziv());
            stmt.setString("@" + PUB_DATE, movie.getPubDate());
            stmt.setString("@" + DESCRIPTION, movie.getDescription());
            stmt.setString("@" + BEGIN_DATE, movie.getBeginDate().format(Movie.DATE_FORMATTER_BEGINDATE));
            stmt.setString("@" + IMAGE_PATH, movie.getImagePath());
            stmt.setInt("@" + DURATION, movie.getDuration());
            stmt.setString("@" + GENRENAME, movie.getGenre());


            stmt.setInt("@" + ID_MOVIE, id);
            stmt.executeUpdate();

        }

    }

    @Override
    public void deleteMovie(int id) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {

            stmt.setInt("@" + ID_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public List<Movie> GetMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_MOVIES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE_HRV),
                        rs.getString(PUB_DATE),
                        rs.getString(DESCRIPTION),
                        rs.getString(TITLE_ENG),
                        rs.getString(GENRENAME),
                        rs.getInt(DURATION),
                        rs.getString(IMAGE_PATH),
                        LocalDate.parse(rs.getString(BEGIN_DATE), Movie.DATE_FORMATTER_BEGINDATE)));
            }
        }
        return movies;
    }

    @Override
    public Optional<Movie> getMovie(int IdMovie) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_MOVIE)) {

            stmt.setInt("@" + ID_MOVIE, IdMovie);
            try (ResultSet rs = stmt.executeQuery()) {
            //int id,String title, String description, String originNaziv, int duration, String imagePath,LocalDate beginDate
                if (rs.next()) {
                    return Optional.of(new Movie(
                             rs.getInt(ID_MOVIE),
                             rs.getString(TITLE_HRV),
                             rs.getString(PUB_DATE),
                             rs.getString(DESCRIPTION),
                             rs.getString(TITLE_ENG),
                             rs.getString(GENRENAME),
                             rs.getInt(DURATION),
                             rs.getString(IMAGE_PATH),
                             LocalDate.parse(rs.getString(BEGIN_DATE), Movie.DATE_FORMATTER_BEGINDATE)));

                }
            }
        }
        return Optional.empty();

    }



}
