package tools.insertcode.junkcode;

import java.util.Random;

public class InsertCode {




    public static String generateDynamicJunkCode4() {

        Random random = new Random();
        // 随机生成所有的变量名
        String arrayName = "arr_" + getRandomString();
        String outerLoopVar = "i_" + getRandomString();
        String innerLoopVar = "j_" + getRandomString();
        String tempVar = "tmp_" + getRandomString();

        return "public void sadcq"+getRandomString()+"() {\n" +
                "    long "+arrayName+" = java.lang.System.nanoTime();\n" +
                "    int "+outerLoopVar+" = new java.util.Random().nextInt(1000);\n" +
                "    boolean "+innerLoopVar+" = ("+arrayName+" % ("+outerLoopVar+" + "+new Random().nextInt(100)+")) > "+new Random().nextInt(100)+";\n" +
                "    double "+tempVar+" = "+innerLoopVar+" ? java.lang.Math.sqrt("+outerLoopVar+") : java.lang.Math.pow("+outerLoopVar+", "+new Random().nextInt(100)+");\n" +
                "    if ("+tempVar+" < 0.0) {\n" +
                "        java.lang.System.out.println("+tempVar+");\n" +
                "    }\n" +
                "}";

    }

    public static String generateDynamicJunkCode3() {


        // 1. 随机生成变量名（仅限字母，确保符合 Java 命名规范）
        String methodName = "ona" + getRandomString();
        String stateVar = "ckuy" + getRandomString();
        String loopVar = "pmh_" + getRandomString();
        String jakad = "dima" + getRandomString();


        return "public void gH3o0_junk() {\n" +
                "    String "+methodName+" = java.util.UUID.randomUUID().toString();\n" +
                "    int "+stateVar+" = "+methodName+".length();\n" +
                "    char "+loopVar+" = "+methodName+".charAt(new java.util.Random().nextInt("+stateVar+"));\n" +
                "    boolean "+jakad+" = ("+loopVar+" == 'z');\n" +
                "    if ("+jakad+" && "+stateVar+" < "+new Random().nextInt(100)+") {\n" +
                "        "+methodName+".substring("+new Random().nextInt(100)+", "+new Random().nextInt(100)+");\n" +
                "    }\n" +
                "}";


    }
    public static String generateDynamicJunkCode2() {
        int index = new Random().nextInt(100);
        // 1. 随机生成变量名和方法名
        String methodName = "process_" + getRandomString();
        String arrayName = "stack_" + getRandomString();
        String outerIdx = "i_" + getRandomString();


        return "    public static void jsla_" + getRandomString() + index+"() {\n" +
                "int "+methodName+" = new java.util.Random().nextInt(50);\n" +
                "    int "+arrayName+" = ("+methodName+" >  "+new  Random().nextInt(100)+") ? 1 : (("+methodName+" >  "+new  Random().nextInt(100)+") ? "+new Random().nextInt(100)+" : (("+methodName+" >  "+new  Random().nextInt(100)+") ? "+new Random().nextInt(100)+" : "+new Random().nextInt(100)+"));\n" +
                "    int "+outerIdx+" = "+arrayName+" * "+methodName+";\n" +
                "    if ("+outerIdx+" > "+new Random().nextInt(100)+") {\n" +
                "        java.lang.System.arraycopy(new int[]{"+outerIdx+"}, 0, new int[]{0}, 0, 1); \n" +
                "    }"+
                "    }";

    }
    public static String generateDynamicJunkCode() {
        int index = new Random().nextInt(100);
        String arrayName = "arr_" + getRandomString();
        String outerLoop = "i_" + getRandomString();
        String innerLoop = "j_" + getRandomString();
        String tempVar = "tmp_" + getRandomString();

        return "    public static void junkMethod_" + getRandomString() + index+"() {\n" +
                " java.lang.Object "+arrayName+" = new java.lang.Object();\n" +
                "        int "+outerLoop+" = "+arrayName+".hashCode();\n" +
                "        int "+innerLoop+" = new java.util.Random().nextInt(100);\n" +
                "        int "+tempVar+" = ("+outerLoop+" ^ "+innerLoop+") & 0x7FFFFFFF;\n" +
                "        if ("+tempVar+" == "+new  Random().nextInt(100)+" && "+outerLoop+" < "+new  Random().nextInt(100)+" ) {\n" +
                "            "+arrayName+".toString();\n" +
                "        }"+
                "    }";
    }


    private static String getRandomString() {
        int length = new Random().nextInt(5,20);
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rd.nextInt(chars.length())));
        }
        return sb.toString();
    }

}
