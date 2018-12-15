#!/usr/bin/python3

import sys

if __name__ == "__main__":
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    sum = 0
    for i in range(-1, len(data)-1):
        if data[i] == data[i+1]:
            sum = sum + int(data[i])
    print("Sum1:", sum)
    
    sum = 0
    for i in range(0, len(data)):
        if data[i] == data[i-len(data)//2]:
            sum = sum + int(data[i])
    print("Sum2:", sum)