import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        int n = 10, k = 5;
        boolean[][] matrix = new boolean[k][n];
        //Заполнение матрицы рандомно
        for(var i = 0; i < k; i++){
            for(var j = 0; j < n; j++){
                var random = new Random();
                matrix[i][j] = random.nextBoolean();
            }
        }

        System.out.println(MatrixCalc.toString(matrix));

        LinearCode linearCode = new LinearCode(matrix);

        System.out.println("G:");
        System.out.println(MatrixCalc.toString(linearCode.getMatrixG()));

        System.out.println("X:");
        System.out.println(MatrixCalc.toString(linearCode.getMatrixX()));

        System.out.println("H:");
        System.out.println(MatrixCalc.toString(linearCode.getMatrixH()));

        System.out.println("Words:");
        System.out.println(MatrixCalc.toString(linearCode.getWords()));

        boolean[][] words = linearCode.getWords();
        boolean[][] H = linearCode.getMatrixH();
        //Умножение множества кодовых слов на матрицу Н
        for (int i = 0; i < words.length; i++){
            boolean[] res = MatrixCalc.rowMultMatr(words[i], H);
            for (int j = 0; j < res.length; j++){
                int val = res[j] ? 1 : 0;
                System.out.print(val);
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println("Минимальное кодовое расстояние: ");
        System.out.println(linearCode.getCodeDistance());

        boolean[] wordEx = linearCode.getWords()[1];
        System.out.println(MatrixCalc.toString(wordEx));
        wordEx[0] = !wordEx[0];
        System.out.println(MatrixCalc.toString(wordEx));
        System.out.println("Результат умножения кодового слова с ошибкой на матрицу H: ");
        boolean[] ex = MatrixCalc.rowMultMatr(wordEx, linearCode.getMatrixH());
        System.out.println(MatrixCalc.toString(ex));

    }

}
