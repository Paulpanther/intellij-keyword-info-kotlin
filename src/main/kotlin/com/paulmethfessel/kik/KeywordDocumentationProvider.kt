package com.paulmethfessel.kik

import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.idea.KotlinLanguage

class KeywordDocumentationProvider: DocumentationProvider {
    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        val keyword = service<KeywordService>().findKeyword(element) ?: return null
        val b = StringBuilder()
        b.append(DocumentationMarkup.DEFINITION_START)
        b.append(keyword.name)
        b.append(DocumentationMarkup.DEFINITION_END)

        b.append(DocumentationMarkup.CONTENT_START)
        b.append(keyword.description)
        b.append(DocumentationMarkup.CONTENT_END)

        if (keyword.example != null) {
            b.append(DocumentationMarkup.CONTENT_START)
            b.append("Example: ")
            b.append("<pre><code>${keyword.example}</code></pre>")
            b.append(DocumentationMarkup.CONTENT_END)
        }
        return b.toString()
    }

    override fun getCustomDocumentationElement(editor: Editor, file: PsiFile, contextElement: PsiElement?, targetOffset: Int): PsiElement? {
        if (contextElement != null &&
            contextElement.language == KotlinLanguage.INSTANCE &&
            service<KeywordService>().findKeyword(contextElement) != null) {
            return contextElement
        }
        return null
    }
}
