package com.massimoregoli.mp2019.interfaces;

import android.widget.LinearLayout;

import com.massimoregoli.mp2019.myactivities.CocktailActivity;

public interface OnCocktailClickListener {
    void onCocktailClick(CocktailActivity.Cocktail item, LinearLayout mIngredients, int position);

}
