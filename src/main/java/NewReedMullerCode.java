import lombok.Data;

import java.util.*;

@Data
public class NewReedMullerCode {
    private boolean[][] matrixG;
    private List<int[]> arrayI;
    private boolean[][] allWordsSizeM;
    private int n;
    private int k;
    private int d;
    private int r;
    private int m;

    public NewReedMullerCode(int r, int m){
        this.r = r;
        this.m = m;
        this.n = (int) Math.pow(2, m);
        this.k = 0;
        for (int i = 0; i <= r; i++)
            this.k += (factorial(m) / (factorial(i) * factorial(m - i)));
        this.d = (int) Math.pow(2, (double)m - r);
        createMatrixG();
    }

    private void createMatrixG(){

        List<int[]> Is = new ArrayList();

        boolean[][] allWords = MatrixCalc.createAllReverseWords(this.m);

        for(int h = 1; h <= this.r; h++) {
            for (int i = 0; i < allWords.length - 1; i++) {
                if (MatrixCalc.wordWeight(allWords[i]) != h)
                    continue;
                int[] temp = new int[MatrixCalc.wordWeight(allWords[i])];
                int j = MatrixCalc.wordWeight(allWords[i]) - 1;
                for (int q = 0; q < allWords[i].length; q++) {
                    if (allWords[i][q]) {
                        temp[j] = allWords[i].length - q - 1;
                        j--;
                    }
                }

                if (temp.length > 0) Is.add(temp);
            }
        }
        this.allWordsSizeM = allWords;
        int p = 0;
        int o = 0;

        for (int i = 0; i < Is.size(); i++) {
            if (Is.get(i).length == 1) {
                Is.add(p, Is.get(i));
                Is.remove(i + 1);
                p++;
            }
        }
        for (int i = Is.size() - 1; i > -1; i--){
            if(Is.get(i).length == this.r) {
                Is.add(Is.size() - o, Is.get(i));
                Is.remove(i);
                o++;
                if (Is.get(i).length == 1) i--;
            }
        }
        Is.add(0, new int[0]);
        for(int j = 0; j < Is.size(); j++){
            System.out.println(Arrays.toString(Is.get(j)));
        }
        System.out.println();
        this.arrayI = Is;
        int[] m_ = new int[this.m];
        for(int i = 0; i < m_.length; i++)
            m_[i] = i;
        this.matrixG = new boolean[this.k][this.n];
        for (int i = 0; i < k; i++){
            for (int j = 0; j < allWords.length; j++){
                this.matrixG[i][j] = resultFunctionForBasis(allWords[j], Is.get(i), m_);
            }
        }
    }

    public boolean[] decodingWord(boolean[] word) throws Exception {
        System.out.println("Принятое слово: \n" + MatrixCalc.toString(word));
        Map<String, Boolean> arrayM = new HashMap<>();
        arrayM.putAll(this.phaseOneDecodingWord(word, this.r));
        Map<String, Boolean> arrayM_2 = new HashMap<>();
        arrayM_2 = phaseTwoDecodingWord(word, this.r, arrayM);
        for(String key : arrayM_2.keySet()){
            System.out.println(key + " " + arrayM_2.get(key));
        }
        boolean[] resultWord = new boolean[this.k];
        int q = 1;
        for (int i = 1; i <= this.r; i++){
            for (int j = 0; j < this.arrayI.size(); j++) {
                if(this.arrayI.get(j).length == i){
                    resultWord[q] = arrayM_2.get(Arrays.toString(this.arrayI.get(j)));
                    q++;
                }
            }
        }
        System.out.println("Раскодированное слово: \n" + MatrixCalc.toString(resultWord));
        return MatrixCalc.rowMultMatr(resultWord, this.matrixG);
    }

    private Map<String, Boolean> phaseOneDecodingWord(boolean[] word, int i) throws Exception {
        Map<String, Boolean> m_ = new HashMap<>();

        int[] Z_m = new int[this.m];
        for (int j = 0; j < m; j++) Z_m[j] = j;
        List<int[]> arrayJ = new ArrayList<>();

        for (int j = 0; j < this.arrayI.size(); j++)
            if (this.arrayI.get(j).length == i)
                arrayJ.add(this.arrayI.get(j));

        for (int j = 0; j < arrayJ.size(); j++){
            for (int l = j + 1; l < arrayJ.size(); l++){
                if(MatrixCalc.equalsRows(arrayJ.get(j), arrayJ.get(l))) {
                    arrayJ.remove(l);
                }
            }
        }

        for (int j = 0; j < arrayJ.size(); j++){

            int[] arrayJ_c = new int[Z_m.length - arrayJ.get(j).length];

            int q = 0;
            for (int u = 0; u < Z_m.length; u++){
                boolean flag = false;
                for (int b = 0; b < arrayJ.get(j).length; b++){
                    if (Z_m[u] == arrayJ.get(j)[b])
                        flag = true;
                }
                if (!flag) {
                    arrayJ_c[q] = Z_m[u];
                    q++;
                }
            }

            List<boolean[]> H_J = new ArrayList<>();
            for (int u = 0; u < this.allWordsSizeM.length; u++) {
                if (resultFunctionForBasis(this.allWordsSizeM[u], arrayJ.get(j), Z_m)){
                    H_J.add(this.allWordsSizeM[u]);
                }
            }

            boolean[] arrForM = new boolean[H_J.size()];
            for (int c = 0; c < H_J.size(); c++) {

                boolean[] v = new boolean[this.allWordsSizeM.length];
                for (int z = 0; z < this.allWordsSizeM.length; z++){
                    v[z] = resultFunctionForBasis(MatrixCalc.rowPlusRow(this.allWordsSizeM[z], H_J.get(c)), arrayJ_c, Z_m);
                }

                boolean[] arrayOfAnd = new boolean[v.length];
                for (int z = 0; z < v.length; z++){
                    arrayOfAnd[z] = v[z] && word[z];
                }
                boolean wMultV = arrayOfAnd[0];
                for (int z = 1; z < v.length; z++){
                    wMultV ^= arrayOfAnd[z];
                }
                arrForM[c] = wMultV;
            }

            if (MatrixCalc.wordWeight(arrForM) > (int) Math.pow(2, (double)m - i - 1)){
                m_.put(Arrays.toString(arrayJ.get(j)), true);
            }
            if (arrForM.length - MatrixCalc.wordWeight(arrForM) > (int) Math.pow(2, (double)m - i - 1)){
                m_.put(Arrays.toString(arrayJ.get(j)), false);
            }
            if (MatrixCalc.wordWeight(arrForM) > (int) Math.pow(2, (double)m - this.r - 1) - 1
                    && arrForM.length - MatrixCalc.wordWeight(arrForM) > (int) Math.pow(2, (double)m - this.r - 1) - 1){
                throw new Exception("Слово не может быть раскодировано");
            }
        }
        return m_;
    }

    private Map<String, Boolean> phaseTwoDecodingWord(boolean[] word, int i, Map<String, Boolean> m_) throws Exception {
        if (i == 0) return m_;
        boolean[] wordForIMinusOne = new boolean[word.length];
        System.arraycopy(word, 0, wordForIMinusOne, 0, word.length);

        int[] Z_m = new int[this.m];
        for (int j = 0; j < m; j++) Z_m[j] = j;

        List<int[]> arrayJ = new ArrayList<>();
        for (int j = 0; j < this.arrayI.size(); j++)
            if (this.arrayI.get(j).length == i)
                arrayJ.add(this.arrayI.get(j));
        for (int j = 0; j < arrayJ.size(); j++){
            for (int l = j + 1; l < arrayJ.size(); l++){
                if(MatrixCalc.equalsRows(arrayJ.get(j), arrayJ.get(l))) {
                    arrayJ.remove(l);
                }
            }
        }

        for (int l = 0; l < arrayJ.size(); l++) {
            if (m_.get(Arrays.toString(arrayJ.get(l)))) {
                boolean[] v = new boolean[this.allWordsSizeM.length];
                for (int z = 0; z < this.allWordsSizeM.length; z++) {
                    v[z] = resultFunctionForBasis(this.allWordsSizeM[z], arrayJ.get(l), Z_m);
                }

                wordForIMinusOne = MatrixCalc.rowPlusRow(v, wordForIMinusOne);

            }
        }

        if(MatrixCalc.wordWeight(wordForIMinusOne) <= (int) Math.pow(2, (double) this.m - this.r - 1) - 1){
            for(int j = 0; j < this.arrayI.size(); j++){
                boolean flag = false;
                for (String key : m_.keySet()){
                    if (Arrays.toString(this.arrayI.get(j)).equals(key))
                        flag = true;
                }
                if (!flag){
                    m_.put(Arrays.toString(this.arrayI.get(j)), false);
                }
            }
        } else {
            m_.putAll(this.phaseOneDecodingWord(wordForIMinusOne, i - 1));
        }


        return m_;
    }

    private boolean resultFunctionForBasis(boolean[] x, int[] I, int[] m_){
        for (int i = 0; i < I.length; i++){
            for(int j = 0; j < m_.length; j++){
                if (m_[j] == I[i] && x[j])
                    return false;
            }
        }
        return true;
    }

    private int factorial(int numb){
        if (numb > 10) return 0;
        int result = 1;
        for(int i = 1; i <= numb; i++)
            result *= i;
        return result;
    }
}
