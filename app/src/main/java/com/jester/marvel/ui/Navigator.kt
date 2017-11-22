package com.jester.marvel.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.jester.marvel.ui.characterDetail.CharacterDetailActivity
import com.jester.marvel.ui.comicList.ComicListActivity
import com.jester.marvel.ui.favCharacterList.FavCharacterListActivity
import javax.inject.Inject

/**
 * Created by Héctor on 16/10/2017.
 */
class Navigator @Inject constructor() {

    fun navigateToCharacterDetailActivity(context: Context, characterId: String){

        val intent = CharacterDetailActivity.getIntent(context)
        intent.putExtra(CharacterDetailActivity.ID, characterId)
        context.startActivity(intent)
    }

    fun navigateToFavCharacters(context: Context) {

        val intent = FavCharacterListActivity.getIntent(context)
        context.startActivity(intent)
    }

    fun shareSuperHeroInfo(context: Context, bitmap: Bitmap){

        val pathofBmp = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "title", null)

        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.type = "image/*"
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Este super heroe es mejor que superman")
        sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse(pathofBmp))
        context.startActivity(sharingIntent)
    }

    fun navigateToComicsList(context: Context) {

        val intent = ComicListActivity.getIntent(context)
        context.startActivity(intent)
    }
}