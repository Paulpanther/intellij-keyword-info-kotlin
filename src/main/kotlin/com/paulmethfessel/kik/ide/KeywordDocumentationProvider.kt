package com.paulmethfessel.kik.ide

import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.idea.KotlinLanguage

class KeywordDocumentationProvider: DocumentationProvider {
    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        val keyword = service<KeywordService>().keywords.findKeyword(element) ?: return null
        return keyword.name
    }

    override fun getCustomDocumentationElement(editor: Editor, file: PsiFile, contextElement: PsiElement?, targetOffset: Int): PsiElement? {
        if (contextElement != null && contextElement.language == KotlinLanguage.INSTANCE) {
            return contextElement
        }
        return null
    }
}
