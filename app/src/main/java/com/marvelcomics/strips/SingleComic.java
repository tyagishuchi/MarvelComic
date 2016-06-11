package com.marvelcomics.strips;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marvelcomics.R;
import com.marvelcomics.characters.CharacterRealm;
import com.marvelcomics.comics.ComicRealm;
import com.marvelcomics.comics.ComicsModel;
import com.marvelcomics.utility.APIAdapter;
import com.marvelcomics.utility.APIService;
import com.marvelcomics.utility.MarvelParameters;
import com.squareup.picasso.Picasso;

import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class SingleComic extends AppCompatActivity {
    ImageView toolbar;
    Bundle extras;
    private APIAdapter apiAdapter = new APIAdapter();
    private APIService service;
    String time;
    MarvelParameters parameters;
    TextView title, character, creators, description, price;
    private ProgressDialog mProgressDialog;
    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_comic);
        toolbar = (ImageView) findViewById(R.id.toolbar);
        extras = getIntent().getExtras();
        title = (TextView) findViewById(R.id.title);
        //issueNo = (TextView) findViewById(R.id.issueNo);
        character = (TextView) findViewById(R.id.characters);
        creators = (TextView) findViewById(R.id.creators);
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.price);
        if (extras.get("id") != null) {
            setDetails();
        }
    }

    public void setDetails() {
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
        Call<ComicsModel> callComics = service.getComicDetails(extras.getInt("id"), parameters.apikey, parameters.getMD5(time + parameters.privateKey + parameters.apikey), time, 50);
        callComics.enqueue(new Callback<ComicsModel>() {
            @Override
            public void onResponse(Response<ComicsModel> response) {
                try {
                    //getting instance of ComicRealm class associated with this id
                    ComicsModel comics = response.body();
                    setAndSaveData(comics);

                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                if (t instanceof java.net.SocketTimeoutException) {
                    Snackbar.make(toolbar, "Slow internet connection.", Snackbar.LENGTH_LONG).show();
                    setDetails();
                } else if (t instanceof UnknownHostException) {
                    showOfflineData();
                    Snackbar.make(toolbar, "Please check your internet connection.", Snackbar.LENGTH_LONG).show();
                } else
                    Snackbar.make(toolbar, "Please try again after sometime.", Snackbar.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public void setAndSaveData(ComicsModel comic) {
        try {
            StringBuilder charString = new StringBuilder();
            StringBuilder creatorString = new StringBuilder();

            Picasso.with(getBaseContext())
                    .load(comic.getData().getResults().get(0).getImages().get(0).getPath() + "." + comic.getData().getResults().get(0).getImages().get(0).getExtension())
                    .into(toolbar);
            title.setText(comic.getData().getResults().get(0).getTitle());

            description.setText(comic.getData().getResults().get(0).getDescription());

            for (ComicsModel.Data.Result.Price prices : comic.getData().getResults().get(0).getPrices()) {
                if (prices.getType().equalsIgnoreCase("digitalPurchasePrice")) {
                    price.setText("Price : $" + prices.getPrice());
                } else price.setText("Digital Price Not Available");
            }
            for (ComicsModel.Data.Result.Characters.Item_ characters : comic.getData().getResults().get(0).getCharacters().getItems()) {
                charString.append(characters.getName() + ",");
            }
            SpannableString characterSpan = new SpannableString("Characters :" + charString);
            characterSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            character.setText(characterSpan);

            StringBuilder penciller = new StringBuilder();
            StringBuilder writer = new StringBuilder();
            StringBuilder colorist = new StringBuilder();
            StringBuilder inker = new StringBuilder();
            StringBuilder letterer = new StringBuilder();
            for (ComicsModel.Data.Result.Creators.Item creator : comic.getData().getResults().get(0).getCreators().getItems()) {
                if (creator.getRole().equalsIgnoreCase("penciller")) {
                    penciller.append(creator.getName() + ",");
                } else if (creator.getRole().equalsIgnoreCase("writer")) {
                    writer.append(creator.getName() + ",");
                } else if (creator.getRole().equalsIgnoreCase("colorist")) {
                    colorist.append(creator.getName() + ",");
                } else if (creator.getRole().equalsIgnoreCase("inker")) {
                    inker.append(creator.getName() + ",");
                } else if (creator.getRole().equalsIgnoreCase("letterer")) {
                    letterer.append(creator.getName() + ",");
                }
            }
            if (!penciller.toString().equalsIgnoreCase("")) {
                creatorString.append(penciller);
                creatorString.append("(penciller)");
            }
            if (!writer.toString().equalsIgnoreCase("")) {
                creatorString.append(writer);
                creatorString.append("(writer)");
            }
            if (!inker.toString().equalsIgnoreCase("")) {
                creatorString.append(inker);
                creatorString.append("(inker)");
            }
            if (!letterer.toString().equalsIgnoreCase("")) {
                creatorString.append(letterer);
                creatorString.append("(letterer)");
            }
            if (!colorist.toString().equalsIgnoreCase("")) {
                creatorString.append(colorist);
                creatorString.append("(colorist)");
            }
            SpannableString creatorSpan = new SpannableString("Creators : " + creatorString);
            creatorSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            creators.setText(creatorSpan);

            //Storing data in the realm
            ComicRealm comicRealm = realm.where(ComicRealm.class).equalTo("id", extras.getInt("id")).findFirst();
            realm.beginTransaction();
            SingleComicRealm singleComicRealm = realm.createObject(SingleComicRealm.class);
            singleComicRealm.setTitle(comic.getData().getResults().get(0).getTitle());
            singleComicRealm.setPath(comic.getData().getResults().get(0).getImages().get(0).getPath());
            singleComicRealm.setExtension(comic.getData().getResults().get(0).getImages().get(0).getExtension());
            singleComicRealm.setDescription(comic.getData().getResults().get(0).getDescription());
            singleComicRealm.setPriceType("digitalPurchasePrices");
            singleComicRealm.setPrice("Prices not available offline");
            singleComicRealm.setCreators(creatorSpan.toString());
            singleComicRealm.setCharacters(characterSpan.toString());
            comicRealm.setSingleComicRealm(singleComicRealm);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showOfflineData() {
        try {
            ComicsModel comicsModel = new ComicsModel();
            ComicsModel.Data.Result result = new ComicsModel().new Data().new Result();
            ComicsModel.Data.Result.Thumbnail thumbnail = new ComicsModel().new Data().new Result().new Thumbnail();
            ComicRealm results1 = realm.where(ComicRealm.class).equalTo("id", extras.getInt("id")).findFirst();

            result.setTitle(results1.getSingleComicRealm().getTitle());
            result.setDescription(results1.getSingleComicRealm().getDescription());

            thumbnail.setPath(results1.getSingleComicRealm().getPath());
            thumbnail.setExtension(results1.getSingleComicRealm().getExtension());
            result.setThumbnail(thumbnail);

            setAndSaveData(comicsModel);
            character.setText(results1.getSingleComicRealm().getCharacters());
            creators.setText(results1.getSingleComicRealm().getCreators());
            price.setText(results1.getSingleComicRealm().getPrice());
        } catch (NullPointerException e) {
            Toast.makeText(this, "Data was not saved for this comic", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
