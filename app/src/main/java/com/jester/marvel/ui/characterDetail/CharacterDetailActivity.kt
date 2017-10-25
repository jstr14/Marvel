package com.jester.marvel.ui.characterDetail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jester.marvel.R
import com.jester.marvel.ui.HorizontalSpaceItemDecorator
import com.jester.marvel.ui.ProgressLoader
import com.jester.marvel.ui.base.BaseActivity
import com.jester.marvel.ui.characterDetail.renderers.ComicRenderer
import com.jester.marvel.ui.model.CharacterViewEntity
import com.jester.marvel.ui.model.ComicViewEntity
import com.jester.marvel.ui.setPrefixTextBold
import com.pedrogomez.renderers.RendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import kotlinx.android.synthetic.main.activity_character_detail.*
import kotlinx.android.synthetic.main.character_info.*
import kotlinx.android.synthetic.main.progress_loader.view.*
import javax.inject.Inject

class CharacterDetailActivity : BaseActivity(), CharacterDetailView {


    companion object {

        const val ID = "ID"
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, CharacterDetailActivity::class.java)
        }
    }

    @Inject lateinit var presenter: CharacterDetailPresenter
    @Inject lateinit var comicRenderer: ComicRenderer
    @Inject lateinit var progressLoader: ProgressLoader
    private val HORIZONTAL_ITEM_SPACE = 16

    lateinit var adapter: RendererAdapter<Any>


    override fun onRequestLayout(): Int {
        return R.layout.activity_character_detail
    }

    override fun onViewLoaded() {

        progressLoader.addImagesToProgressLoader(loading.loading_view, this)
        setToolbar()
        setRecyclerView()
        val characterID = intent.extras.getString(ID)

        presenter.onStart(characterID)
    }

    override fun showCharacterInfo(characterViewEntity: CharacterViewEntity) {

        val properPath = characterViewEntity.image.path + "/" + this.getString(R.string.square) + "." + characterViewEntity.image.extension
        loadImageAndChangeIconColor(properPath)

        characterName.setPrefixTextBold(getString(R.string.name_detail),characterViewEntity.name,getString(R.string.character_info_option_separator))
        description.setPrefixTextBold(getString(R.string.description_detail),characterViewEntity.description, getString(R.string.character_info_option_separator))

        if(characterViewEntity.comics.isEmpty()){
            hideComics()
        } else {
            comicTitle.text = getString(R.string.comics_title)
            adapter.collection.addAll(characterViewEntity.comics)
            adapter.notifyDataSetChanged()
        }



    }

    fun loadImageAndChangeIconColor(url: String){

        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                        characterImage.setImageBitmap(resource)
                        getPaletteFromBitmap(resource)
                    }
                })
    }

    fun getPaletteFromBitmap(bitmap: Bitmap){

        val palette = Palette.from(bitmap).generate()

        val swatch = palette.darkMutedSwatch
        if (swatch != null) {
            val navigationIcon = toolbar.navigationIcon
            navigationIcon?.setColorFilter(swatch.bodyTextColor, PorterDuff.Mode.SRC_ATOP)


        }
    }

    override fun hideLoader() {
        loading.visibility = View.GONE
        progressLoader.pauseAnimation(loading.loading_view)
        characterInfo.visibility = View.VISIBLE
        collapsing_toolbar_character_detail.visibility = View.VISIBLE

    }

    private fun hideComics(){
        comicGroup.visibility = View.GONE
    }

    private fun setToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)

        }
    }

        private fun setRecyclerView() {

            val horizontalLayoutManagaer = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            comicsList.layoutManager = horizontalLayoutManagaer

            adapter = RendererBuilder.create<Any>()
                    .bind(ComicViewEntity::class.java, comicRenderer)
                    .build()
                    .into(comicsList)
            comicsList.addItemDecoration(HorizontalSpaceItemDecorator(HORIZONTAL_ITEM_SPACE,this,true))


        }



}
