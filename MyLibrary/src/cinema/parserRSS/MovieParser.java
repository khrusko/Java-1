/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.parserRSS;

import cinema.model.Actor;
import cinema.model.Director;
import cinema.model.Movie;
import javaCinema.factory.ParserFactory;
import javaCinema.factory.UrlConnectionFactory;
import javaCinema.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


/**
 *
 * @author karlo_6zwakzv
 */
public class MovieParser
{

    private MovieParser() {
    }

    private static final String RSS_URL = "https://web.archive.org/web/20210228003317if_/https://www.blitz-cinestar.hr/rss.aspx?najava=1";
    private static final String ATT_URL = "url";
    private static final String EXTENSION = ".jpg";
    private static final String DIR = "assets";



    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<TagType> tagType = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (tagType.isPresent() && movie != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case TITLE:
                                    if (!data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;

                                case DESCRIPTION:
                                    if (!data.isEmpty()) {
                                        movie.setDescription(data);
                                    }
                                    break;

                                case PUBDATE:
                                    if (!data.isEmpty()) {
                                        movie.setPubDate(data);
                                    }
                                    break;
                                case ORIGNAZIV:
                                    if (!data.isEmpty()) {
                                        movie.setOriginNaziv(data);
                                    }
                                    break;
                                     case REDATELJ:
                                    if (!data.isEmpty()) {
                                        movie.setDirectors(Director.ParseDirector(data));
                                    }
                                    break;
                                    case TRAJANJE:
                                    if (!data.isEmpty()) {
                                        movie.setDuration(Integer.parseInt(data));
                                    }
                                    break;
                                    case ZANR:
                                    if (!data.isEmpty()) {
                                        movie.setGenre(data);
                                    }
                                    break;
                                     case PLAKAT:
                                   if (startElement != null && movie.getImagePath() == null) {

                                            handlePicture(movie, data);

                                    }
                                    break;
                                    case POCETAK:
                                    if (!data.isEmpty()) {
                                       LocalDate beginDate = LocalDate.parse(data.trim(), Movie.DATE_FORMATTER_BEGINDATE);
                                        movie.setBeginDate(beginDate);
                                    }
                                    break;
                                    case GLUMCI:
                                        if (!data.isEmpty() && data.contains(" ")) {
                                            movie.setActors(Actor.ParseActor(data));
                                    }
                                    break;

                            }
                        }
                        break;
                }
            }
        }
        return movies;

    }

     private static void handlePicture(Movie movie, String pictureUrl) {

        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXTENSION;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            movie.setImagePath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        PUBDATE("pubDate"),
        DESCRIPTION("description"),
        ORIGNAZIV("orignaziv"),
        REDATELJ("redatelj"),
        GLUMCI("glumci"),
        TRAJANJE("trajanje"),
        ZANR("zanr"),
        PLAKAT("plakat"),
        LINK("link"),
        GUID("guid"),
        TRAILER("trailer"),
        POCETAK("pocetak");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }

    }



}
