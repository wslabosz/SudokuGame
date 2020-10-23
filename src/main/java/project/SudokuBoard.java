package project;

import java.util.Random;

public class SudokuBoard {
    private static int [][] sudokuBoard = new int [9][9];


    public static void start(){
        Random generator = new Random();
        int rzad, kolumna;
        for(int i=0; i<9; i++){
            do{
                rzad=generator.nextInt(8);
                kolumna=generator.nextInt(8);
            }while(sudokuBoard[rzad][kolumna]!=0);
            sudokuBoard[rzad][kolumna]=i;
        }
    }

    public void fillBoard(){

    }

    public int getCell(int w, int k){

        return 0;
    }

    public static boolean isRowClear(int row, int nr){
        for(int i=0; i<9; i++){
            if(sudokuBoard[row][i]==nr){
                return false;
            }
        }
        return true;
    }

    public static boolean isColumnClear(int col, int nr){
        for(int i=0; i<9; i++){
            if(sudokuBoard[i][col]==nr){
                return false;
            }
        }
        return true;
    }

//    public static boolean isBoxClear(int row, int col, int nr){
//        if(row>=0 && row<3){
//            if(col>=3 && col<=5){
//                for(int i=0; i<9; i++){
//                    sudokuBoard[]
//                }
//            }
//        }
//        return true;
//    }

    public static void display(){
        for(int rzad=0; rzad<9; rzad++){
            if(rzad%3==0 && !(rzad==0 ||  rzad==9)){
                System.out.println();
            }
            for(int kolumna=0; kolumna<9; kolumna++){
                if(kolumna%3==0 && kolumna!=0){
                    System.out.print(" ");
                }
                System.out.print(sudokuBoard[rzad][kolumna]);
                if(kolumna!=8){
                    System.out.print("  ");
                }
            }
            if(rzad!=8){
                System.out.println();
            }
        }
    }
}