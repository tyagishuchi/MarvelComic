package com.marvelcomics.characters;

import com.marvelcomics.comics.ComicRealm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SHUCHI on 6/10/2016.
 */
public class CharacterRealm extends RealmObject {
    public RealmList<ComicRealm> getComics() {
        return comics;
    }

    public void setComics(RealmList<ComicRealm> comics) {
        this.comics = comics;
    }

    @PrimaryKey

    private Integer id;
    private String name;
    private String path;
    private String extension;
    public RealmList<ComicRealm> comics;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }


    public String getExtension() {return extension;}

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
