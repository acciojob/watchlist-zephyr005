package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class MovieRepository {
    private HashMap<String,Movie> movieDB;
    private HashMap<String,Director> directorDB;
    private HashMap<String, List<String>> directorMovieDB;
    //Pair: directorName, movieName

    public MovieRepository(){
        this.movieDB = new HashMap<>();
        this.directorDB = new HashMap<>();
        this.directorMovieDB = new HashMap<>();
    }

    public void saveMovie(Movie movie){
        movieDB.put(movie.getName(),movie);
    }

    public void saveDirector(Director director){
        directorDB.put(director.getName(),director);
    }

    public void saveMovieDirectorPair(String movie, String director){
        if(movieDB.containsKey(movie) && directorDB.containsKey(director)){
            List<String> currentMoviesOfDirector = new ArrayList<>();
            if(directorMovieDB.containsKey(director)){
                currentMoviesOfDirector = directorMovieDB.get(director);
            }
            currentMoviesOfDirector.add(movie);
            directorMovieDB.put(director,currentMoviesOfDirector);
        }
    }

    public Movie findMovie(String movie){
        return movieDB.get(movie);
    }

    public Director findDirector(String director){
        return directorDB.get(director);
    }

    public List<String> findMoviesOfDirector(String director){
        List<String> movieList = new ArrayList<>();
        if(directorMovieDB.containsKey(director)){
            movieList = directorMovieDB.get(director);
        }
        return movieList;
    }

    public List<String> findAllMovies(){
        return new ArrayList<>(movieDB.keySet());
    }

    public void deleteDirector(String director){
        List<String> movies = new ArrayList<>();
        if(directorMovieDB.containsKey(director)){
            //1. Find the movie names by director from pair
            movies = directorMovieDB.get(director);

            //2. Deleting all the movies from movieDB by using MovieName
            for(String movie : movies){
                if(movieDB.containsKey(movie))
                    movieDB.remove(movie);
            }

            //3. Deleting the director from directorDB
            if(directorDB.containsKey(director))
                directorDB.remove(director);

            //4. Deleting the pair
            directorMovieDB.remove(director);
        }
    }

    public void deleteAllDirector(){

        HashSet<String> movieSet = new HashSet<>();

        //Deleting the directorDB
        directorDB = new HashMap<>();

        //Finding all the movies by all the directors
        for(String director : directorMovieDB.keySet()){
            //Iterating in the list of movies by a director
            for(String movie : directorMovieDB.get(director)){
                movieSet.add(movie);
            }
        }

        //Deleting the movies from movieDB
        for(String movie : movieSet){
            if(movieDB.containsKey(movie)){
                movieDB.remove(movie);
            }
        }

        //Clearing the pair
        directorMovieDB = new HashMap<>();
    }
}
