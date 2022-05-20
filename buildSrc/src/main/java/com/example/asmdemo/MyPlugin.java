package com.example.asmdemo;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 2022/5/20
 * des:
 */
public class MyPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        if (project.getPlugins().hasPlugin("com.android.application")) {
            AppExtension extension = (AppExtension) project.getExtensions().getByName("android");
            extension.registerTransform(new CommonTransform(project));
        }


    }
}
