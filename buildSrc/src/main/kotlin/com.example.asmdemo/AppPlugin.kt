package com.example.asmdemo

import com.android.build.gradle.AppExtension
import com.example.asmdemo.transform.AddToastTransform
import com.example.asmdemo.transform.MethodCostTimeTransform
import com.example.asmdemo.transform.MethodReplaceTransform
import com.example.asmdemo.transform.ThreadReplaceTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *@author: hejiajun02@lizhi.fm
 *@date: 2022/10/12
 *des:
 */
class AppPlugin : Plugin<Project> {


    override fun apply(project: Project) {

        when {
            project.plugins.hasPlugin("com.android.application") ||
                    project.plugins.hasPlugin("com.android.dynamic-feature") -> {


                (project.extensions.getByName("android") as? AppExtension).let { androidExt ->
                    androidExt?.registerTransform(ThreadReplaceTransform())
                    androidExt?.registerTransform(AddToastTransform())
                    androidExt?.registerTransform(MethodReplaceTransform())
                    androidExt?.registerTransform(MethodCostTimeTransform())
                }

            }
        }


    }

}