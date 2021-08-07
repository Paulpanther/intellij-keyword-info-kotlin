package com.paulmethfessel.kik.ide

import com.intellij.openapi.components.Service
import com.paulmethfessel.kik.Keywords

@Service
class KeywordService {
    val keywords = Keywords()
}
