public class Main {

    public static void main(String[] args) throws Exception {

        NewReedMullerCode reedMullerCode = new NewReedMullerCode(2, 4);

        System.out.println(MatrixCalc.toString(reedMullerCode.getMatrixG()));
        /*boolean[] word = reedMullerCode.getMatrixG()[2];
        word = MatrixCalc.rowPlusRow(word, reedMullerCode.getMatrixG()[9]);
        word = MatrixCalc.rowPlusRow(word, reedMullerCode.getMatrixG()[5]);
        word[10] = !word[10];
        word[2] = !word[2];*/
        //word[10] = !word[10];
        boolean[] word = new boolean[]{false, true, false, true,     true, true, true, true,    true, false, true, false,    false,false,false,false};
        boolean[] result = reedMullerCode.decodingWord(word);
        System.out.println("Исправленное слово: \n" + MatrixCalc.toString(result));
    }
}