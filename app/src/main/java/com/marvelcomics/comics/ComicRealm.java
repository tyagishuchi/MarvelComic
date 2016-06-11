package com.marvelcomics.comics;

import com.marvelcomics.strips.SingleComicRealm;

import io.realm.RealmObject;

/**
 * Created by SHUCHI on 6/11/2016.
 */
public class ComicRealm extends RealmObject {
    public Integer id;
    public String title;
    public String modified;
    public String characters;
    public String path;
    public String extension;
    public SingleComicRealm singleComicRealm;

    public SingleComicRealm getSingleComicRealm() {
        return singleComicRealm;
    }

    public void setSingleComicRealm(SingleComicRealm singleComicRealm) {
        this.singleComicRealm = singleComicRealm;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
