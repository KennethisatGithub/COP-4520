# COP-4520
Assignment 1

For the method of calculating primes, we will be using the sieve method in a multithreaded application to speed up the process. The program is very simple, it will be using the thread class and be initializing 8 threads which will run through the run() method. The run method is using the sieve algorithm to calculate the primes on a global boolean array. The program will be using shared counters and sum to allow the threads to be able to keep track of the synchronized variables. This will allow the program to perform without having to worry about a race condition and breaking the program.
The speed before using multiple threads is
5243 milliseconds
After using multiple threads, we have a time of
4175 milliseconds
Which shows about 20% increase in efficiency.
Task of proportion 23.58%  is parallel.

