import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LinearCode {

    private int n, k;
    private boolean[][] matrixG;
    private boolean[][] matrixX;
    private boolean[][] matrixH;
    private boolean[][] words;

    public LinearCode(boolean[][] matrix) throws Exception {
        this.matrixG = matrix;
        this.k = matrix.length;
        this.n = matrix[0].length;
        MatrixCalc.Rref(this.matrixG);
        boolean flag = false;
        for(int i = k - 1; i >= 0; i--){
            for (int j = 0; j < n; j++)
                if (this.matrixG[i][j]) flag = true;
            if(!flag) {
                this.k--;
            }
            else break;
        }
        if(k != matrixG.length){
            boolean[][] newMatrixG = new boolean[k][n];
            for (int i = 0; i < k; i++){
                newMatrixG[i] = this.matrixG[i];
            }
            this.matrixG = newMatrixG;
        }
        this.matrixX = this.createMatrixX();
        this.matrixH = this.createMatrixH();
        this.words = this.createWords();
    }

    public int getCodeDistance(){
        int dist = n;
        int temp = 0;
        for(int i = 0; i < this.words.length; i++){
            for(int j = i + 1; j < this.words.length; j++){
                for(int index = 0; index < n; index++) {
                    if (this.words[i][index] != this.words[j][index])
                        temp++;
                }
                if (temp < dist) dist = temp;
                temp = 0;
            }
        }
        return dist;
    }

    private boolean[][] createMatrixX() {
        List<Integer> lead = this.getLeadRows();
        boolean[][] X = new boolean[k][n - lead.size()];
        int q = 0;
        for (int i = 0; i < n; i++){
            if(lead.contains(i)) continue;
            for (int j = 0; j < k; j++){
                X[j][q] = this.matrixG[j][i];
            }
            q++;
        }
        return X;
    }

    private boolean[][] createMatrixH() {
        List<Integer> lead = this.getLeadRows();

        int columnsH = this.matrixX[0].length;
        boolean[][] I = new boolean[columnsH][columnsH];
        for (int i = 0; i < columnsH; i++){
            for(int j = 0; j < columnsH; j++){
                I[i][j] = i == j;
            }
        }

        int rowsH = this.matrixX.length + I.length;

        boolean[][] H = new boolean[rowsH][columnsH];
        int iterX = 0;
        int iterI = 0;
        for (int i = 0; i < rowsH; i++){
            if(lead.contains(i)) {
                H[i] = this.matrixX[iterX];
                iterX++;
            }
            else {
                H[i] = I[iterI];
                iterI++;
            }
        }

        return H;
    }

    private boolean[][] createWords() throws Exception {
        int wordMax = 1;
        for(int i = 0; i < k; i++)
            wordMax *= 2;
        wordMax--;
        List<boolean[]> words = new ArrayList<boolean[]>();
        for (int i = 0; i <= wordMax; i++){
            String val = Integer.toBinaryString(i);
            boolean[] valArr = new boolean[k];
            for (int j = 0; j < val.length(); j++){
                valArr[k - j - 1] = val.charAt(val.length() - 1 - j) == '1';
            }
            words.add(valArr);
        }

        boolean[][] resultWords = new boolean[words.size()][this.n];
        for(int i = 0; i < words.size(); i++)
            resultWords[i] = MatrixCalc.rowMultMatr(words.get(i), this.matrixG);

        return resultWords;
    }

    private List<Integer> getLeadRows(){
        List<Integer> lead = new ArrayList<Integer>();
        for (int i = 0; i < k; i++){
            for (int j = 0; j < n; j++){
                if(this.matrixG[i][j]){
                    lead.add(j);
                    break;
                }
            }
        }
        return lead;
    }

}