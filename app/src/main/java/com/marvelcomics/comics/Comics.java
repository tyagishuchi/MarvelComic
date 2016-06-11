package com.marvelcomics.comics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.marvelcomics.R;
import com.marvelcomics.characters.CharacterAdapter;
import com.marvelcomics.characters.CharacterModel;
import com.marvelcomics.characters.CharacterRealm;
import com.marvelcomics.utility.APIAdapter;
import com.marvelcomics.utility.APIService;
import com.marvelcomics.utility.MarvelParameters;
import com.marvelcomics.utility.WebViewer;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class Comics extends AppCompatActivity implements View.OnClickListener {
    private APIAdapter apiAdapter = new APIAdapter();
    RecyclerView recyclerView;
    private APIService service;
    String time;
    MarvelParameters parameters;
    Bundle extras;
    LinearLayoutManager layoutManager;
    ComicAdapter adapter;
    private ProgressDialog mProgressDialog;
    private TextView defaultMsg,search;
    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        recyclerView = (RecyclerView)findViewById(R.id.comics_rv);
        defaultMsg = (TextView)findViewById(R.id.defaulMsg);

        try {
            extras = getIntent().getExtras();
            if(extras.getInt("id")!=0) {
                setComics();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        search = (TextView)findViewById(R.id.search);
        search.setOnClickListener(this);
    }

    public void setComics() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getDefaultInstance();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Fetching Awesomness...");
        mProgressDialog.show();
        service = apiAdapter.providesRestAdapter().create(APIService.class);
        parameters = new MarvelParameters();
        time = new Timestamp(new Date().getTime()).toString();
        Call<ComicsModel> callComics = service.getComics(extras.getInt("id"),parameters.apikey, parameters.getMD5(time + parameters.privateKey + parameters.apikey), time, 50);
        callComics.enqueue(new Callback<ComicsModel>() {
            @Override
            public void onResponse(Response<ComicsModel> response) {
                try {
                    ComicsModel comics = response.body();
                    if (comics.getData().getResults().size() != 0) {
                        CharacterRealm characterRealm = realm.where(CharacterRealm.class).equalTo("id", extras.getInt("id")).findFirst();

                        for (ComicsModel.Data.Result result : comics.getData().getResults()) {
                            realm.beginTransaction();
                            ComicRealm comic = realm.createObject(ComicRealm.class);
                            if (result.getId() != null) {
                                comic.setId(result.getId());
                            }
                            if (result.getTitle() != null) {
                                comic.setTitle(result.getTitle());
                            }
                            if (result.getModified() != null) {
                                comic.setModified(result.getModified());
                            }
                            if (result.getThumbnail().getPath() != null) {
                                comic.setPath(result.getThumbnail().getPath());
                            }
                            if (result.getThumbnail().getExtension() != null) {
                                comic.setExtension(result.getThumbnail().getExtension());
                            }
                            characterRealm.comics.add(comic);
                            Log.d("comics added at", characterRealm.getId() + "");
                            Log.d("comics added", comic.getId() + "");
                            realm.commitTransaction();
                        }

                        layoutManager = new LinearLayoutManager(getBaseContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new ComicAdapter(comics.getData().getResults());
                        recyclerView.setAdapter(adapter);
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                    } else {
                        defaultMsg.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onFailure(Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                if (t instanceof java.net.SocketTimeoutException) {
                    Snackbar.make(recyclerView, "Slow internet connection.", Snackbar.LENGTH_LONG).show();
                    try {
                        setComics();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else if (t instanceof UnknownHostException) {
                    showOfflineComics();
                   // Snackbar.make(recyclerView, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                } else
                    Snackbar.make(recyclerView, "Please try again after sometime.", Snackbar.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public void showOfflineComics(){
        ArrayList<ComicsModel.Data.Result> allCom = new ArrayList<>();
        ComicsModel.Data.Result single;
        ComicsModel.Data.Result.Thumbnail thumbnail;
        CharacterRealm results1 = realm.where(CharacterRealm.class).equalTo("id",extras.getInt("id")).findFirst();

        for (ComicRealm c : results1.getComics()) {
            single = new ComicsModel().new Data().new Result();
            thumbnail = new ComicsModel().new Data().new Result().new Thumbnail();
            thumbnail.setPath(c.getPath());
            thumbnail.setExtension(c.getExtension());
            single.setId(c.getId());
            single.setTitle(c.getTitle());
            single.setThumbnail(thumbnail);
            single.setModified(c.getModified());
            allCom.add(single);
        }
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ComicAdapter(allCom);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                startActivity(new Intent(this, WebViewer.class));
                break;
        }
    }
}
