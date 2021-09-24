import java.util.Random;


public class Main {

    public static void correctionWord(boolean[] codeWord, boolean[][] matrixErrors, boolean[][] H, boolean[][] matrixSyndromes) throws Exception {
        boolean[] syndrome = MatrixCalc.rowMultMatr(codeWord, H);
        System.out.print("Синдром: ");
        System.out.println(MatrixCalc.toString(syndrome));

        int numberError = MatrixCalc.searchRowInMatrix(syndrome, matrixSyndromes);
        if (numberError == -1) {
            System.out.println("Синдром не найден");
            return;
        }
        System.out.print("Номер неверного разряда:");
        System.out.println(numberError);

        codeWord = MatrixCalc.rowPlusRow(codeWord, matrixErrors[numberError]);
        System.out.println("Исправленное слово:");
        System.out.println(MatrixCalc.toString(codeWord));
    }

    public static void main(String[] args) throws Exception {
        int n = 15;
        int k = 7;
        int d = 5;
        var rand = new Random();
        boolean[][] I_k = MatrixCalc.getUnitMatrix(k);
        boolean[][] X = new boolean[k][n - k];
        boolean[][] G = new boolean[k][n];

        do {
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < n - k; j++) {
                    X[i][j] = rand.nextBoolean();
                }
            }

            for (int i = 0; i < k; i++) {
                for (int j = 0; j < n; j++) {
                    if (j < k) {
                        G[i][j] = I_k[i][j];
                    } else {
                        G[i][j] = X[i][j - k];
                    }
                }
            }
        } while (MatrixCalc.getCodeDistance(G) != d);

        System.out.println("Матрица G: ");
        System.out.println(MatrixCalc.toString(G));
        System.out.print("Кодовое расстояние строк матрицы G: ");
        System.out.println(MatrixCalc.getCodeDistance(G));

        int m = n - k;
        boolean[][] I_m = MatrixCalc.getUnitMatrix(m);
        boolean[][] H = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i < k) H[i][j] = X[i][j];
                else H[i][j] = I_m[i - k][j];
            }
        }
        System.out.println("Матрица H: ");
        System.out.println(MatrixCalc.toString(H));

        boolean[][] matrixErrors = MatrixCalc.getUnitMatrix(n);
        boolean[][] matrixSyndromes = MatrixCalc.matrixMultMatrix(matrixErrors, H);
        System.out.println("Матрица синдромов:");
        System.out.println(MatrixCalc.toString(matrixSyndromes));

        boolean[] word = new boolean[k];
        for (int i = 0; i < k; i++)
            word[i] = rand.nextBoolean();

        System.out.println("Рандомное слово длины k:");
        System.out.println(MatrixCalc.toString(word));

        boolean[] codeWord;
        codeWord = MatrixCalc.rowMultMatr(word, G);

        System.out.println("Сформированное кодовое слово длины n:");
        System.out.println(MatrixCalc.toString(codeWord));

        codeWord = MatrixCalc.rowPlusRow(codeWord, matrixErrors[Math.abs(new Random().nextInt() % n)]);

        System.out.println("Кодовое слово с однократной ошибкой:");
        System.out.println(MatrixCalc.toString(codeWord));

        correctionWord(codeWord, matrixErrors, H, matrixSyndromes);

        boolean[] newWord = new boolean[k];
        for (int i = 0; i < k; i++)
            newWord[i] = rand.nextBoolean();

        System.out.println("Рандомное слово длины k:");
        System.out.println(MatrixCalc.toString(newWord));

        boolean[] newCodeWord;
        newCodeWord = MatrixCalc.rowMultMatr(newWord, G);

        System.out.println("Сформированное кодовое слово длины n:");
        System.out.println(MatrixCalc.toString(newCodeWord));

        newCodeWord = MatrixCalc.rowPlusRow(newCodeWord, MatrixCalc.rowPlusRow(matrixErrors[Math.abs(rand.nextInt() % (n - 1))], matrixErrors[n - 1]));

        System.out.println("Кодовое слово с двукратной ошибкой:");
        System.out.println(MatrixCalc.toString(newCodeWord));

        correctionWord(newCodeWord, matrixErrors, H, matrixSyndromes);

    }
}