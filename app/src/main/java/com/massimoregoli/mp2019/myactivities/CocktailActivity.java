package com.massimoregoli.mp2019.myactivities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.massimoregoli.mp2019.R;
import com.massimoregoli.mp2019.data.Drink;
import com.massimoregoli.mp2019.interfaces.OnCocktailClickListener;
import com.massimoregoli.mp2019.interfaces.OnCategoryClickListener;
import com.massimoregoli.mp2019.threads.CocktailTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CocktailActivity extends AppCompatActivity implements OnCategoryClickListener, OnCocktailClickListener {
    private RecyclerView mRecyclerView;
    private TextView tvIngredients;
    private ArrayList<Category> mCategoryData;
    private ArrayList<Cocktail> mCocktailData;
    private CategoryAdapter mAdapter;
    private CocktailAdapter mAdapterCocktails;
    private LinearLayoutManager mLinearLayoutManager;
    private String[] mIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail);

        Drink ingredients = new Drink();

        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // Initialize the ArrayList that will contain the data.
        mCocktailData = new ArrayList<>();
        mCategoryData = new ArrayList<>();
        // Initialize the adapter and set it to the RecyclerView.
        initializeData();
        mAdapter = new CategoryAdapter(this, mCategoryData, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        // Get the data.

    }

    @Override
    public void onBackPressed() {
        if(mRecyclerView.getAdapter() instanceof CategoryAdapter)
            super.onBackPressed();
        else {
            mAdapter = new CategoryAdapter(this, mCategoryData, this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initializeData() {
        loadCategory();

    }

    private void loadCategory() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getAssets().open("cocktail.json")));
            String json = "", line;
            while ((line = br.readLine()) != null) {
                json += line;
            }

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Type type = new TypeToken<List<Category>>() {
            }.getType();
            mCategoryData = gson.fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onCocktailClick(Cocktail item, LinearLayout mIngredients, int position) {
        if(mIngredients.getVisibility() == View.VISIBLE) {
            mIngredients.setVisibility(View.GONE);
        } else {
            CocktailTask ct = new CocktailTask(mIngredients);
            ct.execute(item.idDrink);
            mIngredients.setVisibility(View.VISIBLE);
            mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
        }
    }

    @Override
    public void onCategoryClick(Category item) {
            Log.w("CA", item.name);
            mCocktailData =  new ArrayList<>(Arrays.asList(item.cocktails));
            mAdapterCocktails = new CocktailAdapter(this, mCocktailData , this);
            mRecyclerView.setAdapter(mAdapterCocktails);
            mAdapterCocktails.notifyDataSetChanged();
    }


    public class Category {
        Cocktail [] cocktails;
        String name;
    }
    public class Cocktail {
        private String strDrink;
        private String strDrinkThumb;
        private String idDrink;

        Cocktail(String id, String drink, String thumb) {
            this.idDrink = id;
            this.strDrink= drink;
            this.strDrinkThumb= thumb;
        }

        /**
         * Gets the title of the sport.
         *
         * @return The title of the sport.
         */
        String getTitle() {
            return strDrink;
        }

        /**
         * Gets the info about the sport.
         *
         * @return The info about the sport.
         */
        String getInfo() {
            return strDrinkThumb;
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
        private ArrayList<Category> mCategoryData;
        private Context mContext;
        private final OnCategoryClickListener listener;

        CategoryAdapter(Context context, ArrayList<Category> categoryData, OnCategoryClickListener listener) {
            this.mCategoryData = categoryData;
            this.mContext = context;
            this.listener = listener;
        }

        @Override
        public CategoryAdapter.ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.list_item, parent, false));
        }

        /**
         * Required method that binds the data to the viewholder.
         *
         * @param holder The viewholder into which the data should be put.
         * @param position The adapter position.
         */
        @Override
        public void onBindViewHolder(CategoryAdapter.ViewHolder holder,
                                     int position) {
            // Get current sport.
            Category currentCategory = mCategoryData.get(position);

            // Populate the textviews with data.
            holder.bindTo(currentCategory);
        }

        /**
         * Required method for determining the size of the data set.
         *
         * @return Size of the data set.
         */
        @Override
        public int getItemCount() {
            return mCategoryData.size();
        }


        /**
         * ViewHolder class that represents each row of data in the RecyclerView.
         */
        class ViewHolder extends RecyclerView.ViewHolder {

            // Member Variables for the TextViews
            private TextView mTitleText;
            private TextView mInfoText;
            private ImageView mImage;
            /**
             * Constructor for the ViewHolder, used in onCreateViewHolder().
             *
             * @param itemView The rootview of the list_item.xml layout file.
             */
            ViewHolder(View itemView) {
                super(itemView);

                // Initialize the views.
                mTitleText = itemView.findViewById(R.id.title);
                mInfoText = itemView.findViewById(R.id.subTitle);
                mImage = itemView.findViewById(R.id.ivCocktail);
            }

            void bindTo(final Category currentCategory){
                // Populate the textviews with data.
                mTitleText.setText(currentCategory.name);
                mInfoText.setText("Cocktails: " + currentCategory.cocktails.length);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onCategoryClick(currentCategory);
                    }
                });
                try {
                    Bitmap bm = BitmapFactory.decodeStream(getAssets().open("pictures/wpxpvu1439905379.jpg"));
                    mImage.setImageBitmap(bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.ViewHolder>{
        private ArrayList<Cocktail> mCocktailData;
        private Context mContext;
        private final OnCocktailClickListener listener;

        CocktailAdapter(Context context, ArrayList<Cocktail> categoryData, OnCocktailClickListener listener) {
            this.mCocktailData = categoryData;
            this.mContext = context;
            this.listener = listener;
        }

        @Override
        public CocktailAdapter.ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.list_item, parent, false));
        }

        /**
         * Required method that binds the data to the viewholder.
         *
         * @param holder The viewholder into which the data should be put.
         * @param position The adapter position.
         */
        @Override
        public void onBindViewHolder(CocktailAdapter.ViewHolder holder,
                                     int position) {
            // Get current sport.
            Cocktail currentCocktail = mCocktailData.get(position);

            // Populate the textviews with data.
            holder.bindTo(currentCocktail, position);

        }

        /**
         * Required method for determining the size of the data set.
         *
         * @return Size of the data set.
         */
        @Override
        public int getItemCount() {
            return mCocktailData.size();
        }


        /**
         * ViewHolder class that represents each row of data in the RecyclerView.
         */
        class ViewHolder extends RecyclerView.ViewHolder {

            // Member Variables for the TextViews
            private TextView mTitleText;
            private TextView mInfoText;
            private LinearLayout mIngredients;
            private ImageView mThumb;

            /**
             * Constructor for the ViewHolder, used in onCreateViewHolder().
             *
             * @param itemView The rootview of the list_item.xml layout file.
             */
            ViewHolder(View itemView) {
                super(itemView);

                // Initialize the views.
                mIngredients = itemView.findViewById(R.id.tvIngredients);
                mTitleText = itemView.findViewById(R.id.title);
                mInfoText = itemView.findViewById(R.id.subTitle);
                mThumb = itemView.findViewById(R.id.ivCocktail);
            }

            void bindTo(final Cocktail currentCocktail, final int position){
                // Populate the textviews with data.
                RequestQueue queue = Volley.newRequestQueue(CocktailActivity.this);
                String url = currentCocktail.strDrinkThumb;
                ImageRequest imageRequest = new ImageRequest(
                        url, new Response.Listener<Bitmap>() {

                    @Override
                    public void onResponse(Bitmap response) {
                        mThumb.setImageBitmap(response);
                    }
                },0,0,null,null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mThumb.setImageResource(R.drawable.ic_glass);
                        Log.w("VOLLEY", error.getMessage());
                    }
                });
                queue.add(imageRequest);
                mTitleText.setText(currentCocktail.strDrink);
                mInfoText.setText("Cocktail: " + currentCocktail.idDrink);
                mIngredients.setVisibility(View.GONE);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onCocktailClick(currentCocktail, mIngredients, position);
                    }
                });

            }
        }
    }
}
