package com.example.asmdemo.transform

import com.example.asmdemo.visitor.MethodReplaceVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *@author: hejiajun02@lizhi.fm
 *@date: 2022/10/12
 *des:
 */
class MethodReplaceTransform: ASMBaseTransform() {

    override fun toTransform(files: HashSet<File>) {
        files.forEach {
            try {
                val classReader = ClassReader(it.readBytes())
                val classNode = ClassNode(Opcodes.ASM9)
                classReader.accept(classNode, 0)

                var methodNode:MethodNode?=null
                for (i in classNode.methods.indices) {
                    val tempMethodNode = classNode.methods[i]
                    if ("printDefaultText" == tempMethodNode.name && "()V" == tempMethodNode.desc) {
                        methodNode = tempMethodNode
                        break
                    }
                }

                methodNode?.let {
                    classNode.methods.remove(methodNode)
                    val methodVisitor = MethodNode(
                        Opcodes.ACC_PRIVATE,
                        "printDefaultText",
                        "()V",
                        null,
                        null
                    )
                    classNode.methods.add(methodVisitor)

                    methodVisitor.visitCode()
                    methodVisitor.visitLdcInsn("printDefaultText2");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/tools/jj/tools/utils/LogUtil", "d", "(Ljava/lang/Object;)V", false);
                    methodVisitor.visitIntInsn(Opcodes.BIPUSH, 100);
                    methodVisitor.visitVarInsn(Opcodes.ISTORE, 1);
                    methodVisitor.visitIntInsn(Opcodes.SIPUSH, 200);
                    methodVisitor.visitVarInsn(Opcodes.ISTORE, 2);
                    methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
                    methodVisitor.visitInsn(Opcodes.DUP);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                    methodVisitor.visitLdcInsn("reslut=");
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
                    methodVisitor.visitVarInsn(Opcodes.ILOAD, 2);
                    methodVisitor.visitInsn(Opcodes.IADD);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "com/tools/jj/tools/utils/LogUtil", "d", "(Ljava/lang/Object;)V", false);
                    methodVisitor.visitInsn(Opcodes.RETURN);
                    methodVisitor.visitMaxs(3, 3);
                    methodVisitor.visitEnd()
                }

                val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                classNode.accept(classWriter)

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
        return "MethodReplaceTransform"
    }

    override fun filter(file: File): Boolean {
        return file.name.endsWith(".class") && file.path.contains("com/returntolife/jjcode/mydemolist/demo/function/asmhook/AsmHookActivity")

    }
}