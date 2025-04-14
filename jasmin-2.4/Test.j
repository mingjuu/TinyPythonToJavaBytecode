.class public Test
.super java/lang/Object

.method public <init>()V
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method


.method public static main([Ljava/lang/String;)V
.limit stack 32
.limit locals 32

ldc 1
ldc 2
ldc 3
iadd
isub
istore 0

getstatic java/lang/System/out Ljava/io/PrintStream;
iload 0
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V


return
.end method
