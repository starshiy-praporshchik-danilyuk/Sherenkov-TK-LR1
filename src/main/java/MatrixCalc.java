public class MatrixCalc {

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
