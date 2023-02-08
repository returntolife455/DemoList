package com.example.asmdemo.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 2022/10/10
 * des:
 */
class ReplaceThreadVisitor(api: Int, classVisitor: ClassVisitor?) :
    ClassVisitor(api, classVisitor) {
    private var className: String? = null

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        return ReplaceThreadAdapter(api, methodVisitor, access, name, descriptor, className)
    }

    private class ReplaceThreadAdapter
    /**
     * Constructs a new [AdviceAdapter].
     *
     * @param api           the ASM API version implemented by this visitor. Must be one of the `ASM`*x* values in [Opcodes].
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see [Opcodes]).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see [Type]).
     */(
        api: Int,
        methodVisitor: MethodVisitor?,
        access: Int,
        methodName: String?,
        descriptor: String?,
        private val className: String?
    ) : AdviceAdapter(api, methodVisitor, access, methodName, descriptor) {
        // 标识是否遇到了 new 指令
        private var find = false
        override fun visitTypeInsn(opcode: Int, s: String?) {
            if (opcode == NEW && "java/lang/Thread" == s) {
                find = true
                mv.visitTypeInsn(NEW, targetClassName)
                return
            }
            super.visitTypeInsn(opcode, s)
        }

        override fun visitMethodInsn(
            opcodeAndSource: Int,
            owner: String?,
            name: String?,
            descriptor: String?,
            isInterface: Boolean
        ) {
            //需要排查CustomThread自己
            if ("java/lang/Thread" == owner && className != targetClassName && opcodeAndSource == INVOKESPECIAL && find) {
                find = false
                mv.visitMethodInsn(opcodeAndSource, targetClassName, name, descriptor, isInterface)
                return
            }
            super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        }
    }

    companion object {
        private const val targetClassName =
            "com/returntolife/jjcode/mydemolist/demo/function/asmhook/CustomThread"
    }
}