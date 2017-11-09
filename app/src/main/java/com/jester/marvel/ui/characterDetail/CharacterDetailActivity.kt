package com.jester.marvel.ui.characterDetail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jester.marvel.R
import com.jester.marvel.ui.HorizontalSpaceItemDecorator
import com.jester.marvel.ui.ProgressLoader
import com.jester.marvel.ui.base.BaseActivity
import com.jester.marvel.ui.characterDetail.renderers.ComicRenderer
import com.jester.marvel.ui.characterDetail.renderers.EventRenderer
import com.jester.marvel.ui.characterDetail.renderers.StoryRenderer
import com.jester.marvel.ui.model.CharacterViewEntity
import com.jester.marvel.ui.model.ComicViewEntity
import com.jester.marvel.ui.model.EventViewEntity
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
    @Inject lateinit var eventRenderer: EventRenderer
    @Inject lateinit var storyRenderer: StoryRenderer
    @Inject lateinit var progressLoader: ProgressLoader
    private val HORIZONTAL_ITEM_SPACE = 16

    lateinit var comicAdapter: RendererAdapter<Any>
    lateinit var eventsAdapter: RendererAdapter<Any>
    lateinit var storyAdapter: RendererAdapter<Any>


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

        characterInfo.visibility = View.VISIBLE
        collapsing_toolbar_character_detail.visibility = View.VISIBLE

        val properPath = characterViewEntity.image.path + "/" + this.getString(R.string.square) + "." + characterViewEntity.image.extension
        loadImageAndChangeIconColor(properPath)

        characterName.setPrefixTextBold(getString(R.string.name_detail),characterViewEntity.name,getString(R.string.character_info_option_separator))
        description.setPrefixTextBold(getString(R.string.description_detail),characterViewEntity.description, getString(R.string.character_info_option_separator))


        showComics(characterViewEntity)
        showEvents(characterViewEntity)
        //showStories(characterViewEntity)

    }

    private fun showComics(characterViewEntity: CharacterViewEntity){

        if(characterViewEntity.comics.isEmpty()){
            hideList(comicGroup)
        } else {
            comicTitle.text = getString(R.string.comics_title)
            comicAdapter.collection.addAll(characterViewEntity.comics)
            comicAdapter.notifyDataSetChanged()
        }
    }

    private fun showEvents(characterViewEntity: CharacterViewEntity){

        if(characterViewEntity.events.isEmpty()){
            hideList(eventGroup)
        } else {
            eventTitle.text = getString(R.string.event_title)
            eventsAdapter.collection.addAll(characterViewEntity.events)
            eventsAdapter.notifyDataSetChanged()
        }
    }

    private fun showStories(characterViewEntity: CharacterViewEntity){

        if(characterViewEntity.stories.isEmpty()){
            hideList(storyGroup)
        } else {
            storyTitle.text = getString(R.string.story_title)
            storyAdapter.collection.addAll(characterViewEntity.stories)
            storyAdapter.notifyDataSetChanged()
        }
    }

    private fun loadImageAndChangeIconColor(url: String){

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

    private fun getPaletteFromBitmap(bitmap: Bitmap){

        val palette = Palette.from(bitmap).generate()

        val vibrantSwatch = palette.vibrantSwatch
        val mutedSwatch = palette.mutedSwatch

        if (vibrantSwatch != null && mutedSwatch != null) {
            collapsing_toolbar.setContentScrimColor(vibrantSwatch.rgb)
            val navigationIcon = toolbar.navigationIcon
            navigationIcon?.setColorFilter(mutedSwatch.titleTextColor, PorterDuff.Mode.SRC_ATOP)


        }
    }

    override fun hideLoader() {
        loading.visibility = View.GONE
        progressLoader.pauseAnimation(loading.loading_view)

    }

    private fun hideList(view: View){
        view.visibility = View.GONE
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

            setLayoutManagerAndAddSpaceDecorator(comicsList)
            setLayoutManagerAndAddSpaceDecorator(eventsList)
            //setLayoutManagerAndAddSpaceDecorator(storiesList)

            comicAdapter = RendererBuilder.create<Any>()
                    .bind(ComicViewEntity::class.java, comicRenderer)
                    .build()
                    .into(comicsList)

            eventsAdapter = RendererBuilder.create<Any>()
                    .bind(EventViewEntity::class.java, eventRenderer)
                    .build()
                    .into(eventsList)

//            storyAdapter = RendererBuilder.create<Any>()
//                    .bind(StoryViewEntity::class.java, storyRenderer)
//                    .build()
//                    .into(storiesList)


        }

    private fun newLinearLayoutManagerHorizontal(): LinearLayoutManager {

        return LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setLayoutManagerAndAddSpaceDecorator(recyclerView: RecyclerView){

        recyclerView.layoutManager = newLinearLayoutManagerHorizontal()
        recyclerView.addItemDecoration(HorizontalSpaceItemDecorator(HORIZONTAL_ITEM_SPACE,this,true))
    }




}
