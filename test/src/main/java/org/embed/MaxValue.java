package org.embed;
import java.util.Scanner;

public class MaxValue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] numbers = new int[5];

        System.out.println("정수 5개를 입력하세요:");
        for (int i = 0; i < 5; i++) {
            numbers[i] = sc.nextInt();
        }

        int max = numbers[0];
        for (int i = 1; i < 5; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }

        System.out.println("5개의 정수 중 가장 큰 값: " + max);
        
    }

	
}