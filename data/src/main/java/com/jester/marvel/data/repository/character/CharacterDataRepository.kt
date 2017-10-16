package com.jester.marvel.data.repository.character

import com.jester.marvel.Result
import com.jester.marvel.data.repository.Repository
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.character.model.mapToCharacter
import com.jester.marvel.data.repository.character.query.GetCharacterListQuery
import com.jester.marvel.model.character.Character
import com.jester.marvel.repository.CharacterRepository
import javax.inject.Inject

/**
 * Created by Héctor on 10/10/2017.
 */
class CharacterDataRepository @Inject constructor(characterApiDataSource: CharacterApiDataSource)
    : CharacterRepository, Repository<String, CharacterDataEntity>() {
    init {
        readableDataSources.add(characterApiDataSource)
    }

    override fun getCharacters(offset: Int): Result<List<Character>, Exception> {

        val params = HashMap<String, Any>()
        params.put(GetCharacterListQuery.OFFSET, offset)
        val result = queryAll(GetCharacterListQuery::class.java, params)
        return result.map { it.map { it.mapToCharacter() } }    }
}