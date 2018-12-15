import sys
import re

def decompress(data):
    decompressed = ""
    d = ''.join(data.split('\n'))
    while len(d):
        s = re.match(r'^.*?(\((\d+?)x(\d+?)\))(.*)', d)
        if s is None:
            decompressed += d
            break
        dec, marker, d = d.partition(s.group(1))
        decompressed += dec
        for i in range(int(s.group(3))):
            decompressed += d[:int(s.group(2))]
        d = d[int(s.group(2)):]
    return decompressed

def dec(d):
    length = 0
    while len(d):
        s = re.match(r'^.*?(\((\d+?)x(\d+?)\))(.*)', d)
        if s is None:
            return length + len(d)
        decomp, marker, d = d.partition(s.group(1))
        length += len(decomp) + int(s.group(3)) * dec(d[:int(s.group(2))])
        d = d[int(s.group(2)):]
    return length


def full_decompress(data):
    length = 0
    d = ''.join(data.split('\n'))
    return dec(d)

def main():
    if (len(sys.argv) < 2):
        print("Usage python3 %s <input>" % sys.argv[0])
        exit(-1)
    with open(sys.argv[1], 'r') as input:
        data = input.read()
    print("Char count:", len(decompress(data)))
    print("Char count:", full_decompress(data))

if __name__ == '__main__':
    main()