package com.example.asmdemo.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 2022/5/20
 * des:
 */
class MethodCostTimeVisitor(api: Int, classVisitor: ClassVisitor?) : ClassVisitor(api, classVisitor) {

    private var className:String?=""

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        className = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if (className?.contains("returntolife")==true) {

            object : AdviceAdapter(api, methodVisitor,access,name,descriptor) {

                var startTimeIndex = 0 //startTime在局部变量表的位置
                var endTimeIndex   = 0 //endTime在局部变量表的位置

                override fun onMethodEnter() {
                    super.onMethodEnter()
                    //调用System.currentTimeMillis方法，调用完后将返回值推到栈顶
                    invokeStatic(Type.getType("Ljava/lang/System;"),Method("currentTimeMillis", "()J"))
                    //获取当前局部变量表可插入的位置，不能乱传，不然会影响下一个值的索引获取，这里你传DOUBLE_TYPE或者LONG_TYPE,都是一样的
                    startTimeIndex = newLocal(Type.LONG_TYPE)
                    //把栈顶的值（也就是System.currentTimeMillis的返回值）保存到局部变量表startTimeIndex位置
                    storeLocal(startTimeIndex)

                }

                override fun onMethodExit(opcode: Int) {
                    super.onMethodExit(opcode)
                    //调用System.currentTimeMillis方法，调用完后将返回值推到栈顶
                    invokeStatic(Type.getType("Ljava/lang/System;"),Method("currentTimeMillis", "()J"))
                    //获取当前局部变量表可插入的位置
                    endTimeIndex = newLocal(Type.LONG_TYPE)
                    //把栈顶的值（也就是System.currentTimeMillis的返回值）保存到局部变量表endTimeIndex位置
                    storeLocal(endTimeIndex)

                    super.visitTypeInsn(NEW, "java/lang/StringBuilder")
                    super.visitInsn(DUP)
                    super.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
                    super.visitLdcInsn("cost time=")
                    super.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                    //把局部变量表中的endTimeIndex位置元素，压入栈顶
                    loadLocal(endTimeIndex)
                    //把局部变量表中的startTimeIndex位置元素，压入栈顶
                    loadLocal(startTimeIndex)
                    //将栈顶两元素进行相减，把结果再压入栈顶
                    math(SUB, Type.LONG_TYPE)
                    super.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false)
                    super.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
                    super.visitMethodInsn(INVOKESTATIC, "com/tools/jj/tools/utils/LogUtil", "d", "(Ljava/lang/Object;)V", false)
                }

            }
        } else methodVisitor
    }
}