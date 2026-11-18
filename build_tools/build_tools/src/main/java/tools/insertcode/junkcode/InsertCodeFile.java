package tools.insertcode.junkcode;

public class InsertCodeFile {
        public static void junkMethod_0() {
            int[] arr_EIVE3dGGEA = new int[] { 28, 58 };
            for (int i_D7IQfqvVu9 = 0; i_D7IQfqvVu9 < arr_EIVE3dGGEA.length - 1; i_D7IQfqvVu9++) {
                for (int j_Fi2h5SvYOk = 0; j_Fi2h5SvYOk < arr_EIVE3dGGEA.length - 1 - i_D7IQfqvVu9; j_Fi2h5SvYOk++) {
                    if (arr_EIVE3dGGEA[j_Fi2h5SvYOk] > arr_EIVE3dGGEA[j_Fi2h5SvYOk + 1]) {
                        int tmp_ZEWuEBMtYJ = arr_EIVE3dGGEA[j_Fi2h5SvYOk];
                        arr_EIVE3dGGEA[j_Fi2h5SvYOk] = arr_EIVE3dGGEA[j_Fi2h5SvYOk + 1];
                        arr_EIVE3dGGEA[j_Fi2h5SvYOk + 1] = tmp_ZEWuEBMtYJ;
                    }
                }
            }
        }
          public  static void  insertCode2(){

          int[] smallStack = {9, 3, 1, 7};
            for (int i = 0; i < smallStack.length; i++) {
                for (int j = i + 1; j < smallStack.length; j++) {
                    if (smallStack[i] > smallStack[j]) {
                        int temp = smallStack[i];
                        smallStack[i] = smallStack[j];
                        smallStack[j] = temp;
                    }
                }
            }
            }


    public static void insertCode3() {

            int x = 10;
                      for (int i = 0; i < 100; i++) {
                          x ^= (x << 13);
                          x ^= (x >>> 17);
                          x ^= (x << 5);
                          // 永远为真的空判断，防止编译器把上面代码优化掉
                          if (x == 0) {
                              x = i;
                          }
                      }
                   }

         public static void insertCode4() {
             int[] numbersfdajipureqvna3280 = new int[] { 2, 3 };
             for (int ifdjaiopurq832074 = 0; ifdjaiopurq832074 < numbersfdajipureqvna3280.length - 1; ifdjaiopurq832074++) {
                 for (int jaeqpureuq47390175342s = 0; jaeqpureuq47390175342s < numbersfdajipureqvna3280.length - 1 - ifdjaiopurq832074; jaeqpureuq47390175342s++) {
                     if (numbersfdajipureqvna3280[jaeqpureuq47390175342s] > numbersfdajipureqvna3280[jaeqpureuq47390175342s + 1]) {
                         int temphiuyuiyuguhg78779hjk = numbersfdajipureqvna3280[jaeqpureuq47390175342s];
                         numbersfdajipureqvna3280[jaeqpureuq47390175342s] = numbersfdajipureqvna3280[jaeqpureuq47390175342s + 1];
                         numbersfdajipureqvna3280[jaeqpureuq47390175342s + 1] = temphiuyuiyuguhg78779hjk;
                     }
                 }
             }
         }



}