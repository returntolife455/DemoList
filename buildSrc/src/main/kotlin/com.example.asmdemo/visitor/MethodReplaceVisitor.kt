package com.example.asmdemo.visitor

import org.objectweb.asm.Attribute
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.tree.MethodNode

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 2022/5/20
 * des:
 */
class MethodReplaceVisitor(api: Int, classVisitor: ClassVisitor?) : ClassVisitor(api, classVisitor) {

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {

        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)

        if ("printDefaultText" == name && "()V" == descriptor){
            val methodVisitor2 = object:MethodVisitor(api,null){
                override fun visitCode() {
                    methodVisitor.visitLdcInsn("printDefaultText2");
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/tools/jj/tools/utils/LogUtil", "d", "(Ljava/lang/Object;)V", false);
                    methodVisitor.visitIntInsn(BIPUSH, 100);
                    methodVisitor.visitVarInsn(ISTORE, 1);
                    methodVisitor.visitIntInsn(SIPUSH, 200);
                    methodVisitor.visitVarInsn(ISTORE, 2);
                    methodVisitor.visitTypeInsn(NEW, "java/lang/StringBuilder");
                    methodVisitor.visitInsn(DUP);
                    methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                    methodVisitor.visitLdcInsn("reslut=");
                    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                    methodVisitor.visitVarInsn(ILOAD, 1);
                    methodVisitor.visitVarInsn(ILOAD, 2);
                    methodVisitor.visitInsn(IADD);
                    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
                    methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                    methodVisitor.visitMethodInsn(INVOKESTATIC, "com/tools/jj/tools/utils/LogUtil", "d", "(Ljava/lang/Object;)V", false);
                    methodVisitor.visitInsn(RETURN);
                    methodVisitor.visitMaxs(3, 3);

                }
            }
          return  methodVisitor2
        }else{
            return super.visitMethod(access, name, descriptor, signature, exceptions)
        }
    }
}