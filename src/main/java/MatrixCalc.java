import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MatrixCalc {

    public static boolean[][] generateRandMatrix(int n, int k){
        var rand = new Random();
        boolean[][] A = new boolean[k][n];

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = rand.nextBoolean();
            }
        }
        return A;
    }

    public static boolean[][] getUnitMatrix(int n){
        boolean[][] matrix = new boolean[n][n];
        for (var i = 0; i < n; i++){
            for (var j = 0; j < n; j++)
                matrix[i][j] = i == j;
        }
        return matrix;
    }

    public static boolean[][] createAllWords(int k){
        int wordMax = 1;
        for(int i = 0; i < k; i++)
            wordMax *= 2;
        boolean[][] words = new boolean[wordMax - 1][k];
        wordMax--;
        for (int i = 1; i <= wordMax; i++){
            String val = Integer.toBinaryString(i);
            boolean[] valArr = new boolean[k];
            for (int j = 0; j < val.length(); j++){
                valArr[k - j - 1] = val.charAt(val.length() - 1 - j) == '1';
            }
            words[i - 1] = valArr;
        }
        return words;
    }

    public static int getCodeDistance(boolean[][] matrix) throws Exception {
        int k = matrix.length;
        int n = matrix[0].length;
        boolean[][] allWordsSizeK = createAllWords(k);
        boolean[][] matr = matrixMultMatrix(allWordsSizeK, matrix);
        int dist = n;
        for (int i = 0; i < matr.length; i++){
            int temp = wordWeight(matr[i]);
            if(temp < dist) dist = temp;
        }
        return dist;
    }

    public static boolean[][] getDualErrors(int n) throws Exception {
        List<boolean[]> tempMatrix = new ArrayList<boolean[]>();
        boolean[][] unitMatrix = getUnitMatrix(n);
        for(int i = 0; i < n; i++)
            tempMatrix.add(unitMatrix[i]);
        for(int i = 0; i < n - 1; i++){
            for(int j = i + 1; j < n; j++)
                tempMatrix.add(rowPlusRow(unitMatrix[i], unitMatrix[j]));
        }
        boolean[][] result = new boolean[tempMatrix.size()][n];
        for(int i = 0; i < result.length; i++)
            result[i] = tempMatrix.get(i);
        return result;
    }

    public static int wordWeight(boolean[] word){
        int weight = 0;
        for (boolean point : word) if (point) weight++;
        return weight;
    }

    public static boolean[] rowMultMatr(boolean[] row, boolean[][] matrix) throws Exception {
        if(row.length != matrix.length) throw new Exception();
        int n = matrix[0].length;
        boolean[] result = new boolean[n];
        for(int i = 0; i < n; i++){
            boolean val = false;
            for(int j = 0; j < row.length; j++){
                boolean temp = row[j] == matrix[j][i] && row[j];
                val = temp != val;
            }
            result[i] = val;
        }
        return result;
    }

    public static int searchRowInMatrix(boolean[] row, boolean[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            if(Arrays.equals(row, matrix[i])) return i;
        }
        return -1;
    }

    public static boolean[][] matrixMultMatrix(boolean[][] matrixOne, boolean[][] matrixTwo) throws Exception {
        int k = matrixOne.length;
        int n = matrixTwo.length;
        boolean[][] matrixResult = new boolean[k][n];
        for (int i = 0; i < k; i++) {
            matrixResult[i] = rowMultMatr(matrixOne[i], matrixTwo);
        }
        return matrixResult;
    }

    public static boolean[] rowPlusRow(boolean[] rowOne, boolean[] rowTwo) throws Exception {
        if(rowOne.length != rowTwo.length) throw new Exception();
        boolean[] rowResult = new boolean[rowOne.length];
        for (int i = 0; i < rowOne.length; i++){
            if(rowOne[i] == rowTwo[i])
                rowResult[i] = false;
            else
                rowResult[i] = true;
        }
        return rowResult;
    }

    public static void swapRow(boolean[][] matrix, int i, int j){
        boolean[] tempRow;
        tempRow = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = tempRow;
    }


    public static void Ref(boolean[][] matrix) throws Exception {
        int k = matrix.length;
        int n = matrix[0].length;
        int mainRow = 0;
        for(int i = 0; i < n; i++){
            int trueRow = mainRow;
            while(!matrix[trueRow][i]){
                if(trueRow > k - 2) break;
                trueRow++;
            }
            if(!matrix[trueRow][i]) continue;
            MatrixCalc.swapRow(matrix, mainRow, trueRow);
            if(mainRow + 1 == k) break;
            for (int j = mainRow + 1; j < k; j++){
                if(matrix[j][i])
                    matrix[j] = MatrixCalc.rowPlusRow(matrix[j], matrix[mainRow]);
            }
            mainRow++;
        }
    }

    public static void Rref(boolean[][] matrix) throws Exception {
        Ref(matrix);
        int k = matrix.length;
        int n = matrix[0].length;
        for(int i = 0; i < k; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j]) {
                    for (int q = 0; q < i; q++)
                        if (matrix[q][j])
                            matrix[q] = rowPlusRow(matrix[q], matrix[i]);
                    break;
                }
            }
        }
    }

    public static String toString(boolean[][] matrix){
        var result = new StringBuilder();
        for (boolean[] booleans : matrix) {
            for (var j = 0; j < matrix[0].length; j++) {
                String val = booleans[j] ? "1" : "0";
                result.append(val);
                result.append(" ");
            }
            result.append('\n');
        }
        return result.toString();
    }

    public static String toString(boolean[] row){
        var result = new StringBuilder();
            for (var j = 0; j < row.length; j++) {
                String val = row[j] ? "1" : "0";
                result.append(val);
                result.append(" ");
            }
        return result.toString();
    }

}
