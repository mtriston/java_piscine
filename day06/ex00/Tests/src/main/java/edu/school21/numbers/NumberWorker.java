package edu.school21.numbers;

class IllegalNumberException extends RuntimeException {
}

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number <= 1)
            throw new IllegalNumberException();
        int sqrt = (int) Math.sqrt(number);
        for (int i = 2; i <= sqrt; ++i) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        int sum = 0;
        do {
            sum += number % 10;
            number /= 10;
        } while (number != 0);
        return sum;
    }
}