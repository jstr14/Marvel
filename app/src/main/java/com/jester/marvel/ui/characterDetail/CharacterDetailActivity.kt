package com.jester.marvel.ui.characterDetail

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jester.marvel.R
import com.jester.marvel.ui.*
import com.jester.marvel.ui.base.BaseActivity
import com.jester.marvel.ui.behaviours.OptionsMenuBehaviourInCollapsingToolbar
import com.jester.marvel.ui.characterDetail.renderers.ComicRenderer
import com.jester.marvel.ui.characterDetail.renderers.EventRenderer
import com.jester.marvel.ui.model.CharacterViewEntity
import com.jester.marvel.ui.model.ComicViewEntity
import com.jester.marvel.ui.model.EventViewEntity
import com.pedrogomez.renderers.RendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import kotlinx.android.synthetic.main.activity_character_detail.*
import kotlinx.android.synthetic.main.character_info.*
import kotlinx.android.synthetic.main.progress_loader.view.*
import javax.inject.Inject


class CharacterDetailActivity : BaseActivity(), CharacterDetailView, OptionsMenuBehaviourInCollapsingToolbar {

    companion object {

        const val ID = "ID"
        const val HORIZONTAL_ITEM_SPACE = 16

        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, CharacterDetailActivity::class.java)
        }
    }

    @Inject lateinit var presenter: CharacterDetailPresenter
    @Inject lateinit var navigator: Navigator
    @Inject lateinit var comicRenderer: ComicRenderer
    @Inject lateinit var eventRenderer: EventRenderer
    @Inject lateinit var progressLoader: ProgressLoader
    @Inject lateinit var managePermissions: ManagePermissions


    lateinit var comicAdapter: RendererAdapter<Any>
    lateinit var eventsAdapter: RendererAdapter<Any>
    private var isFav = false
    lateinit var characterID: String
    lateinit var characterViewEntity: CharacterViewEntity
    lateinit var bitmap: Bitmap



    override fun onRequestLayout(): Int {
        return R.layout.activity_character_detail
    }

    override fun onViewLoaded() {

        progressLoader.addImagesToProgressLoader(loading.loading_view, this)
        setToolbar()
        setRecyclerView()
        characterID = intent.extras.getString(ID)

        presenter.onStart(characterID)

        fab.setOnClickListener{
            isFav = isFav != true
            presenter.onFabButtonPressed(characterID, isFav)
            val item = toolbar.menu.findItem(R.id.fav_action)
            changeFavIcon(item, isFav)
        }
    }

    override fun showCharacterInfo(characterViewEntity: CharacterViewEntity) {

        this.characterViewEntity = characterViewEntity
        characterInfo.visibility = View.VISIBLE
        collapsing_toolbar_character_detail.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE


        val properPath = characterViewEntity.image.path + "/" + this.getString(R.string.square) + "." + characterViewEntity.image.extension
        loadImageAndChangeIconColor(properPath)

        characterName.setPrefixTextBold(getString(R.string.name_detail), characterViewEntity.name)
        description.setPrefixTextBold(getString(R.string.description_detail), characterViewEntity.description)

        showComics(characterViewEntity)
        showEvents(characterViewEntity)

        changeFavIcon(toolbar.menu.findItem(R.id.fav_action), characterViewEntity.isFav)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share_action -> {
                presenter.onShareButtonPressed()
                true
            }
            R.id.fav_action -> {
                isFav = isFav != true
                presenter.onFabButtonPressed(characterID, isFav)
                changeFavIcon(item, isFav)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun requestPermissionToShareImage() {
        val allPermissionListener = managePermissions.setAllPermissionListener(this, root) {
            navigator.shareSuperHeroInfo(this,bitmap)
        }

        managePermissions.setRequestPermissions(this, mutableListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), allPermissionListener)

    }

    override fun getFavCharacter(): CharacterViewEntity {
        return characterViewEntity
    }

    private fun changeFavIcon(item: MenuItem, isFav: Boolean) {

        if (isFav){
            item.icon = ContextCompat.getDrawable(this, R.drawable.favorite_white_icon)
            fab.setImageResource(R.drawable.favorite_white_icon)
        }
        else{
            item.icon = ContextCompat.getDrawable(this, R.drawable.favorite_border_white_icon)
            fab.setImageResource(R.drawable.favorite_border_white_icon)
        }
    }

    private fun showComics(characterViewEntity: CharacterViewEntity) {

        if (characterViewEntity.comics.isEmpty()) {
            hideList(comicGroup)
        } else {
            comicTitle.text = getString(R.string.comics_title)
            comicAdapter.collection.addAll(characterViewEntity.comics)
            comicAdapter.notifyDataSetChanged()
        }
    }

    private fun showEvents(characterViewEntity: CharacterViewEntity) {

        if (characterViewEntity.events.isEmpty()) {
            hideList(eventGroup)
        } else {
            eventTitle.text = getString(R.string.event_title)
            eventsAdapter.collection.addAll(characterViewEntity.events)
            eventsAdapter.notifyDataSetChanged()
        }
    }

    private fun loadImageAndChangeIconColor(url: String) {

        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                        characterImage.setImageBitmap(resource)
                        bitmap = resource
                    }
                })
    }


    override fun hideLoader() {
        loading.visibility = View.GONE
        progressLoader.pauseAnimation(loading.loading_view)

    }

    private fun hideList(view: View) {
        view.visibility = View.GONE
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)

        }
        collapsing_toolbar_character_detail.addOnOffsetChangedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun setRecyclerView() {

        setLayoutManagerAndAddSpaceDecorator(comicsList)
        setLayoutManagerAndAddSpaceDecorator(eventsList)

        comicAdapter = RendererBuilder.create<Any>()
                .bind(ComicViewEntity::class.java, comicRenderer)
                .build()
                .into(comicsList)

        eventsAdapter = RendererBuilder.create<Any>()
                .bind(EventViewEntity::class.java, eventRenderer)
                .build()
                .into(eventsList)


    }

    private fun newLinearLayoutManagerHorizontal(): LinearLayoutManager {

        return LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setLayoutManagerAndAddSpaceDecorator(recyclerView: RecyclerView) {

        recyclerView.layoutManager = newLinearLayoutManagerHorizontal()
        recyclerView.addItemDecoration(HorizontalSpaceItemDecorator(HORIZONTAL_ITEM_SPACE, this, true))
    }


    override fun showOptionBehaviour() {
        toolbar.menu.findItem(R.id.fav_action).isVisible = true
    }

    override fun hideOptionBehaviour() {
        toolbar.menu.findItem(R.id.fav_action).isVisible = false
    }
}
