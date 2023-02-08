package com.example.asmdemo.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 2022/5/20
 * des:
 */
class AddToastVisitor(api: Int, classVisitor: ClassVisitor?) : ClassVisitor(api, classVisitor) {

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if ("onCreate" == name) {

            object : AdviceAdapter(api, methodVisitor,access,name,descriptor) {

                override fun onMethodExit(opcode: Int) {
                    super.onMethodExit(opcode)
                    super.visitVarInsn(Opcodes.ALOAD, 0)
                    super.visitLdcInsn("Hello world!")
                    super.visitInsn(Opcodes.ICONST_1)
                    super.visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        "android/widget/Toast",
                        "makeText",
                        "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;",
                        false
                    )
                    super.visitMethodInsn(
                        Opcodes.INVOKEVIRTUAL,
                        "android/widget/Toast",
                        "show",
                        "()V",
                        false
                    )
                }

            }
        } else methodVisitor
    }
}