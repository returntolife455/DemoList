package com.example.asmdemo.transform

import com.example.asmdemo.visitor.MethodCostTimeVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *@author: hejiajun02@lizhi.fm
 *@date: 2022/10/12
 *des:
 */
class MethodCostTimeTransform: ASMBaseTransform() {

    override fun toTransform(files: HashSet<File>) {
        files.forEach {
            try {
                val classReader = ClassReader(it.readBytes())
                val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES)
                val customVisitor: ClassVisitor = MethodCostTimeVisitor(Opcodes.ASM9, classWriter)
                val options = ClassReader.SKIP_FRAMES or ClassReader.SKIP_DEBUG
                classReader.accept(customVisitor, options)

                val bytes = classWriter.toByteArray()

                val fs = FileOutputStream(it.absoluteFile)
                fs.write(bytes)
                fs.flush()
                fs.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun getName(): String {
        return "MethodCostTimeTransform"
    }

    override fun filter(file: File): Boolean {
        return file.name.endsWith(".class") && file.path.contains("com/returntolife/jjcode/mydemolist/demo/function/asmhook/AsmHookActivity")

    }
}