package com.jester.marvel.data.repository.character.query

import com.jester.marvel.Result
import com.jester.marvel.data.repository.character.model.CharacterDataEntity
import java.lang.Exception
import java.util.*
import javax.inject.Inject

/**
 * Created by Héctor on 17/10/2017.
 */
class GetCharacterListQueryCache @Inject constructor() : GetCharacterListQuery {


    override fun queryAll(parameters: HashMap<String, *>?, queryable: Any?): Result<Collection<Any>, Exception> {

        val cache = queryable as HashMap<String, CharacterDataEntity>
        val queryResult = ArrayList<CharacterDataEntity>()
        for ((_, key) in cache) {
            queryResult.add(key)
        }
        val offset = parameters?.get(GetCharacterListQuery.Parameters.OFFSET) as Int


        return if (queryResult.isNotEmpty() && queryResult.size >= offset) {
            Result.of { queryResult }
        } else Result.Failure()
    }

}