import lombok.Data;

@Data
public class ReedMullerCode {

    private int n;
    private int k;
    private int[][] G;
    //private int[][] H;

    public ReedMullerCode(int r, int m){
        this.n = (int) Math.pow(2, m);
        this.k = 0;
        for (int i = 0; i <= r; i++)
            this.k += (factorial(m) / (factorial(i) * factorial(m - i)));
        this.G = new int[k][n];
        generateMatrixG(0, 0, k - 1, n - 1, r, m);

    }

    private void generateMatrixG(int i1, int j1, int i2, int j2, int r, int m){
        if (0 < r && r < m){
            generateMatrixG(i1, j1, i2 - 1, (j2 - j1) / 2, r, m - 1);
            for (int i = i1; i < i2; i++){
                for (int j = j1; j < (j2 - j1) / 2 + 1; j++){
                    this.G[i][j + (j2 - j1) / 2 + 1] = this.G[i][j];
                }
            }
            generateMatrixG(i2, (j2 - j1) / 2 + 1, i2, j2, r - 1, m - 1);
        } else if (r == 0){
            for (int i = 0; i < (int) Math.pow(2, m); i++){
                this.G[i1][j1 + i] = 1;
            }
        } else if (r == m) {
            generateMatrixG(i1, j1, i2 - 1, j2, r - 1, m);
            this.G[i2][j2] = 1;
        }
    }

    public int[][] getMatrixH(int i, int m){
        int[][] backUnitMatrix = MatrixCalc.getUnitMatrixInteger((int) Math.pow(2, (double)m - i));
        int[][] frontUnitMatrix = MatrixCalc.getUnitMatrixInteger((int) Math.pow(2, i - 1.));
        int[][] H = {{1, 1},
                     {1, -1}};
        int[][] resultMatrix = MatrixCalc.multMatrixByCronecker(backUnitMatrix, H);
        resultMatrix = MatrixCalc.multMatrixByCronecker(resultMatrix, frontUnitMatrix);
        return resultMatrix;
    }

    public boolean[] getOriginalWord(int[] errorWord, int m){
        int[] wOne = MatrixCalc.rowMultMatrixInteger(errorWord, this.getMatrixH(1, m));
        int[] wTwo = MatrixCalc.rowMultMatrixInteger(wOne, this.getMatrixH(2, m));
        int[] wThree = MatrixCalc.rowMultMatrixInteger(wTwo, this.getMatrixH(3, m));
        int[] wFour = MatrixCalc.rowMultMatrixInteger(wThree, this.getMatrixH(4, m));

        int index = 0;
        for(int i = 1; i < wFour.length; i++){
            if (Math.abs(wFour[i]) > Math.abs(wFour[index]))
                index = i;
        }

        boolean[] originalWord = new boolean[5];
        String binaryIndex = new StringBuilder(Integer.toBinaryString(index)).reverse().toString();
        for (int i = 0; i < binaryIndex.length(); i++){
            originalWord[i + 1] = binaryIndex.charAt(i) == '1';
        }
        if (wFour[index] > 0)
            originalWord[0] = true;
        return originalWord;
    }

    private int factorial(int numb){
        if (numb > 10) return 0;
        int result = 1;
        for(int i = 1; i <= numb; i++)
            result *= i;
        return result;
    }

}
