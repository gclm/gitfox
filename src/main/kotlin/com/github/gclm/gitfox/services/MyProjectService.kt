package com.github.gclm.gitfox.services

import com.intellij.openapi.project.Project
import com.github.gclm.gitfox.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
