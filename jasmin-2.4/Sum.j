.class public Sum

.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method

;sum function
.method public static sum(I)I
.limit stack 32
.limit locals 8

;write your code
ldc 0
istore 1

iload 0
ifeq Lend

Loop:
	iload 0
	iload 1
	iadd
	istore 1
	iinc 0 -1
	iload 0
	ifne Loop

Lend:
iload 1
ireturn
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 32
.limit locals 8
ldc 100
istore 0
getstatic java/lang/System/out Ljava/io/PrintStream;
iload 0
invokestatic Sum/sum(I)I
invokevirtual java/io/PrintStream/println(I)V
return
.end method