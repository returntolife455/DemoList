package com.example.asmdemo;

import org.gradle.api.Project;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 2022/5/20
 * des:
 */
public class MyInjectByJavassit {

    public static void injectToast(String absolutePath, Project project) {

        System.out.println("MyInjectByJavassit absolutePath="+absolutePath);
        try {
            ClassReader classReader=new ClassReader(new FileInputStream(absolutePath));
            ClassWriter classWriter=new ClassWriter(ClassWriter.COMPUTE_FRAMES);

            ClassVisitor classVisitor=new AddToastVisitor(Opcodes.ASM9,classWriter);

            classReader.accept(classVisitor,0);

            byte[] bytes=classWriter.toByteArray();

            FileOutputStream fs=new FileOutputStream(absolutePath);
            fs.write(bytes);
            fs.flush();
            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
