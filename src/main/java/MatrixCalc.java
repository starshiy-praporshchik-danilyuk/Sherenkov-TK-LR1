import java.util.Arrays;

public class MatrixCalc {

    public static boolean[][] getUnitMatrix(int n){
        boolean[][] matrix = new boolean[n][n];
        for (var i = 0; i < n; i++){
            for (var j = 0; j < n; j++)
                matrix[i][j] = i == j;
        }
        return matrix;
    }

    public static int getCodeDistance(boolean[][] matrix){
        int k = matrix.length;
        int n = matrix[0].length;
        int dist = n;
        int temp = 0;
        for(int i = 0; i < k; i++){
            for(int j = i + 1; j < k; j++){
                for(int index = 0; index < n; index++) {
                    if (matrix[i][index] != matrix[j][index])
                        temp++;
                }
                if (temp < dist) dist = temp;
                temp = 0;
            }
        }
        return dist;
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
