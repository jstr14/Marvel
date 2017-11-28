package com.jester.marvel.ui

import com.jester.marvel.R

/**
 * Created by Héctor on 24/11/2017.
 */
enum class FormatComic(val format: String) {

    COMIC("Comic") {
        override fun getColor(): Int = R.color.comic_color //TODO: Change color
        override fun getTextColor(): Int = R.color.colorPrimary
    },
    MAGAZINE("Magazine") {
        override fun getColor(): Int = R.color.magazine_color
        override fun getTextColor(): Int = R.color.magazine_text_color

    },
    TRADE_PAPERBACK("Trade Paperback") {
        override fun getColor(): Int = R.color.trade_paperback_color
        override fun getTextColor(): Int = R.color.trade_paperback_text_color

    },
    HARDCOVER("Hardcover") {
        override fun getColor(): Int = R.color.hardcover_color
        override fun getTextColor(): Int = R.color.hardcover_text_color

    },
    DIGEST("Digest") {
        override fun getColor(): Int = R.color.digest_color //TODO: Change color
        override fun getTextColor(): Int = R.color.colorPrimary

    },
    GRAPHIC_NOVEL("Graphic Novel") {
        override fun getColor(): Int = R.color.graphic_novel_color
        override fun getTextColor(): Int = R.color.graphic_novel_text_color

    },
    DIGITAL_COMIC("Digital Comic") {
        override fun getColor(): Int = R.color.digital_comic
        override fun getTextColor(): Int = R.color.digital_comic_text_color

    },
    INFINITE_COMIC("Infinite Comic") {
        override fun getColor(): Int = R.color.infinite_comic
        override fun getTextColor(): Int = R.color.infinite_comic_text_color

    };


    abstract fun getColor(): Int
    abstract fun getTextColor(): Int

}