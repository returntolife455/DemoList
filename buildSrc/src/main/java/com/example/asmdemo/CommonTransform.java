package com.example.asmdemo;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.println;

import com.android.build.api.transform.Context;
import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;

import org.gradle.api.Project;
import org.gradle.internal.impldep.org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 2022/5/20
 * des:
 */
public class CommonTransform extends Transform {

    private Project project;


    public CommonTransform(Project project){
        this.project=project;
    }



    @Override
    public String getName() {
        return "CommonTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        System.out.println("--------------------transform 开始-------------------");

        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        for (TransformInput input : transformInvocation.getInputs()) {
            // 遍历文件夹
            //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {

                if(directoryInput.getFile().length()==0){
                    break;
                }

                //收集需要修改的类文件
                HashSet<File> files= handlerDir(directoryInput.getFile(),new HashSet<File>());
                for (File file : files) {
                    // 注入代码
                    MyInjectByJavassit.injectToast(file.getAbsolutePath(), project);
                }

                // 获取输出目录
                File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);

                System.out.println("directory output dest:"+dest.getAbsolutePath());
                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.getFile(), dest);
            }

            // 遍历jar
            for(JarInput jarInput : input.getJarInputs()) {
                File dest = transformInvocation.getOutputProvider().getContentLocation(
                        jarInput.getFile().getAbsolutePath(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
                FileUtils.copyFile(jarInput.getFile(), dest);
            }
        }

        System.out.println("---------------------transform 结束-------------------");
    }

    private HashSet<File> handlerDir(File file, HashSet<File> hashSet) {
        if(file.isDirectory()){
            File[] child=file.listFiles();

            if(child==null||child.length==0){
                return hashSet;
            }

            for (File file1 : child) {
                if(file1.isDirectory()){
                    handlerDir(file1,hashSet);
                }else if(file1.getAbsolutePath().contains("MainActivity")){
                    hashSet.add(file1);
                }
            }


        }else if(file.getAbsolutePath().contains("MainActivity")){
            hashSet.add(file);
        }

        return hashSet;
    }


}
