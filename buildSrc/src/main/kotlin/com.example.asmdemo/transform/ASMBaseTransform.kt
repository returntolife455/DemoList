package com.example.asmdemo.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import java.io.*
import java.util.HashSet
/**
 *@author: hejiajun02@lizhi.fm
 *@date: 2022/10/12
 *des:
 */
abstract class ASMBaseTransform : Transform() {

    private val TAG="ASMBaseTransform"

    abstract fun toTransform(files: HashSet<File>)

    override fun getInputTypes(): Set<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope>? {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }



    @Throws(TransformException::class, InterruptedException::class, IOException::class)
    override fun transform(transformInvocation: TransformInvocation) {
        println("$TAG --------------------${name} transform 开始-------------------")

        try {
            // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
            for (input in transformInvocation.inputs) {
                // 遍历文件夹
                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                for (directoryInput in input.directoryInputs) {
                    if (directoryInput.file.length() == 0L) {
                        break
                    }

                    //收集需要修改的类文件
                    val files = handlerDir(directoryInput.file, HashSet())
                    //真正修改的地方
                    toTransform(files)


                    // 获取输出目录
                    val dest = transformInvocation.outputProvider.getContentLocation(
                        directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY
                    )
                    // 将input的目录复制到output指定目录
                    com.android.utils.FileUtils.copyDirectory(directoryInput.file, dest)
                }

                // 遍历jar
                for (jarInput in input.jarInputs) {
                    val dest = transformInvocation.outputProvider.getContentLocation(
                        jarInput.file.absolutePath,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR
                    )
                    //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
                    com.android.utils.FileUtils.copyFile(jarInput.file, dest)
                }
            }
        }catch (e:Exception){
            println("---------------------${name} transform Exception=${e}-------------------")
        }

        println("---------------------${name} transform 结束-------------------")
    }



    open fun filter(file: File):Boolean{
        return file.path.endsWith(".class")
    }

    private fun handlerDir(file: File, hashSet: HashSet<File>): HashSet<File> {
        if (file.isDirectory) {
            val child = file.listFiles()
            if (child == null || child.isEmpty()) {
                return hashSet
            }

            for (file1 in child) {
                if (file1.isDirectory) {
                    handlerDir(file1, hashSet)
                } else if (filter(file1)) {
                    println("$TAG 过滤匹配到的文件 :  ${file1.absolutePath}")
                    hashSet.add(file1)
                }
            }
        } else if (filter(file)) {
            hashSet.add(file)
        }
        return hashSet
    }

}