package com.github.paulpanther.intellijkeywordsinfokotlin.services

import com.github.paulpanther.intellijkeywordsinfokotlin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
