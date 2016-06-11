package com.marvelcomics.characters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.marvelcomics.R;
import com.marvelcomics.utility.APIAdapter;
import com.marvelcomics.utility.APIService;
import com.marvelcomics.utility.MarvelParameters;
import com.marvelcomics.utility.WebViewer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    private APIAdapter apiAdapter = new APIAdapter();
    private APIService service;
    private CharacterAdapter adapter;
    private boolean loading = true;
    GridLayoutManager layoutManager;
    private TextView search;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int total, offset;
    String time;
    MarvelParameters parameters;
    List<CharacterModel.Data.Result> charcters = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView = (RecyclerView) findViewById(R.id.characters_rv);
        try {
            setCharacters();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        search = (TextView) findViewById(R.id.search);
        search.setOnClickListener(this);

    }

    public void setCharacters() throws UnsupportedEncodingException, NoSuchAlgorithmException {
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
        Call<CharacterModel> callCharacters = service.getCharacters(parameters.apikey, parameters.getMD5(time + parameters.privateKey + parameters.apikey), time, 50);
        callCharacters.enqueue(new Callback<CharacterModel>() {
            @Override
            public void onResponse(Response<CharacterModel> response) {
                CharacterModel allCharacters = response.body();
                total = allCharacters.getData().getTotal();
                offset = allCharacters.getData().getLimit();
                charcters = allCharacters.getData().getResults();
                if (!realm.isEmpty()) {
                    realm.beginTransaction();
                    realm.delete(CharacterRealm.class);
                    realm.commitTransaction();
                    realm.close();
                    for (CharacterModel.Data.Result result : charcters) {
                        saveCharacters(result);
                    }
                } else {
                    realmConfig = new RealmConfiguration.Builder(getBaseContext()).build();
                    Realm.setDefaultConfiguration(realmConfig);
                    realm = Realm.getDefaultInstance();
                    for (CharacterModel.Data.Result result : charcters) {
                        saveCharacters(result);
                    }
                }
                layoutManager = new GridLayoutManager(getBaseContext(), 2);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new CharacterAdapter(charcters);
                recyclerView.setAdapter(adapter);
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                        if (dy > 0) //check for scroll down
                        {
                            visibleItemCount = layoutManager.getChildCount();
                            totalItemCount = layoutManager.getItemCount();
                            pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                            if (loading) {
                                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    loading = false;
                                    offset++;
                                    if (offset < total) {
                                        time = new Timestamp(new Date().getTime()).toString();
                                        Call<CharacterModel> callCharacters = service.getCharacters(parameters.apikey, parameters.getMD5(time + parameters.privateKey + parameters.apikey), time, offset);
                                        callCharacters.enqueue(new Callback<CharacterModel>() {
                                            @Override
                                            public void onResponse(Response<CharacterModel> response) {
                                                CharacterModel characterModel = response.body();
                                                for (CharacterModel.Data.Result result : characterModel.getData().getResults()) {
                                                    charcters.add(result);
                                                }
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onFailure(Throwable t) {
                                                if (mProgressDialog.isShowing())
                                                    mProgressDialog.dismiss();
                                                if (t instanceof java.net.SocketTimeoutException) {
                                                    Snackbar.make(recyclerView, "Slow internet connection.", Snackbar.LENGTH_LONG).show();
                                                    try {
                                                        setCharacters();
                                                    } catch (UnsupportedEncodingException e) {
                                                        e.printStackTrace();
                                                    } catch (NoSuchAlgorithmException e) {
                                                        e.printStackTrace();
                                                    }
                                                } else if (t instanceof UnknownHostException) {
                                                    Snackbar.make(recyclerView, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                                                } else
                                                    Snackbar.make(recyclerView, "Please try again after sometime.", Snackbar.LENGTH_LONG).show();
                                                t.printStackTrace();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                if (t instanceof java.net.SocketTimeoutException) {
                    Snackbar.make(recyclerView, "Slow internet connection.", Snackbar.LENGTH_LONG).show();
                    try {
                        setCharacters();
                        showOfflineCharacters();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else if (t instanceof UnknownHostException) {
                    showOfflineCharacters();
                    //Snackbar.make(recyclerView, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                } else
                    Snackbar.make(recyclerView, "Please try again after sometime.", Snackbar.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public void saveCharacters(final CharacterModel.Data.Result data) {
        try {
            realm.beginTransaction();
            Log.d("phirse", "ex");
            CharacterRealm user = realm.createObject(CharacterRealm.class);
            if (data.getId() != null) {
                user.setId(data.getId());
            }
            if (data.getName() != null) {
                user.setName(data.getName());
            }
            if (data.getThumbnail().getPath() != null) {
                user.setPath(data.getThumbnail().getPath());
            }
            if (data.getThumbnail().getExtension() != null) {
                user.setExtension(data.getThumbnail().getExtension());
            }
            realm.commitTransaction();

        } catch (IllegalStateException e) {
            Toast.makeText(this, "Cannot be stored", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showOfflineCharacters() {
        try {
            ArrayList<CharacterModel.Data.Result> allChar = new ArrayList<>();
            CharacterModel.Data.Result single;
            CharacterModel.Data.Result.Thumbnail thumbnail;
            RealmResults<CharacterRealm> results1 =
                    realm.where(CharacterRealm.class).findAll();

            for (CharacterRealm c : results1) {
                single = new CharacterModel().new Data().new Result();
                thumbnail = new CharacterModel().new Data().new Result().new Thumbnail();
                thumbnail.setPath(c.getPath());
                thumbnail.setExtension(c.getExtension());
                single.setId(c.getId());
                single.setName(c.getName());
                single.setThumbnail(thumbnail);
                Log.d("results1", single.getId() + "");
                allChar.add(single);
            }
            layoutManager = new GridLayoutManager(getBaseContext(), 2);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new CharacterAdapter(allChar);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "Oops! some erroe occured while getting data", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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
