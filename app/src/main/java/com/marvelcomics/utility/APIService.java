package com.marvelcomics.utility;

import com.marvelcomics.characters.CharacterModel;
import com.marvelcomics.comics.ComicsModel;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by SHUCHI on 6/3/2016.
 */
public interface APIService {

    @GET("characters")
    Call<CharacterModel> getCharacters(
            @Query("apikey") String key,
            @Query("hash") String hash,
            @Query("ts") String timestamp,
            @Query("limit") int limit
    );

    @GET("characters/{characterid}/comics")
    Call<ComicsModel> getComics(
            @Path("characterid") int characterid,
            @Query("apikey") String key,
            @Query("hash") String hash,
            @Query("ts") String timestamp,
            @Query("limit") int limit
    );
    @GET("comics/{comicsId}")
    Call<ComicsModel> getComicDetails(
            @Path("comicsId") int comicsId,
            @Query("apikey") String key,
            @Query("hash") String hash,
            @Query("ts") String timestamp,
            @Query("limit") int limit
    );
}
