public class Main {

    public static void main(String[] args) throws Exception {

        /*GolayCode golayCode = new GolayCode();
        System.out.println(MatrixCalc.toString(golayCode.getG()));
        System.out.println(MatrixCalc.toString(golayCode.getH()));

        System.out.println(MatrixCalc.toString(MatrixCalc.matrixMultMatrix(golayCode.getG(), golayCode.getH())));

        boolean[] word = golayCode.getG()[2];
        System.out.println(MatrixCalc.toString(word));
        word[13] = !word[13];
        word[16] = !word[16];
        word[20] = !word[20];
        word[10] = !word[10];
        System.out.println(MatrixCalc.toString(word));
        System.out.println(MatrixCalc.toString(golayCode.decodingWordForGolayCode(word)));*/

        ReedMullerCode reedMullerCode = new ReedMullerCode(1, 4);
        MatrixCalc.outMatrixToConsole(reedMullerCode.getG());
        System.out.println();

        int[] word = reedMullerCode.getG()[3];
        MatrixCalc.outWordToConsole(word);

        word[7] = word[7] == 1 ? 0 : 1;
        for(int i = 0; i < word.length; i++)
            word[i] = word[i] == 0 ? -1 : 1;

        System.out.println(MatrixCalc.toString(reedMullerCode.getOriginalWord(word, 4)));

        word[2] = word[2] == 1 ? -1 : 1;
        System.out.println(MatrixCalc.toString(reedMullerCode.getOriginalWord(word, 4)));

        word[13] = word[13] == 1 ? -1 : 1;
        System.out.println(MatrixCalc.toString(reedMullerCode.getOriginalWord(word, 4)));

        word[10] = word[10] == 1 ? -1 : 1;
        System.out.println(MatrixCalc.toString(reedMullerCode.getOriginalWord(word, 4)));
    }
}