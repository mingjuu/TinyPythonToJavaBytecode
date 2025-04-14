import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.print.DocFlavor;
import java.sql.SQLOutput;
import java.util.Hashtable;
import java.util.Stack;

public class tinyPythonToJasmin extends tinyPythonBaseListener{
    public StringBuilder fffffinal = new StringBuilder();

    public ParseTreeProperty<String> newTexts = new ParseTreeProperty<>();
    public Hashtable<String, String> hashtable_method_public = new Hashtable<>();
    public Stack<String> Stack = new Stack<>();
    static int argumentCount = 0;
    static int labelCount = 0;


    int method = 0;

    @Override
    public void enterFile_input(tinyPythonParser.File_inputContext ctx) {
        super.enterFile_input(ctx);
        labelCount = 0;

    }

    @Override
    public void exitFile_input(tinyPythonParser.File_inputContext ctx) {
        super.exitFile_input(ctx);
        // init code
        String f = """
                .class public Test
                .super java/lang/Object

                .method public <init>()V
                aload_0
                invokespecial java/lang/Object/<init>()V
                return
                .end method

                """;
        f += newTexts.get(ctx.defs())+"\n";
        f +="""
                        .method public static main([Ljava/lang/String;)V
                        .limit stack 32
                        .limit locals 32
                        """;
        for(int i = 0; i<ctx.stmt().size(); i++){
            f += "\n"+newTexts.get(ctx.stmt(i));
        }
        f +="\n";
        f += "return\n.end method\n";
        fffffinal.append(f);

    }

    @Override
    public void enterDefs(tinyPythonParser.DefsContext ctx) {
        super.enterDefs(ctx);
    }

    @Override
    public void exitDefs(tinyPythonParser.DefsContext ctx) {
        super.exitDefs(ctx);
        String s = "";
        for(int i =0; i<ctx.def_stmt().size(); i++){
            s += newTexts.get(ctx.def_stmt(i));

        }

        /// 추가로 구현하기
        newTexts.put(ctx, s);


    }

    @Override
    public void enterStmt(tinyPythonParser.StmtContext ctx) {
        super.enterStmt(ctx);
    }

    @Override
    public void exitStmt(tinyPythonParser.StmtContext ctx) {
        super.exitStmt(ctx);
        if(ctx.simple_stmt() != null){
            newTexts.put(ctx, newTexts.get(ctx.simple_stmt()));
        }
        else{
            newTexts.put(ctx, newTexts.get(ctx.compound_stmt()));
        }
    }

    @Override
    public void enterSimple_stmt(tinyPythonParser.Simple_stmtContext ctx) {
        super.enterSimple_stmt(ctx);
    }

    @Override
    public void exitSimple_stmt(tinyPythonParser.Simple_stmtContext ctx) {
        super.exitSimple_stmt(ctx);
        String s ="";
        s += newTexts.get(ctx.small_stmt())+"\n";
        newTexts.put(ctx, s);
    }

    @Override
    public void enterSmall_stmt(tinyPythonParser.Small_stmtContext ctx) {
        super.enterSmall_stmt(ctx);
    }

    @Override
    public void exitSmall_stmt(tinyPythonParser.Small_stmtContext ctx) {
        super.exitSmall_stmt(ctx);
        if (ctx.assignment_stmt() != null) {
            newTexts.put(ctx, newTexts.get(ctx.assignment_stmt()));
        }
        else if(ctx.print_stmt() != null){
            newTexts.put(ctx, newTexts.get(ctx.print_stmt()));
        }
        else if(ctx.flow_stmt() != null){
            newTexts.put(ctx, newTexts.get(ctx.flow_stmt()));
        }
        else if(ctx.return_stmt() != null){
            newTexts.put(ctx, newTexts.get(ctx.return_stmt()));
        }
    }

    @Override
    public void enterAssignment_stmt(tinyPythonParser.Assignment_stmtContext ctx) {
        super.enterAssignment_stmt(ctx);
    }

    @Override
    public void exitAssignment_stmt(tinyPythonParser.Assignment_stmtContext ctx) {
//        super.exitAssignment_stmt(ctx);
        int NAME_num = Stack.indexOf(ctx.NAME().getText());
        if (NAME_num == -1){
            Stack.push(ctx.NAME().getText());// 함수의 지역변수 카운트
        }
        NAME_num = Stack.indexOf(ctx.NAME().getText());
        String expr = newTexts.get(ctx.expr());
        String s = expr+"\n"+"istore "+NAME_num;
        newTexts.put(ctx, s);
    }

    @Override
    public void enterFlow_stmt(tinyPythonParser.Flow_stmtContext ctx) {
        super.enterFlow_stmt(ctx);
    }

    @Override
    public void exitFlow_stmt(tinyPythonParser.Flow_stmtContext ctx) {
        super.exitFlow_stmt(ctx);
        if (ctx.break_stmt() != null){
            newTexts.put(ctx, newTexts.get(ctx.break_stmt()));
        }
        else{
            newTexts.put(ctx, newTexts.get(ctx.continue_stmt()));
        }
    }

    @Override
    public void enterBreak_stmt(tinyPythonParser.Break_stmtContext ctx) {
        super.enterBreak_stmt(ctx);
    }

    @Override
    public void exitBreak_stmt(tinyPythonParser.Break_stmtContext ctx) {
        super.exitBreak_stmt(ctx);
        newTexts.put(ctx, "goto END");
    }

    @Override
    public void enterContinue_stmt(tinyPythonParser.Continue_stmtContext ctx) {
        super.enterContinue_stmt(ctx);
    }

    @Override
    public void exitContinue_stmt(tinyPythonParser.Continue_stmtContext ctx) {
        super.exitContinue_stmt(ctx);
        newTexts.put(ctx, "goto LOOP");

    }

    @Override
    public void enterCompound_stmt(tinyPythonParser.Compound_stmtContext ctx) {
        super.enterCompound_stmt(ctx);
    }

    @Override
    public void exitCompound_stmt(tinyPythonParser.Compound_stmtContext ctx) {
        super.exitCompound_stmt(ctx);
        if (ctx.if_stmt() != null) {
            newTexts.put(ctx, newTexts.get(ctx.if_stmt()));
//            System.out.print(newTexts.get(ctx.assignment_stmt())+"\n");
        }
        else if(ctx.while_stmt() != null){
            newTexts.put(ctx, newTexts.get(ctx.while_stmt()));
//            System.out.println(newTexts.get(ctx.print_stmt()));
        }

    }

    @Override
    public void enterIf_stmt(tinyPythonParser.If_stmtContext ctx) {
        super.enterIf_stmt(ctx);

    }

    @Override
    public void exitIf_stmt(tinyPythonParser.If_stmtContext ctx) {
        super.exitIf_stmt(ctx);

        int size = ctx.suite().size()-1;
        int END_num = size +1;
        String END = "LABEL"+END_num;
        String label = "LABEL";
        String s = "";
        for(int i =0; i<ctx.test().size(); i++){
            s += newTexts.get(ctx.test(i))+" "+label+labelCount+"\n"+newTexts.get(ctx.suite(i))+"\ngoto "+END+"\n"+label+labelCount+":\n";
            labelCount++;

        }
        if (ctx.test().size() != ctx.suite().size()) {
            s += newTexts.get(ctx.suite(size));
        }
        s += "\n"+END+":";
        newTexts.put(ctx, s);

    }

    @Override
    public void enterWhile_stmt(tinyPythonParser.While_stmtContext ctx) {
        super.enterWhile_stmt(ctx);
    }

    @Override
    public void exitWhile_stmt(tinyPythonParser.While_stmtContext ctx) {
        super.exitWhile_stmt(ctx);
        String start = "LOOP";
        String end = "END";
        String test = newTexts.get(ctx.test());
        String suite = newTexts.get(ctx.suite());
        newTexts.put(ctx, start+":\n"+test+" END"+suite+"\ngoto LOOP\n"+end+":\n");

    }

    @Override
    public void enterDef_stmt(tinyPythonParser.Def_stmtContext ctx) {
        super.enterDef_stmt(ctx);
    }

    @Override
    public void exitDef_stmt(tinyPythonParser.Def_stmtContext ctx) {
        super.exitDef_stmt(ctx);
        String ar = "";
        for(int i=0; i<argumentCount; i++){
            ar +="I";
        }
        String s = "\n.method public static "+ctx.NAME()+"("+ar+")I\n"+".limit stack 32\n" +
                ".limit locals 32"+newTexts.get(ctx.suite())+".end method\n";
        String func = "invokestatic "+"Test/"+ctx.NAME()+"("+ar+")I";
        hashtable_method_public.put(ctx.NAME().getText(), func); // 이따 부를 때 써
        newTexts.put(ctx, s);
        Stack.clear();
        argumentCount =0;

    }

    @Override
    public void enterSuite(tinyPythonParser.SuiteContext ctx) {
        super.enterSuite(ctx);
    }

    @Override
    public void exitSuite(tinyPythonParser.SuiteContext ctx) {
        super.exitSuite(ctx);
        String s ="";
        if(ctx.getChildCount() == 1){
            //simple_stmt
            s = newTexts.get(ctx.simple_stmt());
        }
        else{
            // NEWLINE  stmt+
            s = "\n";
            for(int i =0; i<ctx.stmt().size(); i++){
                s += newTexts.get(ctx.stmt(i));
            }
        }
        newTexts.put(ctx, s);
    }

    @Override
    public void enterArgs(tinyPythonParser.ArgsContext ctx) {
        super.enterArgs(ctx);
    }


    @Override
    public void exitArgs(tinyPythonParser.ArgsContext ctx) {
        super.exitArgs(ctx);
        for(int i=0; i<ctx.NAME().size(); i++){
            Stack.push(ctx.NAME(i).getText());
            argumentCount +=1;
        }
    }

    @Override
    public void enterReturn_stmt(tinyPythonParser.Return_stmtContext ctx) {
        super.enterReturn_stmt(ctx);
    }

    @Override
    public void exitReturn_stmt(tinyPythonParser.Return_stmtContext ctx) {
        super.exitReturn_stmt(ctx);
        String expr = newTexts.get(ctx.expr());
        newTexts.put(ctx, expr+"\nireturn");
    }

    @Override
    public void enterTest(tinyPythonParser.TestContext ctx) {
        super.enterTest(ctx);
    }

    @Override
    public void exitTest(tinyPythonParser.TestContext ctx) {
        super.exitTest(ctx);
        String s1 = newTexts.get(ctx.expr(0));
        String s2 = newTexts.get(ctx.expr(1));
        String cmp = newTexts.get(ctx.comp_op());
        newTexts.put(ctx, s1+"\n"+s2+"\n"+cmp);

    }

    @Override
    public void enterPrint_stmt(tinyPythonParser.Print_stmtContext ctx) {
        super.enterPrint_stmt(ctx);
    }

    @Override
    public void exitPrint_stmt(tinyPythonParser.Print_stmtContext ctx) {
        super.exitPrint_stmt(ctx);

        String s ="getstatic java/lang/System/out Ljava/io/PrintStream;\n";
        s+= newTexts.get(ctx.print_arg());
        if (ctx.print_arg().STRING() != null){
            s+= "\ninvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n";
        }
        else{
            s +="\ninvokestatic java/lang/String/valueOf(I)Ljava/lang/String;";
            s+= "\ninvokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n";

        }
        newTexts.put(ctx, s);


    }

    @Override
    public void enterPrint_arg(tinyPythonParser.Print_argContext ctx) {
        super.enterPrint_arg(ctx);
    }

    @Override
    public void exitPrint_arg(tinyPythonParser.Print_argContext ctx) {
        super.exitPrint_arg(ctx);
        if (ctx.STRING() != null){
            String str = ctx.STRING().getText();
            newTexts.put(ctx, "ldc "+str);
        }
        else{
            newTexts.put(ctx, newTexts.get(ctx.expr()));
        }

    }

    @Override
    public void enterComp_op(tinyPythonParser.Comp_opContext ctx) {
        super.enterComp_op(ctx);
    }

    @Override
    public void exitComp_op(tinyPythonParser.Comp_opContext ctx) {
        super.exitComp_op(ctx);
        String com = ctx.getChild(0).getText();

        if (com.equals("<")){
            newTexts.put(ctx, "if_icmpge");
        } else if (com.equals(">")) {
            newTexts.put(ctx, "if_icmple");
        }
        else if (com.equals("==")) {
            newTexts.put(ctx, "if_icmpne");
        }
        else if (com.equals(">=")) {
            newTexts.put(ctx, "if_icmplt");
        }
        else if (com.equals("<=")) {
            newTexts.put(ctx, "if_icmpgt");
        }
        else if (com.equals("!=")) {
            newTexts.put(ctx, "if_icmpeq");
        }

    }

    @Override
    public void enterExpr(tinyPythonParser.ExprContext ctx) {
        super.enterExpr(ctx);
    }

    @Override
    public void exitExpr(tinyPythonParser.ExprContext ctx) {
        super.exitExpr(ctx);
        String s = "";
        if(ctx.getChildCount() == 1){
            //NUMBER
            String number = ctx.NUMBER().getText();
            s =  "ldc "+number;
            newTexts.put(ctx, s);
        } else if (ctx.getChild(1).getText().equals("+") || ctx.getChild(1).getText().equals("-")) {
            // + or -
            String expr1 = newTexts.get(ctx.expr(0));
            String expr2 = newTexts.get(ctx.expr(1));
            if (ctx.getChild(1).getText().equals("+")){
                s = expr1+"\n"+expr2+"\n"+"iadd";
            }
            else{
                s = expr1+"\n"+expr2+"\n"+"isub";
            }
            newTexts.put(ctx, s);
        } else if (ctx.getChild(0).getText().equals("(") && ctx.getChild(2).getText().equals(")")) {
           // ( expr )
            String expr = newTexts.get(ctx.expr(0));
            newTexts.put(ctx, expr);
        }
        else{
            String opt = newTexts.get(ctx.opt_paren());
            if (opt.equals("")){
                // 지역변수 사용
                int num =Stack.indexOf(ctx.NAME().getText());
                s = "iload "+num;
                newTexts.put(ctx, s);
            } else if (opt.equals("()")) {
//                invokestatic [[NAME]]fs
                String func_info = hashtable_method_public.get(ctx.NAME().getText());
                newTexts.put(ctx, func_info);
            }
            else {
                //인자 있는 함수 호출 인자로드하고, 함수 호출하는건가? 이게맞냐
                String func_info = hashtable_method_public.get(ctx.NAME().getText());
                for (int i=0; i<ctx.opt_paren().expr().size(); i++){
                    s += newTexts.get(ctx.opt_paren().expr(i))+"\n";
                }
                newTexts.put(ctx, s+func_info);
            }
        }
    }

    @Override
    public void enterOpt_paren(tinyPythonParser.Opt_parenContext ctx) {
        super.enterOpt_paren(ctx);
    }

    @Override
    public void exitOpt_paren(tinyPythonParser.Opt_parenContext ctx) {
        super.exitOpt_paren(ctx);
//        System.out.println(ctx.getChildCount());
        if (ctx.getChildCount() == 0){
            newTexts.put(ctx, "");
        } else if (ctx.getChildCount() == 2) {
            newTexts.put(ctx, "()");
        }
        else{
            String t ="";
            for(int i=0; i<ctx.getChildCount(); i++){
                t += newTexts.get(ctx.getChild(i));
            }
            newTexts.put(ctx, t);
        }
    }




}
