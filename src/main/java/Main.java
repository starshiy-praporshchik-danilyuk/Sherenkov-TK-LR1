import java.util.Random;

import static java.lang.Math.abs;

public class Main {

    public static void correctionWord(boolean[] codeWord,
                                      boolean[][] matrixErrors,
                                      boolean[][] H,
                                      boolean[][] matrixSyndromes,
                                      int errorMultiplicity) throws Exception {

        System.out.println("Кодовое слово: \n" + MatrixCalc.toString(codeWord));

        var random = new Random();
        int numberError = 0;
        for (int i = 0; i < errorMultiplicity; i++){
            if(i == 0) {
                numberError = abs(random.nextInt()) % (codeWord.length - 2);
                codeWord = MatrixCalc.rowPlusRow(codeWord, matrixErrors[numberError]);
            }
            else
                codeWord = MatrixCalc.rowPlusRow(codeWord, matrixErrors[numberError + i]);
        }

        System.out.println("Слово с ошибкой кратности " + errorMultiplicity + ": \n" + MatrixCalc.toString(codeWord));

        boolean[] syndrome = MatrixCalc.rowMultMatr(codeWord, H);

        System.out.println("Синдром: \n" + MatrixCalc.toString(syndrome));

        int numberSyndrome = MatrixCalc.searchRowInMatrix(syndrome, matrixSyndromes);
        if (numberSyndrome == -1) {
            System.out.println("Синдром не найден");
            return;
        }

        codeWord = MatrixCalc.rowPlusRow(codeWord, matrixErrors[numberError]);

        System.out.println("Исправленное слово: \n" + MatrixCalc.toString(codeWord));
    }

    public static void main(String[] args) throws Exception {
        Random random = new Random();

        var linearCode = new LinearCode(4, true);

        System.out.println(String.format("Код Хемминга (%d, %d, %d); G:",
                linearCode.getN(), linearCode.getK(), MatrixCalc.getCodeDistance(linearCode.getMatrixG())) + '\n');

        System.out.println(MatrixCalc.toString(linearCode.getMatrixG()));
        System.out.println("Матрица H: \n" + MatrixCalc.toString(linearCode.getMatrixH()));

        int n = linearCode.getN();
        int k = linearCode.getK();

        System.out.println();
        boolean[][] oneErrorWords = MatrixCalc.getUnitMatrix(n);
        System.out.println("Все однократные ошибки:\n" + MatrixCalc.toString(oneErrorWords));
        boolean[][] syndromes = MatrixCalc.matrixMultMatrix(oneErrorWords, linearCode.getMatrixH());
        System.out.println("Синдромы:\n" + MatrixCalc.toString(syndromes));

        System.out.println("Проверка на корректность. При умножении G * H должна получиться нулевая матрица \n" +
                MatrixCalc.toString(MatrixCalc.matrixMultMatrix(linearCode.getMatrixG(), linearCode.getMatrixH())));



        boolean[] word = linearCode.getMatrixG()[abs(random.nextInt() % k)];

        correctionWord(word, oneErrorWords, linearCode.getMatrixH(), syndromes, 1);
        System.out.println();
        correctionWord(word, oneErrorWords, linearCode.getMatrixH(), syndromes, 2);
        System.out.println();
        correctionWord(word, oneErrorWords, linearCode.getMatrixH(), syndromes, 3);

    }
}