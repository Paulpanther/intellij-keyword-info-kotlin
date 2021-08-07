package com.paulmethfessel.kik

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

data class Keyword(val name: String, val elementType: String, val parentType: String?)

class Keywords {
    private val values: List<Keyword>

    init {
        val keywordJson = javaClass.classLoader.getResource("keywords.json")?.readText()
        try {
            values = Gson().fromJson(keywordJson, object : TypeToken<List<Keyword>>() {}.type)
        } catch (e: JsonSyntaxException) {
            // TODO handle
            throw e
        }
    }

    fun findKeyword(element: PsiElement): Keyword? {
        return values.find {
            val elementType = element.elementType?.toString()
            val parentType = element.parent.elementType?.toString()
            it.elementType == elementType && (it.parentType == null || it.parentType == parentType)
        }
    }
}
