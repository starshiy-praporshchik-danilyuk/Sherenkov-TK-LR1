import lombok.Data;


@Data
public class GolayCode {

    private boolean[][] G;
    private boolean[][] H;
    private boolean[][] matrixB = {
            {true, true, false, true, true, true, false, false, false, true, false, true},
            {true, false, true, true, true, false, false, false, true, false, true, true},
            {false, true, true, true, false, false, false, true, false, true, true, true},
            {true, true, true, false, false, false, true, false, true, true, false, true},
            {true, true, false, false, false, true, false, true, true, false, true, true},
            {true, false, false, false, true, false, true, true, false, true, true, true},
            {false, false, false, true, false, true, true, false, true, true, true, true},
            {false, false, true, false, true, true, false, true, true, true, false, true},
            {false, true, false, true, true, false, true, true, true, false, false, true},
            {true, false, true, true, false, true, true, true, false, false, false, true},
            {false, true, true, false, true, true, true, false, false, false, true, true},
            {true,  true,  true,  true,  true, true, true, true, true, true, true, false}
    };
    private int n;
    private int k;
    private int d;


    public GolayCode(){
        this.n = 24;
        this.k = 12;
        boolean[][] unitMatrix = MatrixCalc.getUnitMatrix(12);


        this.G = new boolean[k][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < k; j++){
                if (i < k){
                    this.G[j][i] = unitMatrix[j][i];
                } else {
                    this.G[j][i] = matrixB[j][i - k];
                }
            }
        }

        this.H = new boolean[n][k];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < k; j++){
                if (i < k){
                    this.H[i][j] = unitMatrix[i][j];
                } else {
                    this.H[i][j] = matrixB[i - k][j];
                }
            }
        }
    }

    public boolean[] decodingWordForGolayCode(boolean[] errorWord) throws Exception {
        boolean[] syndrome = MatrixCalc.rowMultMatr(errorWord, this.H);
        if (MatrixCalc.wordWeight(syndrome) <= 3){
            boolean[] result = new boolean[n];
            System.arraycopy(syndrome, 0, result, 0, k);
            return MatrixCalc.rowPlusRow(result, errorWord);
        }

        for (int i = 0; i < k; i++){
            boolean[] u = MatrixCalc.rowPlusRow(syndrome, matrixB[i]);
            if (MatrixCalc.wordWeight(u) <= 2){
                boolean[] result = new boolean[n];
                System.arraycopy(u, 0, result, 0, k);
                result[k + i] = !result[k + i];
                return MatrixCalc.rowPlusRow(result, errorWord);
            }
        }

        boolean[] newSyndrome = MatrixCalc.rowMultMatr(syndrome, matrixB);

        if (MatrixCalc.wordWeight(newSyndrome) <= 3) {
            boolean[] result = new boolean[n];
            System.arraycopy(newSyndrome, 0, result, k, k);
            return MatrixCalc.rowPlusRow(result, errorWord);
        }

        for (int i = 0; i < k; i++){
            boolean[] u = MatrixCalc.rowPlusRow(newSyndrome, matrixB[i]);
            if (MatrixCalc.wordWeight(u) <= 2){
                boolean[] result = new boolean[n];
                System.arraycopy(u, 0, result, k, k);
                result[i] = !result[i];
                return MatrixCalc.rowPlusRow(result, errorWord);
            }
        }
        return errorWord;
    }

}
