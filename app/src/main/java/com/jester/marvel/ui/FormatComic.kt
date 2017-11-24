package com.jester.marvel.ui

import com.jester.marvel.R

/**
 * Created by Héctor on 24/11/2017.
 */
enum class FormatComic(val format: String, val color: Int) {

    COMIC("comic", R.color.comic_color),
    MAGAZINE("magazine",R.color.magazine_color),
    TRADE_PAPERBACK("trade paperback",R.color.trade_paperback_color),
    HARDCOVER("hardcover",R.color.hardcover_color),
    DIGEST("digest",R.color.digest_color),
    GRAPHIC_NOVEL("graphic novel",R.color.graphic_novel_color),
    DIGITAL_COMIC("digital comic",R.color.digital_comic),
    INFINITE_COMIC("infinite_comic",R.color.infinite_comic)

}