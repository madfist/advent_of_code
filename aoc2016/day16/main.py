import re
import sys

def dragon_step(a):
    b = a[::-1] #reverse
    b = re.sub('a', '1', re.sub('1', '0', re.sub('0', 'a', b))) #invert
    return a + '0' + b

def checksum(d):
    l = 0
    w = d
    while l%2 == 0:
        cs = ''
        for i in range(0, len(w), 2):
            if w[i] == w[i+1]:
                cs += '1'
            else:
                cs += '0'
        w = cs
        l = len(cs)
    return w

def main():
    if (len(sys.argv) < 3):
        print("Usage: python3", sys.argv[0], "<init> <length>")
        exit(1)
    data_length = int(sys.argv[2])
    random_data = sys.argv[1]
    while len(random_data) < data_length:
        random_data = dragon_step(random_data)
    print("Checksum:", checksum(random_data[:data_length]))

if __name__ == '__main__':
    main()