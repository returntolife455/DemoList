package com.example.asmdemo;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author: hejiajun02@lizhi.fm
 * @date: 2022/5/20
 * des:
 */
public class AddToastVisitor extends ClassVisitor {
    public AddToastVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

        MethodVisitor methodVisitor=super.visitMethod(access, name, descriptor, signature, exceptions);

        if("onCreate".equals(name)){

            return new MethodVisitor(api,methodVisitor) {

                @Override
                public void visitInsn(int opcode) {

                    if (opcode == Opcodes.ATHROW || (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
                        super.visitVarInsn(Opcodes.ALOAD,0);
                        super.visitLdcInsn("Hello world!");
                        super.visitInsn(Opcodes.ICONST_0);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC, "android/widget/Toast", "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;", false);
                        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "android/widget/Toast", "show", "()V", false);
                    }


                    super.visitInsn(opcode);
                }
            };

        }



        return methodVisitor;
    }
}
