package com.jester.marvel.data.repository.character

import com.jester.marvel.Result
import com.jester.marvel.data.repository.Repository
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import com.jester.marvel.data.repository.character.model.mapToCharacter
import com.jester.marvel.data.repository.character.model.mapToCharacterDataEntity
import com.jester.marvel.data.repository.character.query.GetCharacterListQuery
import com.jester.marvel.data.repository.character.query.GetFavCharactersQuery
import com.jester.marvel.model.character.Character
import com.jester.marvel.repository.CharacterRepository
import javax.inject.Inject

/**
 * Created by Héctor on 10/10/2017.
 */
class CharacterDataRepository @Inject constructor(characterApiDataSource: CharacterApiDataSource,
                                                  characterCacheDataStore: CharacterCacheDataStore,
                                                  characterRealmDataSource: CharacterRealmDataSource)
    : CharacterRepository, Repository<String, CharacterDataEntity>() {



    init {
        readableDataSources.add(characterRealmDataSource)
        readableDataSources.add(characterApiDataSource)
        //cacheDataSources.add(characterCacheDataStore)
        writableDataSources.add(characterRealmDataSource)
    }

    override fun getCharacters(offset: Int): Result<List<Character>, Exception> {

        val params = HashMap<String, Any>()
        params.put(GetCharacterListQuery.OFFSET, offset)
        val result = queryAll(GetCharacterListQuery::class.java, params)
        return result.map { it.map { it.mapToCharacter() } }
    }

    override fun getCharacterById(id: String): Result<Character, Exception> {

        val result = getByKey(id)
        return result.map { it.mapToCharacter() }
    }

    override fun saveCharacter(character: Character): Result<Character, Exception> {

        val result = addOrUpdate(character.mapToCharacterDataEntity())
        return result.map { it.mapToCharacter() }
    }

    override fun removeCharacter(id: String): Result<Unit, Exception> {

        val result = deleteByKey(id)
        return result
    }

    override fun getFavCharacters(): Result<List<Character>, Exception> {

        val params = HashMap<String, Any>()
        val result = queryAll(GetFavCharactersQuery::class.java,params)
        return result.map { it.map { it.mapToCharacter() }

        }
    }
}