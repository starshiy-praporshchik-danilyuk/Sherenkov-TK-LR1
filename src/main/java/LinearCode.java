import lombok.Data;

@Data
public class LinearCode {

    private int n, k, d, r;
    private boolean[][] matrixG;
    private boolean[][] matrixH;

    public LinearCode(int r, boolean extended) {
        this.r = r;
        int temp = 2;
        for (int i = 0; i < r-1; i++)
            temp *= 2;
        this.n = temp - 1;
        this.k = this.n - r;
        this.d = 3;
        this.matrixH = MatrixCalc.createAllWords(r);

        int q = n - 1;
        for(int i = 0; i < n - r; i++){
            if (MatrixCalc.wordWeight(this.matrixH[i]) == 1) {
                MatrixCalc.swapRow(this.matrixH, i, q);
                q--;
            }
        }

        this.matrixG = new boolean[this.k][this.n];
        boolean[][] unitMatrix = MatrixCalc.getUnitMatrix(k);
        for (int i = 0; i < k; i++){
            for (int j = 0; j < n; j++){
                if (j < k)
                    this.matrixG[i][j] = unitMatrix[i][j];
                else
                    this.matrixG[i][j] = this.matrixH[i][j - k];
            }
        }

        if (extended) {
            generateExtendedMatrixG();
            this.generateExtendedMatrixH();
            this.n++;
            this.r++;
        }
    }

    private void generateExtendedMatrixG(){
        boolean[][] newG = new boolean[this.k][this.n + 1];
        for (int i = 0; i < k; i++){
            for (int j = 0; j < n + 1; j++){
                if(j != n)
                    newG[i][j] = this.matrixG[i][j];
                else
                    newG[i][j] = false;
            }
        }

        for (int i = 0; i < k; i++){
            if(MatrixCalc.wordWeight(newG[i]) % 2 == 1)
                newG[i][n] = true;
        }
        this.matrixG = newG;
    }

    private void generateExtendedMatrixH(){
        boolean[][] newH = new boolean[n + 1][this.r + 1];
        for (int i = 0; i < n + 1; i++){
            for (int j = 0; j < r + 1; j++){
                if (i < n && j < r)
                    newH[i][j] = this.matrixH[i][j];
                else newH[i][j] = j == r;
            }
        }
        this.matrixH = newH;
    }

}